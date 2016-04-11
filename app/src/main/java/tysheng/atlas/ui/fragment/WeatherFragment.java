package tysheng.atlas.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.RWeatherBean;
import tysheng.atlas.mvp.retrofit.PPost;
import tysheng.atlas.mvp.retrofit.VPost;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class WeatherFragment extends BaseFragment implements VPost {

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
    private PPost presenter;

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
        presenter = new PPost(this);
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


    private void getWeather(final String cityname) {
        if (!city_url.equals("")) {
            drawee_weather_city.setImageURI(Uri.parse(city_url));
        } else {
            drawee_weather_city.setVisibility(View.GONE);
        }
        presenter.func(cityname);
    }


    @Override
    public void onFailedError(Throwable e) {
        showSnackbar(getView(), "请输入正确的城市名:(");
    }

    @Override
    public void onSuccess(RWeatherBean.HeWeatherEntity heWeatherEntity) {
        if (heWeatherEntity.getBasic().getCnty().equals("中国")) {
            if (heWeatherEntity.getAqi() == null){
                ll_aqi.setVisibility(View.GONE);
            } else {
                tvAqi.setText(heWeatherEntity.getAqi().getCity().getAqi());
                tcQlty.setText(heWeatherEntity.getAqi().getCity().getQlty());
                tv_textaqi.setText("AQI");
            }
            tvBrf.setText(heWeatherEntity.getSuggestion().getComf().getBrf());
            tvCity.setText(heWeatherEntity.getBasic().getCity());
            tvTmp.setText(heWeatherEntity.getHourly_forecast().get(0).getTmp() + "°");
            tvTxt.setText(heWeatherEntity.getSuggestion().getComf().getTxt());
            tvCond.setText(heWeatherEntity.getNow().getCond().getTxt());
        } else {
            showSnackbar(getView(), "国外城市暂不提供:(");
        }
    }
}
