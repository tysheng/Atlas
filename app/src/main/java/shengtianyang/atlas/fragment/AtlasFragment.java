package shengtianyang.atlas.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import butterknife.Bind;
import me.relex.circleindicator.CircleIndicator;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.DraweePagerAdapter;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.phtodraweeview.MultiTouchViewPager;
import shengtianyang.atlas.utils.RecyclerTransformAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class AtlasFragment extends BaseFragment {

    private int[] drawble;
    private String[] stringsdrawble;
    @Bind(R.id.vp_main)
    MultiTouchViewPager viewPager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.textView)
    TextView tv;


    public AtlasFragment(int[] drawble) {
        this.drawble = drawble;
    }
    public AtlasFragment(String[] drawble) {
        this.stringsdrawble = drawble;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_china_main;
    }

    @Override
    protected void initData() {
        viewPager.setPageTransformer(true, new RecyclerTransformAnimation());
        viewPager.setAdapter(new DraweePagerAdapter(stringsdrawble));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv.setText("中国政区图");
                        break;
                    case 1:
                        tv.setText("中国地形图");
                        break;
                    case 2:
                        tv.setText("中国地势图");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setViewPager(viewPager);
    }

}
