package tysheng.atlas.activity;

import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import lib.lhh.fiv.library.FrescoImageView;
import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.fragment.AtlasFragment;
import tysheng.atlas.fragment.FlashFragment;
import tysheng.atlas.fragment.V2HotFragment;
import tysheng.atlas.fragment.V2NewFragment;
import tysheng.atlas.fragment.V2NodeFragment;
import tysheng.atlas.utils.SPHelper;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RESULT_LOAD_IMAGE = 10;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private long exitTime;
    private FragmentManager fragmentManager;
    AtlasFragment atlasFragment;
    FlashFragment flashFragment;
    V2HotFragment v2HotFragment;
    V2NewFragment v2NewFragment;
    V2NodeFragment v2NodeFragment;
    Fragment currentFragment;

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        initNav();

        fragmentManager = getSupportFragmentManager();
        atlasFragment = AtlasFragment.getInstance(getResources().getStringArray(R.array.atlas_drawbles));
        flashFragment = FlashFragment.getInstance();
        v2HotFragment = V2HotFragment.getInstance(Constant.URL_V2_HOT);
        v2NewFragment = V2NewFragment.getInstance(Constant.URL_V2_LASTED);
        v2NodeFragment = V2NodeFragment.getInstance();

        currentFragment = v2HotFragment;
        jumpFragment(null, v2HotFragment, R.id.fg_main, "hot");
    }

    private void initNav() {
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        FrescoImageView imageView = (FrescoImageView) headerView.findViewById(R.id.imageView);

        imageView.setImageURI(Uri.parse(SPHelper.getAvatar(actContext)),null);
        imageView.asCircle();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (id) {
            case R.id.nav_hot:
                if (v2HotFragment == null)
                    v2HotFragment = V2HotFragment.getInstance(Constant.URL_V2_HOT);
                if (!(currentFragment instanceof V2HotFragment))
                    jumpFragment(currentFragment, v2HotFragment, R.id.fg_main, "hot");
                currentFragment = v2HotFragment;
                break;
            case R.id.nav_new:
                if (v2NewFragment == null)
                    v2NewFragment = V2NewFragment.getInstance(Constant.URL_V2_LASTED);
                if (!(currentFragment instanceof V2NewFragment))
                    jumpFragment(currentFragment, v2NewFragment, R.id.fg_main, "new");
                currentFragment = v2NewFragment;
                break;
            case R.id.nav_node:
                if (v2NodeFragment == null)
                    v2NodeFragment = V2NodeFragment.getInstance();
                if (!(currentFragment instanceof V2NodeFragment))
                    jumpFragment(currentFragment, v2NodeFragment, R.id.fg_main, "node");
                currentFragment = v2NodeFragment;
                break;
            case R.id.nav_atals:
                if (atlasFragment == null)
                    atlasFragment = AtlasFragment.getInstance(getResources().getStringArray(R.array.atlas_drawbles));
                if (!(currentFragment instanceof AtlasFragment))
                    jumpFragment(currentFragment, atlasFragment, R.id.fg_main, "atlas");
                currentFragment = atlasFragment;
                break;
            case R.id.nav_flash:
                if (flashFragment == null)
                    flashFragment = FlashFragment.getInstance();
                if (!(currentFragment instanceof FlashFragment))
                    jumpFragment(currentFragment, flashFragment, R.id.fg_main, "flash");
                currentFragment = flashFragment;
                break;
            case R.id.nav_weather:
                jumpActivity(WeatherTabActivity.class, false);
                break;
            case R.id.nav_setting:
                jumpActivity(SettingActivity.class, false);
                break;
            case R.id.nav_weather2:
                jumpActivity(WeatherListActivity.class, false);
                break;
//            case R.id.imageView:
//                break;
//            case R.id.tv_name:
//                break;
//            case R.id.tv_email:
//                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragmentManager.getBackStackEntryCount() == 0 && !drawer.isDrawerOpen(GravityCompat.START)) {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ShowToast("再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
