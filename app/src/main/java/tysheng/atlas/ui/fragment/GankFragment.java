package tysheng.atlas.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.adapter.EndlessRecyclerOnScrollListener;
import tysheng.atlas.adapter.GankCategoryLoadAdapter;
import tysheng.atlas.api.GankApi;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.GankCategory;
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
    Fragment fragment;
    GankCategory mGankCategory;
    LinearLayoutManager mLinearLayoutManager;
    int page = 1;
    ACache mCache;

    @Override
    protected void setTitle() {
        getActivity().setTitle("Gank");
    }

    public GankFragment() {
    }

    public static GankFragment getInstance() {
        return new GankFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initData() {
        initSwipe();
        mCache = ACache.get(frmContext);
        mGankCategory = (GankCategory) mCache.getAsObject("GankCategory");

        mLinearLayoutManager = new LinearLayoutManager(frmContext);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                mAdapter.setHasMoreDataAndFooter(true, true);
                getData("福利", current_page);
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

        mAdapter.setOnItemClickListener(new GankCategoryLoadAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = PictureActivity.newIntent(frmContext, mAdapter.getItem(position).url,
                        mAdapter.getItem(position).desc);
                startActivity(intent);

            }
        });
    }

    private void getData(String category, final int page) {
        RetrofitSingleton.getInstance(MyApplication.getInstance(), GankApi.BASE_URL)
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
                            if (page == 1 && gankCategory.results.size()<5) {
                                mGankCategory.results.addAll(gankCategory.results);
                                mCache.put("GankCategory", mGankCategory, 2 * ACache.TIME_DAY);
                            }

                            mAdapter.appendToList(gankCategory.results);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ShowToast("网络访问出错");
                        }


                    }
                });


    }

    private void putCategory() {
        Observable observable = Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                mCache.put("GankCategory", mGankCategory, 2 * ACache.TIME_DAY);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });

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
                getData("福利", page = 1);
            }
        });
//        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                swipe.setRefreshing(true);
//                getData("福利", page);
//            }
//        });
    }
}
