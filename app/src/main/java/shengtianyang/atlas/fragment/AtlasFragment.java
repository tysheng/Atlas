package shengtianyang.atlas.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.DraweePagerAdapter;
import shengtianyang.atlas.adapter.Transform;
import shengtianyang.atlas.phtodraweeview.MultiTouchViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class AtlasFragment extends Fragment {

    private int[] drawble;
    @Bind(R.id.vp_main)
    MultiTouchViewPager viewPager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @Bind(R.id.textView)
    TextView tv;


    public AtlasFragment(int[] drawble) {
        this.drawble = drawble;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_china_main, container, false);
        ButterKnife.bind(this, view);
        viewPager.setPageTransformer(true, new Transform());
        viewPager.setAdapter(new DraweePagerAdapter(drawble));
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
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
