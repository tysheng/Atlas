package tysheng.atlas.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.ui.GankCategoryActivity;
import tysheng.atlas.gank.ui.GankDailyActivity;
import tysheng.atlas.gank.utils.GankUtils;
import tysheng.atlas.hupu.ui.ForumFragment;
import tysheng.atlas.ui.fragment.FragmentCallback;
import tysheng.atlas.ui.fragment.V2HotFragment;
import tysheng.atlas.utils.ACache;
import tysheng.atlas.utils.SPHelper;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentCallback {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.cl)
    CoordinatorLayout cl;

    private long exitTime;
    private FragmentManager fragmentManager;
    V2HotFragment v2HotFragment;
    ForumFragment forumFragment;
    Fragment currentFragment;

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initNav();

        fragmentManager = getSupportFragmentManager();
        v2HotFragment = V2HotFragment.getInstance(V2HotFragment.HOT);
        forumFragment = ForumFragment.getInstance();
        currentFragment = v2HotFragment;
        jumpFragment(null, v2HotFragment, R.id.fg_main, V2HotFragment.class.getName());
        if (!GankUtils.isNetworkConnected(actContext))
            showSnackbar(cl, "貌似没有网络连接...");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("sty",outState.toString());
    }

    private void initNav() {
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        CircleImageView imageView = (CircleImageView) headerView.findViewById(R.id.imageView);
        TextView name = (TextView) headerView.findViewById(R.id.tv_name);
        TextView email = (TextView) headerView.findViewById(R.id.tv_email);
        navigationView.getMenu().findItem(R.id.nav_node).setIcon(GankUtils.getWeekIcon());

        if (ACache.get(actContext).getAsBitmap(Constant.AVATAR_BITMAP) == null) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.menu_myavatar));
        } else
            imageView.setImageBitmap(ACache.get(actContext).getAsBitmap(Constant.AVATAR_BITMAP));

        SPHelper spHelper = new SPHelper(actContext);
        name.setText(spHelper.getSpString(Constant.USER_NAME,getString(R.string.user_name)));
        email.setText(spHelper.getSpString(Constant.USER_EMAIL,getString(R.string.user_email)));

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
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_hot:
                if (v2HotFragment == null)
                    v2HotFragment = V2HotFragment.getInstance(V2HotFragment.HOT);
                if (!(currentFragment instanceof V2HotFragment))
                    jumpFragment(currentFragment, v2HotFragment, R.id.fg_main, V2HotFragment.class.getName());
                currentFragment = v2HotFragment;
                break;
            case R.id.nav_new:
                jumpActivity(GankCategoryActivity.class, false);
                break;
            case R.id.nav_node:
                jumpActivity(GankDailyActivity.class, false);
                break;
            case R.id.nav_flash:
                if (forumFragment == null)
                    forumFragment = ForumFragment.getInstance();
                if (!(currentFragment instanceof ForumFragment))
                    jumpFragment(currentFragment, forumFragment, R.id.fg_main, ForumFragment.class.getName());
                currentFragment = forumFragment;
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
                    showSnackbar(cl, "再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void func1(String s) {
        if (s.equals("")){
            V2HotFragment fragment = (V2HotFragment) fragmentManager.findFragmentByTag(V2HotFragment.class.getName());
            if (fragment!=null){
                fragment.show();
            }
        }
    }
}
