package tysheng.atlas.gank.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.api.MyRetrofit;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.gank.adapter.EndlessRecyclerOnScrollListener;
import tysheng.atlas.gank.adapter.GankCategoryAdapter;
import tysheng.atlas.gank.api.GankApi;
import tysheng.atlas.gank.bean.GankCategory;
import tysheng.atlas.gank.bean.GankResult;
import tysheng.atlas.gank.ui.PictureActivity;
import tysheng.atlas.gank.ui.WebviewActivity;
import tysheng.atlas.gank.view.SectionsDecoration;
import tysheng.atlas.utils.ACache;


/**
 * Created by shengtianyang on 16/3/26.
 */
public class GankCategoryFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    GankCategoryAdapter mAdapter;

    GankCategory mGankCategory;
    private List<GankResult> data;
    int page = 1;
    ACache mCache;
    String typeName;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void setTitle() {
        getActivity().setTitle("Gank");
    }

    public GankCategoryFragment() {
    }

    public GankCategoryFragment(String typeName) {
        this.typeName = typeName;
    }

    public static GankCategoryFragment newInstance(String typeName) {
        return new GankCategoryFragment(typeName);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initData() {
        initSwipe();
        mCache = ACache.get(frmContext);
        mGankCategory = (GankCategory) mCache.getAsObject(typeName);
        mLayoutManager = new LinearLayoutManager(frmContext);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        if (!typeName.equals("福利"))
            rv.addItemDecoration(new SectionsDecoration(true));
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getData(typeName, current_page);
            }
        });
        if (mGankCategory == null) {
            mGankCategory = new GankCategory();
            data = new ArrayList<>();
            mAdapter = new GankCategoryAdapter(frmContext, data);
            rv.setAdapter(mAdapter);
        } else {
            data.addAll(mGankCategory.results.subList(0, 9));
            mAdapter = new GankCategoryAdapter(frmContext, data);
            rv.setAdapter(mAdapter);
        }

        setItemClick();


    }


    private void setItemClick() {
        mAdapter.setOnItemClickListener(new GankCategoryAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                if (data.get(position).type.equals("福利")) {
                    Intent intent = PictureActivity.newIntent(frmContext, data.get(position).url,
                            data.get(position).desc);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(frmContext, WebviewActivity.class);
                    intent.putExtra(WebviewActivity.URL, data.get(position).url);
                    intent.putExtra(WebviewActivity.TITLE, data.get(position).desc);
                    startActivity(intent);
                }
            }
        });

    }

    private void getData(String category, final int page) {
        mSubscription.add(MyRetrofit.getGankApi(MyApplication.getInstance(), GankApi.BASE_URL)
                .getCategory(category, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankCategory>() {
                    @Override
                    public void onCompleted() {
                        stopSwipe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        stopSwipe();
                    }

                    @Override
                    public void onNext(GankCategory gankCategory) {
                        if (gankCategory.results.isEmpty()) {
                            ShowToast("没有更多数据了~");
                            return;
                        }

                        if (!gankCategory.error) {
                            if (page == 1 && gankCategory.results.size() < 5) {
                                mGankCategory.results.addAll(gankCategory.results);
                                mCache.put(typeName, mGankCategory, 2 * ACache.TIME_DAY);
                            }

                            data.addAll(gankCategory.results);
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


    private void initSwipe() {
        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                swipe.setRefreshing(true);
                getData(typeName, page = 1);
            }
        });
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                getData(typeName, page = 1);
            }
        });
    }


}
