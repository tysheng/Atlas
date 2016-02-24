package shengtianyang.atlas.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import butterknife.Bind;
import butterknife.BindString;
import shengtianyang.atlas.R;
import shengtianyang.atlas.base.BaseActivity;
import shengtianyang.atlas.fragment.VPWeatherFragment;
import shengtianyang.atlas.fragment.WeatherFragment;

/**
 * Created by shengtianyang on 16/2/23.
 */
public class WeatherActivity extends BaseActivity {
    @Bind(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    @BindString(R.string.weather_city_shaoxing_url)
    String weather_city_shaoxing_url;
    @BindString(R.string.weather_city_ningbo_url)
    String weather_city_ningbo_url;
    @BindString(R.string.weather_city_xian_url)
    String weather_city_xian_url;

    @Override
    public void initData() {
        Toolbar toolbar = mViewPager.getToolbar();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                getDrawable(R.drawable.menu_webp_background));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                getDrawable(R.drawable.menu_webp_background));
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.cyan,
                                getDrawable(R.drawable.menu_webp_background));
                    case 3:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.red,
                                getDrawable(R.drawable.menu_webp_background));
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });
        ViewPager viewPager = mViewPager.getViewPager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return new WeatherFragment("shaoxing",weather_city_shaoxing_url);
                    case 1:
                        return new WeatherFragment("ningbo",weather_city_ningbo_url);
                    case 2:
                        return new WeatherFragment("xian",weather_city_xian_url);
                    case 3:
                        return new WeatherFragment("","");
                    default:
                        return VPWeatherFragment.getInstance();
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
        });

//After set an adapter to the ViewPager
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

}
