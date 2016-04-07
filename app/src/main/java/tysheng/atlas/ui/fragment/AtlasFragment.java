package tysheng.atlas.ui.fragment;


import android.support.design.widget.TabLayout;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.DraweePagerAdapter;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.utils.RecyclerTransformAnimation;
import tysheng.atlas.utils.phtodraweeview.MultiTouchViewPager;


public class AtlasFragment extends BaseFragment {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    private String[] drawbles;
    @Bind(R.id.vp_main)
    MultiTouchViewPager viewPager;

    public AtlasFragment() {
    }

    public AtlasFragment(String[] drawble) {
        this.drawbles = drawble;
    }

    public static AtlasFragment getInstance(String[] drawble) {
        return new AtlasFragment(drawble);
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.fm_atlas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_atlas;
    }

    @Override
    protected void initData() {
        viewPager.setPageTransformer(true, new RecyclerTransformAnimation());
        viewPager.setAdapter(new DraweePagerAdapter(drawbles));
//        viewPager.setOffscreenPageLimit(1);
        tablayout.setupWithViewPager(viewPager);
    }

}
