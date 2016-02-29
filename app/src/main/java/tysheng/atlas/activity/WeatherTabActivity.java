package tysheng.atlas.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.BindString;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.fragment.WeatherFragment;

/**
 * Created by shengtianyang on 16/2/29.
 */
public class WeatherTabActivity extends BaseActivity {
    @Bind(R.id.stl_weathertab)
    SmartTabLayout stlWeathertab;
    @Bind(R.id.vp_weathertab)
    ViewPager vpWeathertab;
    @BindString(R.string.weather_city_shaoxing_url)
    String weather_city_shaoxing_url;
    @BindString(R.string.weather_city_ningbo_url)
    String weather_city_ningbo_url;
    @BindString(R.string.weather_city_xian_url)
    String weather_city_xian_url;
    @Bind(R.id.tb_weathertab)
    Toolbar tbWeathertab;

    @Override
    public void initData() {
        tbWeathertab.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tbWeathertab);
        tbWeathertab.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        tbWeathertab.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return new WeatherFragment("shaoxing", weather_city_shaoxing_url);
                    case 1:
                        return new WeatherFragment("ningbo", weather_city_ningbo_url);
                    case 2:
                        return new WeatherFragment("xian", weather_city_xian_url);
                    case 3:
                        return new WeatherFragment("", "");
                    default:
                        return new WeatherFragment("", "");
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "绍兴";
                    case 1:
                        return "宁波";
                    case 2:
                        return "西安";
                    case 3:
                        return "其他";
                }
                return "";
            }
        };


        vpWeathertab.setAdapter(adapter);

        stlWeathertab.setViewPager(vpWeathertab);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weathertab;
    }

}
