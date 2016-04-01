package tysheng.atlas.gank.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.gank.api.GankApi;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.gank.adapter.EndlessRecyclerOnScrollListener;
import tysheng.atlas.gank.adapter.GankCategoryLoadAdapter;
import tysheng.atlas.gank.bean.GankCategory;
import tysheng.atlas.ui.activity.PictureActivity;
import tysheng.atlas.utils.ACache;

/**
 * Created by shengtianyang on 16/3/26.
 */
public class GankFragment extends BaseFragment {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    GankCategoryLoadAdapter mAdapter;
    GankCategory mGankCategory;
    LinearLayoutManager mLinearLayoutManager;
    int page = 1;
    ACache mCache;
    String typeName = "all";

    @Override
    protected void setTitle() {
        getActivity().setTitle("Gank");
    }

    public GankFragment() {
    }

    public GankFragment(String typeName) {
        this.typeName = typeName;
    }

    public static GankFragment getInstance() {
        return new GankFragment();
    }

    public static GankFragment newInstance(String typeName) {
        return new GankFragment(typeName);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initData() {

        mCache = ACache.get(frmContext);
        mGankCategory = (GankCategory) mCache.getAsObject(typeName);

        mLinearLayoutManager = new LinearLayoutManager(frmContext);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mAdapter.setHasMoreDataAndFooter(true, true);
                getData(typeName, current_page);
            }
        });
        if (mGankCategory == null) {
            mGankCategory = new GankCategory();
            List<GankCategory.ResultsEntity> data = new ArrayList<>();
            mAdapter = new GankCategoryLoadAdapter(frmContext, data);
            rv.setAdapter(mAdapter);
        } else {
            mAdapter = new GankCategoryLoadAdapter(frmContext, mGankCategory.results.subList(0, 9));
            rv.setAdapter(mAdapter);
        }

        initSwipe();
        onItemClick(typeName);

    }

    private void onItemClick(String typeName) {
        if (typeName.equals("福利")) {
            mAdapter.setOnItemClickListener(new GankCategoryLoadAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(View view, int position) {
                    Intent intent = PictureActivity.newIntent(frmContext, mAdapter.getItem(position).url,
                            mAdapter.getItem(position).desc);
                    startActivity(intent);
                }
            });
        } else {
            mAdapter.setOnItemClickListener(new GankCategoryLoadAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(View view, int position) {
                    ShowToast(mAdapter.getItem(position).url);
                }
            });
        }

    }

    private void getData(String category, final int page) {
        subscriber.add(RetrofitSingleton.getInstance(MyApplication.getInstance(), GankApi.BASE_URL)
                .create(GankApi.class)
                .getParams(category, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankCategory>() {
                    @Override
                    public void onCompleted() {
                        stopSwipe();
                        stopLoad();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopSwipe();
                        stopLoad();
                    }

                    @Override
                    public void onNext(GankCategory gankCategory) {
                        if (gankCategory.results.isEmpty()) {
                            ShowToast("没有更多数据了~");
                            stopLoad();
                            return;
                        }

                        if (!gankCategory.error) {
                            if (page == 1 && gankCategory.results.size() < 5) {
                                mGankCategory.results.addAll(gankCategory.results);
                                mCache.put(typeName, mGankCategory, 2 * ACache.TIME_DAY);
                            }

                            mAdapter.appendToList(gankCategory.results);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ShowToast("网络访问出错");
                        }


                    }
                }));

    }


    private void stopSwipe() {
        if (swipe.isRefreshing())
            swipe.setRefreshing(false);
    }

    private void stopLoad() {
        if (mAdapter.hasFooter() || mAdapter.hasMoreData())
            mAdapter.setHasMoreDataAndFooter(false, false);
    }

    private void initSwipe() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                getData(typeName, page = 1);
            }
        });
        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                swipe.setRefreshing(true);
                getData(typeName, page);
            }
        });
    }
}
