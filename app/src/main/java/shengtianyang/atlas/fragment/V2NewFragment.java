package shengtianyang.atlas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2HotAdapter;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.bean.V2HotBean;
import shengtianyang.atlas.net.VolleyIF;
import shengtianyang.atlas.net.VolleyUtils;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class V2NewFragment extends BaseFragment {

    @Bind(R.id.rv_v2)
    RecyclerView rvV2;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private List<V2HotBean> data;
    Fragment fragment;

    public V2NewFragment(String url) {
        this.url = url;
    }

    public static V2NewFragment getInstance(String url) {
        return new V2NewFragment(url);
    }

    private String url;

    private void getHotThread() {
        data = new ArrayList<V2HotBean>();
        final V2HotAdapter mAdapter = new V2HotAdapter(frmContext, data);
        mAdapter.setOnItemClickListener(new V2HotAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("avatar_normal", data.get(position).getMember().getAvatar_normal());
                bundle.putString("username", data.get(position).getMember().getUsername());
                bundle.putString("node_title", data.get(position).getNode().getTitle());
                bundle.putString("content", data.get(position).getContent());
                bundle.putString("title", data.get(position).getTitle());
                bundle.putInt("last_modified", data.get(position).getLast_modified());
                V2ThreadFragment v2ThreadFragment = new V2ThreadFragment(data.get(position).getId());
                v2ThreadFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                fragment = manager.findFragmentByTag("new");
                transaction.hide(fragment)
                        .addToBackStack(null)
                        .add(R.id.fg_main, v2ThreadFragment, "thread" + position)
                        .commitAllowingStateLoss();
            }
        });
        rvV2.setLayoutManager(new LinearLayoutManager(frmContext));
        rvV2.setItemAnimator(new DefaultItemAnimator());
        rvV2.setAdapter(mAdapter);
        VolleyUtils.Get(url, "V2NewFragment", new VolleyIF(VolleyIF.mListener) {
            @Override
            public void onMyResponse(String response) {
                data.addAll(JSON.parseArray(response, V2HotBean.class));
                mAdapter.notifyDataSetChanged();
                if (swipe.isRefreshing())
                    swipe.setRefreshing(false);
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("V2NewFragment");
    }
    private void initView() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHotThread();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2hot;
    }

    @Override
    protected void initData() {
        initView();
        getHotThread();
    }
}
