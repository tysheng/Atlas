package tysheng.atlas.diy;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tysheng.atlas.R;

/**
 * Created by shengtianyang on 16/3/2.
 */
public class WeatherCard extends LinearLayout {
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_aqi)
    TextView tvAqi;
    @Bind(R.id.tv_textaqi)
    TextView tvTextaqi;
    @Bind(R.id.tc_qlty)
    TextView tcQlty;
    @Bind(R.id.ll_aqi)
    LinearLayout llAqi;
    @Bind(R.id.tv_brf)
    TextView tvBrf;
    @Bind(R.id.tv_txt)
    TextView tvTxt;
    @Bind(R.id.tv_tmp)
    TextView tvTmp;
    @Bind(R.id.tv_cond)
    TextView tvCond;

    public void setTvCity(String tvCity) {
        this.tvCity.setText(tvCity);
    }

    public void setTvAqi(String tvAqi) {
        this.tvAqi.setText(tvAqi);
    }

    public void setTvTextaqi(String tvTextaqi) {
        this.tvTextaqi.setText(tvTextaqi);
    }

    public void setTcQlty(String tcQlty) {
        this.tcQlty.setText(tcQlty);
    }


    public void setLlAqiVisible(int visibility) {
        this.llAqi.setVisibility(visibility);
    }

    public void setTvBrf(String tvBrf) {
        this.tvBrf.setText(tvBrf);
    }

    public void setTvTxt(String tvTxt) {
        this.tvTxt.setText(tvTxt);
    }

    public void setTvTmp(String tvTmp) {
        this.tvTmp.setText(tvTmp);
    }

    public void setTvCond(String tvCond) {
        this.tvCond.setText(tvCond);
    }

    public WeatherCard(Context context) {
        super(context);
        initView(context);

    }

    private void initView(Context context) {
        View.inflate(context, R.layout.weather_card, this);
        ButterKnife.bind(this);
    }

    public WeatherCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WeatherCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WeatherCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }
}
