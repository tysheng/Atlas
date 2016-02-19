package shengtianyang.atlas.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.fragment.AtlasFragment;
import shengtianyang.atlas.fragment.FlashFragment;
import shengtianyang.atlas.fragment.V2HotFragment;
import shengtianyang.atlas.fragment.V2NodeFragment;
import shengtianyang.atlas.fragment.WeatherFragment;
import shengtianyang.atlas.utils.Constant;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
//    private long exitTime;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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

        toolbar.setTitle(item.getTitle());
        if (id == R.id.nav_main) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fg_main, new V2HotFragment(Constant.URL_V2_HOT), null)
                    .commit();

        } else if (id == R.id.nav_transport) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fg_main, new WeatherFragment(), null)
                    .commit();

        } else if (id == R.id.nav_resource) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fg_main, new V2HotFragment(Constant.URL_V2_LASTED), null)
                    .commit();

        } else if (id == R.id.nav_climate) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fg_main, new V2NodeFragment(), null)
                    .commit();
        } else if (id == R.id.nav_disaster) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fg_main, new AtlasFragment(new int[]{
                            R.drawable.zhongguozhengqu, R.drawable.zhongguodixing,
                            R.drawable.zhongguodishi
                    }), null)
                    .commit();
        } else if (id == R.id.nav_world) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fg_main, new FlashFragment(), null)
                    .commit();

        } else if (id == R.id.nav_history) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
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
//    private void SuperToast(Context context,String content){
//        SuperActivityToast.create((Activity) context,content,
//                888, Style.getStyle(Style.BLUE, SuperToast.Animations.FLYIN)).show();
//
//    }
}
