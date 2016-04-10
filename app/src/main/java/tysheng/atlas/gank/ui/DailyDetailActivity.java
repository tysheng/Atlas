package tysheng.atlas.gank.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.adapter.GankDailyDetailAdapter;
import tysheng.atlas.gank.api.GankApi;
import tysheng.atlas.gank.bean.GankDaily;
import tysheng.atlas.gank.view.SectionsDecoration;

/**
 * Created by shengtianyang on 16/4/7.
 */
public class DailyDetailActivity extends BaseActivity {
    @Bind(R.id.iv_head)
    SimpleDraweeView ivHead;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_gank)
    RecyclerView recyclerView;
    public static final String DATE = "date";
    public static final String URL = "url";
    GankDailyDetailAdapter mAdapter;
    String[] date;
    String url;
    GankDaily mDaily;

    @Override
    public void initData() {
        date = getIntent().getStringArrayExtra(DATE);
        url = getIntent().getStringExtra(URL);
        ivHead.setImageURI(Uri.parse(url));
        toolbar.setTitle(date[1] + "/" + date[2]);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDaily = new GankDaily();
        mAdapter = new GankDailyDetailAdapter(actContext, mDaily.results.androidList);
        mAdapter.setOnItemClickListener(new GankDailyDetailAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Intent intent = new Intent(actContext, WebviewActivity.class);
                intent.putExtra(WebviewActivity.URL, mAdapter.getDataItem(position).url);
                intent.putExtra(WebviewActivity.TITLE, mAdapter.getDataItem(position).desc);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(actContext));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new SectionsDecoration(true));
        getData(date);
    }

    private void getData(String[] date) {
        RetrofitSingleton.getInstance(actContext, GankApi.BASE_URL)
                .create(GankApi.class)
                .getParams(date[0], date[1], date[2]).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showSnackbar(toolbar, "数据有点不太对劲:(");
                    }

                    @Override
                    public void onNext(GankDaily gankDaily) {
                        mAdapter.add(gankDaily);

                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_daily_detail;
    }


    @OnClick(R.id.fab)
    public void onClick() {
        if (mAdapter.getDataItem(0)!=null&&mAdapter.getDataItem(0).type.equals("休息视频")){
            Intent intent = new Intent(DailyDetailActivity.this, WebVideoActivity.class);
            intent.putExtra(WebVideoActivity.URL, mAdapter.getDataItem(0).url);
            startActivity(intent);
        }else {
            showSnackbar(toolbar,"今天的视频好像出了点问题:(");
        }
    }
}
