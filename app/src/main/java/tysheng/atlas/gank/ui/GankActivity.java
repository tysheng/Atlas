package tysheng.atlas.gank.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.adapter.GankViewPagerAdapter;
import tysheng.atlas.gank.bean.GankViewPagerItem;
import tysheng.atlas.gank.ui.fragment.GankFragment;
import tysheng.atlas.utils.ACache;
import tysheng.atlas.utils.RecyclerTransformAnimation;

/**
 * Created by shengtianyang on 16/4/1.
 */
public class GankActivity extends BaseActivity {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    GankViewPagerItem mItem;
    ACache mCache;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    public static final int REQUEST_CODE = 80;
    private GankViewPagerAdapter mAdapter;

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCache = ACache.get(actContext);
        getCache();
        mAdapter = new GankViewPagerAdapter(getSupportFragmentManager());
        for (String str : mItem.mList) {
            mAdapter.addFragment(GankFragment.newInstance(str), str);
        }
        viewPager.setPageTransformer(true, new RecyclerTransformAnimation());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void getCache() {
        mItem = (GankViewPagerItem) mCache.getAsObject("GankViewPagerItem");
        if (mItem == null)
            mItem = new GankViewPagerItem();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank;
    }


    @OnClick(R.id.imageView)
    public void onClick() {
        startActivityForResult(new Intent(actContext, SortActivity.class), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            onRefresh();
        }
    }

    private void onRefresh() {
        getCache();

        mAdapter.clear();
        tabLayout.removeAllTabs();
        viewPager.removeAllViews();
        for (String str : mItem.mList) {
            mAdapter.addFragment(GankFragment.newInstance(str), str);
        }
        viewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }
}
