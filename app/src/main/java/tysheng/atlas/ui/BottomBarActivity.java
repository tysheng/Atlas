package tysheng.atlas.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.gank.ui.fragment.GankCategoryFragment;
import tysheng.atlas.gank.ui.fragment.GankDailyFragment;
import tysheng.atlas.ui.fragment.V2HotFragment;
import tysheng.atlas.ui.fragment.WeatherFragment;
import tysheng.atlas.ui.fragment.WebviewFragment;

/**
 * Created by shengtianyang on 16/7/13.
 */
public class BottomBarActivity extends AppCompatActivity implements OnMenuTabClickListener {
    private BottomBar mBottomBar;
    private long mClickedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        ButterKnife.bind(this);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.menu_bottom);
        mBottomBar.setOnMenuTabClickListener(this);
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");

    }

    @Override
    public void onMenuTabSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.action_1:
                easyJump(GankCategoryFragment.newInstance("Android"));
                break;
            case R.id.action_2:
                easyJump(V2HotFragment.getInstance(Constant.URL_V2_HOT));
                break;
            case R.id.action_3:
                easyJump( new GankDailyFragment());
                break;
            case R.id.action_4:
                easyJump(new WeatherFragment(getString(R.string.shaoxing), getString(R.string.weather_city_shaoxing_url)));
                break;
            default:
                easyJump( WebviewFragment.newInstance("www.baidu.com",""));
                break;
        }
    }


    @Override
    public void onMenuTabReSelected(@IdRes int menuItemId) {
        if (System.currentTimeMillis() - mClickedTime > 1500) {
            mClickedTime = System.currentTimeMillis();
        } else {
            mClickedTime = 0;
            // TODO: 16/7/13
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    private void easyJump(Fragment to) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, to)
                .commit();
    }

}
