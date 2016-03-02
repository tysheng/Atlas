package tysheng.atlas.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import butterknife.Bind;
import butterknife.BindString;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.fragment.WeatherFragment;

/**
 * Created by shengtianyang on 16/2/29.
 */
public class WeatherTabActivity extends BaseActivity {
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.vp_weathertab)
    ViewPager viewPager;
    @BindString(R.string.weather_city_shaoxing_url)
    String weather_city_shaoxing_url;
    @BindString(R.string.weather_city_ningbo_url)
    String weather_city_ningbo_url;
    @BindString(R.string.weather_city_xian_url)
    String weather_city_xian_url;
    @BindString(R.string.shaoxing)
    String shaoxing;
    @BindString(R.string.ningbo)
    String ningbo;
    @BindString(R.string.xian)
    String xian;
    @Bind(R.id.tb_weathertab)
    Toolbar toolbar;

    @Override
    public void initData() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
                        return new WeatherFragment(shaoxing, weather_city_shaoxing_url);
                    case 1:
                        return new WeatherFragment(ningbo, weather_city_ningbo_url);
                    case 2:
                        return new WeatherFragment(xian, weather_city_xian_url);
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
                        return shaoxing;
                    case 1:
                        return ningbo;
                    case 2:
                        return xian;
                    case 3:
                        return "自定义";
                }
                return "";
            }
        };


        viewPager.setAdapter(adapter);
        ViewCompat.setElevation(tabLayout, getResources().getDimension(R.dimen.appbar_elevation));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weathertab;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
