package shengtianyang.atlas.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.alibaba.fastjson.JSON;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2NodeRecyclerAdapter;
import shengtianyang.atlas.app.Constant;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.bean.V2NodesBean;
import shengtianyang.atlas.net.VolleyIF;
import shengtianyang.atlas.net.VolleyUtils;

/**
 * Created by shengtianyang on 16/2/2.
 */
@SuppressLint("ValidFragment")
public class V2NodeFragment extends BaseFragment {
    @Bind(R.id.srl_node)
    SwipyRefreshLayout srlNode;
    @Bind(R.id.rv_v2node)
    RecyclerView rvV2node;
    V2NodeRecyclerAdapter mAdapter;
    List<V2NodesBean> data;
    Fragment v2NodeFragment;
    List<V2NodesBean> list;
    int total = 0;
    int used = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2node;
    }

    public static V2NodeFragment getInstance() {
        return new V2NodeFragment();
    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("V2NodeFragment");
    }

    @Override
    protected void initData() {
        initswipe();
        data = new ArrayList<>();
        mAdapter = new V2NodeRecyclerAdapter(frmContext, data);
        mAdapter.setOnItemClickListener(new V2NodeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                v2NodeFragment = manager.findFragmentByTag("node");
                WebviewFragment webviewFragment = new WebviewFragment(data.get(position).getUrl());
                transaction.hide(v2NodeFragment)
                        .addToBackStack(null)
                        .add(R.id.fg_main, webviewFragment, "nodeweb" + position)
                        .commitAllowingStateLoss();
            }
        });
        rvV2node.setLayoutManager(new GridLayoutManager(frmContext, 2));
        rvV2node.setAdapter(mAdapter);
        srlNode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                srlNode.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                srlNode.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData() {
        VolleyUtils.Get(Constant.URL_V2_NODE_ALL, "V2NodeFragment", new VolleyIF(VolleyIF.mListener) {
            @Override
            public void onMyResponse(String response) {
                list = JSON.parseArray(response, V2NodesBean.class);
                total = list.size();
                for (int i = 0; i < 30; i++) {
                    data.add(list.get(i));
                }
                used += 30;
//                data.addAll(JSON.parseArray(response, V2NodesBean.class));
                mAdapter.notifyDataSetChanged();
                if (srlNode.isRefreshing())
                    srlNode.setRefreshing(false);
            }
        });
    }

    private void initswipe() {
        srlNode.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        srlNode.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    used = 0;
                    data.clear();
                    getData();
                } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    int wtf = (total - used) < 30 ? total - used : 30;
                    for (int i = used; i < wtf+used; i++) {
                        data.add(list.get(i));
                    }
                    used += 30;
                    mAdapter.notifyDataSetChanged();
                    if (srlNode.isRefreshing())
                        srlNode.setRefreshing(false);
                }
            }
        });
    }

}
