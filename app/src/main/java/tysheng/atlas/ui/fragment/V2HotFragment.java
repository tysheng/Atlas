package tysheng.atlas.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.adapter.V2HotAdapter;
import tysheng.atlas.api.MyRetrofit;
import tysheng.atlas.api.V2exApi;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.V2HotBean;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class V2HotFragment extends BaseFragment {
    public static final String HOT = "hot";
    public static final String LATEST = "latest";
    @BindView(R.id.rv_v2)
    RecyclerView rvV2;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<V2HotBean> data;
    Fragment fragment;
    private V2HotAdapter mAdapter;

    public V2HotFragment() {
    }

    public V2HotFragment(String url) {
        this.url = url;
    }

    public static V2HotFragment getInstance(String url) {
        return new V2HotFragment(url);
    }
    private String url;

    public void show(){
        Log.d("sty","show");
    }
    private void getHotThread(String mUrl) {
        mSubscription.add(
                MyRetrofit.getV2exApi(frmContext, V2exApi.BASE_URL)
                        .getV2Hot(mUrl)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<V2HotBean>>() {
                            @Override
                            public void onCompleted() {
                                swipe.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                swipe.setRefreshing(false);
                            }

                            @Override
                            public void onNext(List<V2HotBean> v2HotBeen) {
                                data.addAll(v2HotBeen);
                                mAdapter.notifyDataSetChanged();
                            }
                        }));
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
                getHotThread(url);
            }
        });
        data = new ArrayList<>();
        mAdapter = new V2HotAdapter(frmContext, data);
        mAdapter.setOnItemClickListener(new V2HotAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(V2ThreadFragment.TAG, data.get(position));
                V2ThreadFragment v2ThreadFragment = V2ThreadFragment.newInstance(data.get(position).getId());
                v2ThreadFragment.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                fragment = manager.findFragmentByTag(V2HotFragment.class.getName());
                addFragment(manager,fragment,v2ThreadFragment,R.id.fg_main,
                        V2ThreadFragment.class.getName());
            }
        });
        rvV2.setLayoutManager(new LinearLayoutManager(frmContext));
        rvV2.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.fm_v2ex);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2hot;
    }

    @Override
    protected void initData() {
        setHasOptionsMenu(true);
        initView();
        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                swipe.setRefreshing(true);
                getHotThread(url);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_v2ex, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                if (url.equals(HOT))
                    url = LATEST;
                else
                    url = HOT;
                data.clear();
                getHotThread(url);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
