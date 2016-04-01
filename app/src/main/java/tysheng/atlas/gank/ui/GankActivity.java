package tysheng.atlas.gank.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.adapter.GankViewPagerAdapter;
import tysheng.atlas.gank.bean.GankViewPagerItem;
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

    @Override
    public void initData() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCache = ACache.get(actContext);
        mItem = (GankViewPagerItem) mCache.getAsObject("GankViewPagerItem");
        if (mItem == null)
            mItem = new GankViewPagerItem();

        viewPager.setPageTransformer(true, new RecyclerTransformAnimation());
        viewPager.setAdapter(new GankViewPagerAdapter(getSupportFragmentManager(), mItem.mList.size(), mItem));
        ViewCompat.setElevation(tabLayout, getResources().getDimension(R.dimen.appbar_elevation));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
