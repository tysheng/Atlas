package shengtianyang.atlas.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.TestRecyclerViewAdapter;
import shengtianyang.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class VPWeatherFragment extends BaseFragment {


    @Bind(R.id.rv_vp_weather)
    RecyclerView rvVpWeather;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 100;

    private List<Object> mContentItems = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vp_weather;
    }
    public static VPWeatherFragment getInstance(){
        return new VPWeatherFragment();
    }

    @Override
    protected void initData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvVpWeather.setLayoutManager(layoutManager);
        rvVpWeather.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
        rvVpWeather.setAdapter(mAdapter);

        {
            for (int i = 0; i < ITEM_COUNT; ++i)
                mContentItems.add(new Object());
            mAdapter.notifyDataSetChanged();
        }
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), rvVpWeather, null);
    }

}
