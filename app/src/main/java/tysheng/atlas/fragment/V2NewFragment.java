package tysheng.atlas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.V2HotAdapter;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.V2HotBean;
import tysheng.atlas.presenter.GetPresenter;
import tysheng.atlas.presenter.GetPresenterImpl;
import tysheng.atlas.presenter.VolleyView;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class V2NewFragment extends BaseFragment implements VolleyView {

    @Bind(R.id.rv_v2)
    RecyclerView rvV2;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;

    private List<V2HotBean> data;
    Fragment fragment;
    private V2HotAdapter mAdapter;
    private GetPresenter presenter;
    public V2NewFragment() {
    }

    public V2NewFragment(String url) {
        this.url = url;
    }

    public static V2NewFragment getInstance(String url) {
        return new V2NewFragment(url);
    }

    private String url;

    private void getHotThread() {
        presenter.getData(url,getClass().getSimpleName());
    }

    @Override
    public void onSuccessResponse(String response) {
        data.addAll(JSON.parseArray(response, V2HotBean.class));
        mAdapter.notifyDataSetChanged();
        stopSwipe();
    }

    @Override
    public void onFailResponse(VolleyError error) {
        stopSwipe();
    }


    private void stopSwipe() {
        if (swipe.isRefreshing())
            swipe.setRefreshing(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll(getClass().getSimpleName());
    }
    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
    private void initView() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                getHotThread();
            }
        });
        data = new ArrayList<>();
        mAdapter = new V2HotAdapter(frmContext, data);
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
                bundle.putString("url",data.get(position).getUrl());
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
        presenter = new GetPresenterImpl(this);
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.fm_new);
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
