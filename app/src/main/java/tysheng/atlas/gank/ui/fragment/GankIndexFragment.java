package tysheng.atlas.gank.ui.fragment;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.gank.adapter.GankDailyAdapter;
import tysheng.atlas.gank.api.GankApi;
import tysheng.atlas.gank.bean.GankCategory;
import tysheng.atlas.gank.ui.DailyDetailActivity;
import tysheng.atlas.gank.ui.PictureActivity;
import tysheng.atlas.utils.ACache;

/**
 * Created by shengtianyang on 16/4/7.
 */
public class GankIndexFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    ACache mCache;
    GankCategory gankCategory;
    @Bind(R.id.cl)
    CoordinatorLayout cl;
    int page = 1;
    GankDailyAdapter mAdapter;
    final int REFRESH = 0;
    final int MORE = 1;

    @Override
    protected void setTitle() {
        getActivity().setTitle("Daily");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initData() {
        mCache = ACache.get(frmContext);
        gankCategory = (GankCategory) mCache.getAsObject("index_category");
        if (gankCategory == null)
            gankCategory = new GankCategory();
        mAdapter = new GankDailyAdapter(frmContext, gankCategory);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(frmContext));
        setItemClick();
        getData(REFRESH,page);
    }

    private void getData(final int type, int page) {
        subscriber.add(RetrofitSingleton.getInstance(MyApplication.getInstance(), GankApi.BASE_URL)
                        .create(GankApi.class)
                        .getParams("福利", 10, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<GankCategory>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(GankCategory bean) {
                                if (!bean.error) {
                                    if (type == REFRESH) {
                                        mAdapter.clear();
                                        mAdapter.add(bean);
                                    } else {
                                        mAdapter.add(bean);
                                    }

                                } else
                                    showSnackbar(cl, "获取出错...");
                            }
                        })
        );
    }

    @OnClick(R.id.fab)
    public void onClick() {
        getData(MORE, ++page);
    }

    private void setItemClick() {
        mAdapter.setOnItemClickListener(new GankDailyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if (view.getId() == R.id.image) {
                    Intent intent = PictureActivity.newIntent(frmContext, gankCategory.results.get(position).url,
                            gankCategory.results.get(position).desc);
                    startActivity(intent);
                } else if (view.getId() == R.id.tv_date) {
                    Intent intent = new Intent(frmContext, DailyDetailActivity.class);
                    intent.putExtra(DailyDetailActivity.DATE,mAdapter.getYMD(position));
                    intent.putExtra(DailyDetailActivity.URL,mAdapter.getUrl(position));
                    startActivity(intent);
//
                }
            }
        });
    }
}
