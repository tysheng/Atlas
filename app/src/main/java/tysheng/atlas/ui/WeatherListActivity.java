package tysheng.atlas.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tysheng.atlas.R;
import tysheng.atlas.adapter.WeatherRVAdapter;
import tysheng.atlas.api.MyRetrofit;
import tysheng.atlas.api.WeatherApi;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.bean.CityList;
import tysheng.atlas.bean.RWeatherBean;


/**
 * Created by shengtianyang on 16/2/29.
 */
public class WeatherListActivity extends BaseActivity {
    @BindView(R.id.rv_v2)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar mProgressBar;
//    MaterialDialog dialog;
    WeatherRVAdapter adapter;
    int sum = 0;
    int count = 0;
    List<String> cities;
    private long exitTime = 0;
    protected CompositeSubscription subscriber;
    Realm mRealm = Realm.getDefaultInstance();
    public static final int ADD = 1;
    public static final int NOT_ADD = 0;

    @Override
    public void initData() {
        subscriber = new CompositeSubscription();
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        realmQuery();
        mProgressBar.show();
        adapter = new WeatherRVAdapter(actContext, sum);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(actContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollingToBottom = dy > 0;
                if (fab != null) {
                    if (isScrollingToBottom) {
                        if (fab.isShown())
                            fab.hide();
                    } else {
                        if (!fab.isShown())
                            fab.show();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!fab.isShown())
                        fab.show();
                }
            }
        });
        firstGetData();

    }

    private void firstGetData() {
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(actContext)
//                .progress(true, 0)
//                .content(R.string.loading_chn)
//                .progressIndeterminateStyle(false);
//        dialog = builder.build();
//        dialog.show();
        for (int i = 0; i < sum; i++) {
            getData(cities.get(i), NOT_ADD);
        }
    }

    private WeatherRVAdapter.OnItemClickListener onItemClickListener = new WeatherRVAdapter.OnItemClickListener() {
        @Override
        public void onClickListener(View view, final int position) {
            new MaterialDialog.Builder(actContext)
                    .title("删除?")
                    .content("再次点击空白处可以取消.")
                    .positiveText("删除")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            realmDelete(position);
                            adapter.removeItem(position);
                        }
                    })
                    .show();
        }
    };

    private void realmWrite(final String cityName) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CityList cityList = realm.createObject(CityList.class);
                cityList.setName(cityName);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                showSnackbar(toolbar, "数据出错了:(");
            }
        });
    }

    private List<String> realmQuery() {
        RealmResults<CityList> results = mRealm.where(CityList.class)
                .findAll();
        cities = new ArrayList<>();
        for (CityList cityList : results) {
            cities.add(cityList.getName());
        }
        sum = results.size();
        return cities;
    }

    private void realmDelete(int position) {
        RealmResults<CityList> results = mRealm.where(CityList.class)
                .findAll();
        mRealm.beginTransaction();
        results.remove(position);
        mRealm.commitTransaction();
    }

    private void getData(final String cityName, final int type) {
        subscriber.add(
                MyRetrofit.getWeatherApi(MyApplication.getInstance(), WeatherApi.BASE_URL)
                        .getParams(cityName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<RWeatherBean, Boolean>() {
                            @Override
                            public Boolean call(RWeatherBean rWeatherBean) {
                                if (!rWeatherBean.getHeWeather().get(0).getStatus().equals("ok"))
                                    showSnackbar(toolbar, "请输入正确的城市名:(");
                                return rWeatherBean.getHeWeather().get(0).getStatus().equals("ok");
                            }
                        })
                        .map(new Func1<RWeatherBean, RWeatherBean.HeWeatherEntity>() {

                            @Override
                            public RWeatherBean.HeWeatherEntity call(RWeatherBean rWeatherBean) {
                                return rWeatherBean.getHeWeather().get(0);
                            }
                        })
                        .subscribe(new Subscriber<RWeatherBean.HeWeatherEntity>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mProgressBar.hide();
                            }

                            @Override
                            public void onNext(RWeatherBean.HeWeatherEntity heWeatherEntity) {
                                HashMap<String, String> map = new HashMap<>();
                                if (heWeatherEntity.getBasic().getCnty().equals("中国")) {
                                    if (heWeatherEntity.getAqi() == null) {
                                        map.put("ll_aqi", "ll_aqi");
                                    } else {
                                        map.put("aqi", heWeatherEntity.getAqi().getCity().getAqi());
                                        map.put("qlty", heWeatherEntity.getAqi().getCity().getQlty());
                                    }
                                    map.put("brf", heWeatherEntity.getSuggestion().getComf().getBrf());
                                    map.put("city_name", heWeatherEntity.getBasic().getCity());
                                    map.put("tmp", heWeatherEntity.getHourly_forecast().get(0).getTmp() + "°");
                                    map.put("txt", heWeatherEntity.getSuggestion().getComf().getTxt());
                                    map.put("cond", heWeatherEntity.getNow().getCond().getTxt());
                                    if (type == ADD) {
                                        adapter.addItem(map);
                                        realmWrite(cityName);
                                    } else {
                                        for (int i = 0; i < sum; i++) {
                                            if (cities.get(i).equals(cityName)) {
                                                adapter.removeItem(i);
                                                adapter.addItem(i, map);
                                                count++;
                                                if (count >= sum) {
//                                                    dialog.dismiss();

                                                }
                                            }
                                        }
                                    }
                                } else {
                                    showSnackbar(toolbar, "国外城市暂不提供:(");
                                }
                                mProgressBar.hide();
                            }
                        })
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        subscriber.unsubscribe();
        mProgressBar.onDetachedFromWindow();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_weather_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                if ((exitTime == 0 || System.currentTimeMillis() - exitTime > 8000)) {
                    firstGetData();
                    exitTime = System.currentTimeMillis();
                }
                return true;
            case R.id.action_info:
                new MaterialDialog.Builder(actContext)
                        .title("提示")
                        .content("某些城市暂无AQI信息查询\n刷新后间隔8秒才能进行下一次刷新")
                        .positiveText("知道了")
                        .show();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab)
    public void onClick() {
        new MaterialDialog.Builder(this)
                .title("请输入城市名")
                .content("中文或者拼音,仅支持国内城市")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(2, 18)
                .positiveText("OK")
                .input("", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (realmQuery().size() < 10)
                            getData(input.toString(), ADD);
                        else
                            showSnackbar(toolbar, "最多添加十个中国城市");
                    }
                }).show();
    }
}
