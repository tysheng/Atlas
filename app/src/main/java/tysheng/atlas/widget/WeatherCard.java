package tysheng.atlas.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tysheng.atlas.R;

/**
 * Created by shengtianyang on 16/3/2.
 */
public class WeatherCard extends LinearLayout {
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_aqi)
    TextView tvAqi;
    @BindView(R.id.tv_textaqi)
    TextView tvTextaqi;
    @BindView(R.id.tc_qlty)
    TextView tcQlty;
    @BindView(R.id.ll_aqi)
    LinearLayout llAqi;
    @BindView(R.id.tv_brf)
    TextView tvBrf;
    @BindView(R.id.tv_txt)
    TextView tvTxt;
    @BindView(R.id.tv_tmp)
    TextView tvTmp;
    @BindView(R.id.tv_cond)
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
        this(context);
    }

    public WeatherCard(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }
}
