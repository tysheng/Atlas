package tysheng.atlas.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.V2HotAdapter;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.V2HotBean;
import tysheng.atlas.net.VolleyIF;
import tysheng.atlas.net.VolleyUtils;

/**
 * Created by shengtianyang on 16/1/31.
 */
@SuppressLint("ValidFragment")
public class V2HotFragment extends BaseFragment {


    @Bind(R.id.rv_v2)
    RecyclerView rvV2;
    @Bind(R.id.swipe)
    SwipyRefreshLayout swipe;
    private List<V2HotBean> data;
    Fragment fragment;

    public V2HotFragment(String url) {
        this.url = url;
    }

    public static V2HotFragment getInstance(String url) {
        return new V2HotFragment(url);
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
                fragment = manager.findFragmentByTag("hot");
                transaction.hide(fragment)
                        .addToBackStack(null)
                        .add(R.id.fg_main, v2ThreadFragment, "thread" + position)
                        .commitAllowingStateLoss();
            }
        });
        rvV2.setLayoutManager(new LinearLayoutManager(frmContext));
        rvV2.setItemAnimator(new DefaultItemAnimator());
        rvV2.setAdapter(mAdapter);
        rvV2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }
        });
        VolleyUtils.Get(url, "V2HotFragment", new VolleyIF(VolleyIF.mListener,VolleyIF.mErrorListener) {
            @Override
            public void onMyResponse(String response) {
                data.addAll(JSON.parseArray(response, V2HotBean.class));
                mAdapter.notifyDataSetChanged();
                if (swipe.isRefreshing())
                    swipe.setRefreshing(false);
            }

            @Override
            public void onMyErrorResponse(VolleyError error) {
                if (swipe.isRefreshing())
                    swipe.setRefreshing(false);
            }
        });
    }

    private void initView() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    getHotThread();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("V2HotFragment");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2hot;
    }

    @Override
    protected void initData() {
        initView();
        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                swipe.setRefreshing(true);
                getHotThread();
            }
        });
    }
}
