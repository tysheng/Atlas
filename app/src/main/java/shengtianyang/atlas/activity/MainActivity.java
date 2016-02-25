package shengtianyang.atlas.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.app.Constant;
import shengtianyang.atlas.base.BaseActivity;
import shengtianyang.atlas.fragment.AtlasFragment;
import shengtianyang.atlas.fragment.FlashFragment;
import shengtianyang.atlas.fragment.V2HotFragment;
import shengtianyang.atlas.fragment.V2NewFragment;
import shengtianyang.atlas.fragment.V2NodeFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    //    private long exitTime;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private String nowItemTtile = "";
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
        navigationView.setNavigationItemSelectedListener(this);
        atlasFragment = AtlasFragment.getInstance(getResources().getStringArray(R.array.atlas_drawbles));
        flashFragment = FlashFragment.getInstance();
        v2HotFragment = V2HotFragment.getInstance(Constant.URL_V2_HOT);
        v2NewFragment = V2NewFragment.getInstance(Constant.URL_V2_LASTED);
        v2NodeFragment = V2NodeFragment.getInstance();
        
        currentFragment = v2HotFragment;
        jumpFragment(null, v2HotFragment, R.id.fg_main, "hot");
//        fragmentManager.beginTransaction()
//                .replace(R.id.fg_main, V2HotFragment.getInstance(Constant.URL_V2_HOT), "V2HotFragment")
//                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!toolbar.getTitle().equals(""))
            toolbar.setTitle(nowItemTtile);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id != R.id.nav_history && id != R.id.nav_transport) {
            nowItemTtile = (String) item.getTitle();
            toolbar.setTitle(nowItemTtile);
        }
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (id) {
            case R.id.nav_main:
                if (v2HotFragment == null) {
                    v2HotFragment = V2HotFragment.getInstance(Constant.URL_V2_HOT);
                }
                if (currentFragment instanceof V2HotFragment) {
                } else {
                    jumpFragment(currentFragment, v2HotFragment, R.id.fg_main, "hot");
                }
                currentFragment = v2HotFragment;
                break;
            case R.id.nav_resource:
                if (v2NewFragment == null) {
                    v2NewFragment = V2NewFragment.getInstance(Constant.URL_V2_LASTED);
                }
                if (currentFragment instanceof V2NewFragment) {
                } else {
                    jumpFragment(currentFragment, v2NewFragment, R.id.fg_main, "new");
                }
                currentFragment = v2NewFragment;
                break;
            case R.id.nav_climate:
                if (v2NodeFragment == null) {
                    v2NodeFragment = V2NodeFragment.getInstance();
                }
                if (currentFragment instanceof V2NodeFragment) {
                } else {
                    jumpFragment(currentFragment, v2NodeFragment, R.id.fg_main, "node");
                }
                currentFragment = v2NodeFragment;
                break;
            case R.id.nav_disaster:
                if (atlasFragment == null) {
                    atlasFragment = AtlasFragment.getInstance(getResources().getStringArray(R.array.atlas_drawbles));
                }
                if (currentFragment instanceof AtlasFragment) {
                } else {
                    jumpFragment(currentFragment, atlasFragment, R.id.fg_main, "atlas");
                }
                currentFragment = atlasFragment;
                break;
            case R.id.nav_world:
                if (flashFragment == null) {
                    flashFragment = FlashFragment.getInstance();
                }
                if (currentFragment instanceof FlashFragment) {
                } else {
                    jumpFragment(currentFragment, flashFragment, R.id.fg_main, "flash");
                }
                currentFragment = flashFragment;
                break;
            case R.id.nav_transport:
                jumpActivity(WeatherActivity.class, false);
                break;
            case R.id.nav_history:
                jumpActivity(SettingActivity.class, false);
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fg_main, fragment, null)
                .commit();
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                SuperToast(MainActivity.this,"再按一次退出程序");
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//    private void SuperToast(Context actContext,String content){
//        SuperActivityToast.create((Activity) actContext,content,
//                888, Style.getStyle(Style.BLUE, SuperToast.Animations.FLYIN)).show();
//
//    }
}
