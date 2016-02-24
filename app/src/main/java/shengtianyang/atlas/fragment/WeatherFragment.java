package shengtianyang.atlas.fragment;

import android.content.Context;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.app.Constant;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class WeatherFragment extends BaseFragment {

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
    @Bind(R.id.drawee_weather_city)
    SimpleDraweeView drawee_weather_city;
    @Bind(R.id.scrollView)
    ObservableScrollView mScrollView;
    @Bind(R.id.et_search_city)
    EditText etSearchCity;
    private String cityname;
    private String city_url;

    public WeatherFragment(String cityname,String city_url) {
        this.cityname = cityname;
        this.city_url = city_url;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    protected void initData() {
        if (cityname.equals("")) {
            etSearchCity.setVisibility(View.VISIBLE);
            etSearchCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        InputMethodManager inputMethodManager =(InputMethodManager) getContext().
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

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);

    }

    private void getWeather(final String cityname) {
        if (!city_url.equals("")){
            drawee_weather_city.setImageURI(Uri.parse(city_url));
        } else {
            drawee_weather_city.setVisibility(View.GONE);
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.HF_WEATHER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.optJSONArray("HeWeather data service 3.0");
                    JSONObject object1 = array.optJSONObject(0);
                    if (object1.optString("status").equals("ok")) {
                        JSONObject object2 = object1.optJSONObject("aqi");
                        JSONObject basic = object1.optJSONObject("basic");
                        JSONObject now = object1.optJSONObject("now");
                        JSONObject suggestion = object1.optJSONObject("suggestion");
                        JSONObject comf = suggestion.optJSONObject("comf");
                        JSONArray hourly_forecast = object1.optJSONArray("hourly_forecast");
                        JSONObject hourforecast = hourly_forecast.getJSONObject(0);
                        JSONObject object3 = object2.optJSONObject("city");
                        String aqi = object3.optString("aqi");
                        String qlty = object3.optString("qlty");
                        String brf = comf.optString("brf");
                        String txt = comf.optString("txt");
                        String tmp = hourforecast.optString("tmp");
                        String city_name = basic.optString("city");
                        String cond = now.optJSONObject("cond").optString("txt");
                        tvAqi.setText(aqi);
                        tvBrf.setText(brf);
                        tvCity.setText(city_name);
                        tvTmp.setText(tmp + "°");
                        tvTxt.setText(txt);
                        tcQlty.setText(qlty);
                        tvCond.setText(cond);
                    } else {
                        Toast.makeText(getActivity(), "请输入正确的城市名", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("apikey", Constant.HF_WEATHER_APIKEY);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("city", cityname);
                return params;
            }
        };
        stringRequest.setTag("WeatherFragment" + cityname);
        MyApplication.getRequestQueue().add(stringRequest);
    }

}
