package tysheng.atlas.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tysheng.atlas.gank.bean.GankViewPagerItem;
import tysheng.atlas.gank.ui.fragment.GankFragment;

/**
 * Created by shengtianyang on 16/4/1.
 */
public class GankViewPagerAdapter extends FragmentStatePagerAdapter {

    private int mPages = 8;
    private GankViewPagerItem mItem;

    public GankViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public GankViewPagerAdapter(FragmentManager fm, int mPages, GankViewPagerItem mItem) {
        super(fm);
        this.mPages = mPages;
        this.mItem = mItem;
    }

    @Override
    public Fragment getItem(int position) {
        return GankFragment.newInstance(mItem.mList.get(position));
    }

    @Override
    public int getCount() {
        return mPages;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItem.mList.get(position);
    }
}
