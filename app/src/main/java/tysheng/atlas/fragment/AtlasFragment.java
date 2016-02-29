package tysheng.atlas.fragment;


import android.annotation.SuppressLint;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.DraweePagerAdapter;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.utils.RecyclerTransformAnimation;
import tysheng.atlas.utils.phtodraweeview.MultiTouchViewPager;


@SuppressLint("ValidFragment")
public class AtlasFragment extends BaseFragment {

    @Bind(R.id.stl_atlas)
    SmartTabLayout stlAtlas;
    private String[] stringsdrawble;
    @Bind(R.id.vp_main)
    MultiTouchViewPager viewPager;



    public AtlasFragment(String[] drawble) {
        this.stringsdrawble = drawble;
    }

    public static AtlasFragment getInstance(String[] drawble) {
        return new AtlasFragment(drawble);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_atlas;
    }

    @Override
    protected void initData() {
        viewPager.setPageTransformer(true, new RecyclerTransformAnimation());
        viewPager.setAdapter(new DraweePagerAdapter(stringsdrawble));

        stlAtlas.setViewPager(viewPager);
    }

}
