package tysheng.atlas.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.V2NodeRecyclerAdapter;
import tysheng.atlas.app.Constant;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.V2NodesBean;
import tysheng.atlas.presenter.GetPresenter;
import tysheng.atlas.presenter.GetPresenterImpl;
import tysheng.atlas.presenter.VolleyView;

/**
 * Created by shengtianyang on 16/2/2.
 */
public class V2NodeFragment extends BaseFragment implements VolleyView {
    @Bind(R.id.srl_node)
    SwipyRefreshLayout swipe;
    @Bind(R.id.rv_v2node)
    RecyclerView rvV2node;
    V2NodeRecyclerAdapter mAdapter;
    List<V2NodesBean> data;
    Fragment v2NodeFragment;
    List<V2NodesBean> list;
    private GetPresenter presenter;
    int total = 0;
    int used = 0;

    public V2NodeFragment() {
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.fm_node);
    }

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
        MyApplication.getRequestQueue().cancelAll(getClass().getSimpleName());
    }

    @Override
    protected void initData() {
        setHasOptionsMenu(true);
        presenter = new GetPresenterImpl(this);
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
                        .setCustomAnimations(0, 0, R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .add(R.id.fg_main, webviewFragment, "nodeweb" + position)
                        .commitAllowingStateLoss();
            }
        });
        rvV2node.setLayoutManager(new GridLayoutManager(frmContext, 2));
        rvV2node.setAdapter(mAdapter);
        
        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                swipe.setRefreshing(true);
                getData();
            }
        });
    }

    private void getData() {
        presenter.getData(Constant.URL_V2_NODE_ALL,getClass().getSimpleName());
    }

    private void initswipe() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
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
                    if (swipe.isRefreshing())
                        swipe.setRefreshing(false);
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_choosenode, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_node:
                new MaterialDialog.Builder(frmContext)
                        .title("请输入节点名")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .inputRange(2, 20)
                        .positiveText("OK")
                        .input("", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                FragmentManager manager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                v2NodeFragment = manager.findFragmentByTag("node");
                                WebviewFragment webviewFragment = new WebviewFragment(Constant.URL_V2_NODE_GO+input.toString());
                                transaction.hide(v2NodeFragment)
                                        .addToBackStack(null)
                                        .setCustomAnimations(0, 0, R.anim.abc_fade_in, R.anim.abc_fade_out)
                                        .add(R.id.fg_main, webviewFragment, "choose")
                                        .commitAllowingStateLoss();
                            }
                        }).show();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessResponse(String response) {
        list = JSON.parseArray(response, V2NodesBean.class);
        total = list.size();
        for (int i = 0; i < 30; i++) {
            data.add(list.get(i));
        }
        used += 30;
        mAdapter.notifyDataSetChanged();
        if (swipe.isRefreshing())
            swipe.setRefreshing(false);
    }

    @Override
    public void onFailResponse(VolleyError error) {
        if (swipe.isRefreshing())
            swipe.setRefreshing(false);
    }
}
