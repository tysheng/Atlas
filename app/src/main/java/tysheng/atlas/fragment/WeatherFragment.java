package tysheng.atlas.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.presenter.WeatherPresenter;
import tysheng.atlas.presenter.WeatherPresenterImpl;
import tysheng.atlas.presenter.WeatherView;

/**
 * Created by shengtianyang on 16/1/31.
 */
@SuppressLint("ValidFragment")
public class WeatherFragment extends BaseFragment implements WeatherView{

    @Bind(R.id.tv_aqi)
    TextView tvAqi;
    @Bind(R.id.tc_qlty)
    TextView tcQlty;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_brf)
    TextView tvBrf;
    @Bind(R.id.tv_txt)
    TextView tvTxt;
    @Bind(R.id.tv_tmp)
    TextView tvTmp;
    @Bind(R.id.tv_cond)
    TextView tvCond;
    @Bind(R.id.tv_textaqi)
    TextView tv_textaqi;
    @Bind(R.id.drawee_weather_city)
    SimpleDraweeView drawee_weather_city;
    @Bind(R.id.et_search_city)
    EditText etSearchCity;
    @Bind(R.id.ll_aqi)
    LinearLayout ll_aqi;
    private String cityname;
    private String city_url;
    private WeatherPresenter presenter;

    public WeatherFragment(String cityname, String city_url) {
        this.cityname = cityname;
        this.city_url = city_url;
    }
    public WeatherFragment() {
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getLayoutId() {
            return R.layout.fragment_weathertab;
    }

    @Override
    protected void initData() {
        presenter = new WeatherPresenterImpl(this);
        if (cityname.equals("")) {
            etSearchCity.setVisibility(View.VISIBLE);
            etSearchCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(etSearchCity.getWindowToken(), 0);
                        getWeather(etSearchCity.getText().toString());
                        etSearchCity.setCursorVisible(false);
                        return true;
                    }
                    return false;
                }
            });
            etSearchCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etSearchCity.setCursorVisible(true);
                }
            });
        } else {
            getWeather(cityname);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("WeatherFragment" + cityname);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void getWeather(final String cityname) {
        if (!city_url.equals("")) {
            drawee_weather_city.setImageURI(Uri.parse(city_url));
        } else {
            drawee_weather_city.setVisibility(View.GONE);
        }

        HashMap<String, String> header = new HashMap<>();
        header.put("apikey", Constant.HF_WEATHER_APIKEY);
        HashMap<String, String> params = new HashMap<>();
        params.put("city", cityname);
        presenter.getData(Constant.HF_WEATHER_API, "WeatherFragment" + cityname, header, params);
    }

    @Override
    public void onSuccessResponse(String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.optJSONArray("HeWeather data service 3.0");
            JSONObject object1 = array.optJSONObject(0);
            if (object1.optString("status").equals("ok")) {
                JSONObject basic = object1.optJSONObject("basic");
                if (basic.optString("cnty").equals("中国")) {
                    JSONObject object2 = object1.optJSONObject("aqi");
                    if (object2 != null) {
                        JSONObject object3 = object2.optJSONObject("city");
                        String aqi = object3.optString("aqi");
                        String qlty = object3.optString("qlty");
                        tvAqi.setText(aqi);
                        tcQlty.setText(qlty);
                        tv_textaqi.setText("AQI");
                    } else {
                        ll_aqi.setVisibility(View.GONE);
                    }

                    JSONObject now = object1.optJSONObject("now");
                    JSONObject suggestion = object1.optJSONObject("suggestion");
                    JSONObject comf = suggestion.optJSONObject("comf");
                    JSONArray hourly_forecast = object1.optJSONArray("hourly_forecast");
                    JSONObject hourforecast = hourly_forecast.getJSONObject(0);

                    String brf = comf.optString("brf");
                    String txt = comf.optString("txt");
                    String tmp = hourforecast.optString("tmp");
                    String city_name = basic.optString("city");
                    String cond = now.optJSONObject("cond").optString("txt");

                    tvBrf.setText(brf);
                    tvCity.setText(city_name);
                    tvTmp.setText(tmp + "°");
                    tvTxt.setText(txt);
                    tvCond.setText(cond);
                } else {
                    ShowToast("外国城市懒得解析~");
                }
            } else {
                ShowToast("请输入正确的城市名");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailResponse(VolleyError error) {

    }
}
