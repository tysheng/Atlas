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

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        fragmentManager.beginTransaction()
                .replace(R.id.fg_main, new V2HotFragment(Constant.URL_V2_HOT), "")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!toolbar.getTitle().equals(""))
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

        if (id == R.id.nav_main) {
            changeFragment(new V2HotFragment(Constant.URL_V2_HOT));
        } else if (id == R.id.nav_transport) {
            jumpActivity(WeatherActivity.class, false);
//            changeFragment(new WeatherFragment());
        } else if (id == R.id.nav_resource) {
            changeFragment(new V2HotFragment(Constant.URL_V2_LASTED));
        } else if (id == R.id.nav_climate) {
            changeFragment(new V2NodeFragment());
        } else if (id == R.id.nav_disaster) {
//            changeFragment(new AtlasFragment(new int[]{
//                    R.drawable.zhongguozhengqu, R.drawable.zhongguodixing,
//                    R.drawable.zhongguodishi
//            }));
            changeFragment(new AtlasFragment(getResources().getStringArray(R.array.atlas_drawbles)));
        } else if (id == R.id.nav_world) {
            changeFragment(new FlashFragment());
        } else if (id == R.id.nav_history) {
            jumpActivity(SettingActivity.class, false);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment) {
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
