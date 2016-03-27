package tysheng.atlas.ui.activity;

import android.support.annotation.NonNull;
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

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tysheng.atlas.R;
import tysheng.atlas.adapter.WeatherRVAdapter;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.bean.RWeatherBean;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.api.WeatherApi;
import tysheng.atlas.utils.SPHelper;


/**
 * Created by shengtianyang on 16/2/29.
 */
public class WeatherListActivity extends BaseActivity {
    @Bind(R.id.rv_v2)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    MaterialDialog dialog;
    List<HashMap<String, String>> list;
    WeatherRVAdapter adapter;
    int sum;
    int count;
    ArrayList<String> cities;
    private long exitTime = 0;
    protected CompositeSubscription subscriber;

    @Override
    public void initData() {
        subscriber = new CompositeSubscription();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        count = 0;
        sum = SPHelper.getCitySum(actContext);
        cities = SPHelper.getCities(actContext, sum);
        initList();
        adapter = new WeatherRVAdapter(actContext, list);
        adapter.setOnItemClickListener(new WeatherRVAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, final int position) {
                new MaterialDialog.Builder(actContext)
                        .title("删除?")
                        .content("再次点击空白处可以取消.")
                        .positiveText("删除")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                cities.remove(position);
                                list.remove(position);
                                adapter.notifyItemRemoved(position);
                            }
                        })
                        .show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(actContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        reGetData();

    }

    private void reGetData() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(actContext)
                .progress(true, 0)
                .content(R.string.loading_chn)
                .progressIndeterminateStyle(false);
        dialog = builder.build();
        dialog.show();
        for (int i = 0; i < sum; i++) {
            getData(cities.get(i), 0);
        }
    }

    private void getData(final String cityName, final int type) {
        subscriber.add(
        RetrofitSingleton.getWeatherApi(MyApplication.getInstance(), WeatherApi.BASE_URL)
                .getParams(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<RWeatherBean, Boolean>() {
                    @Override
                    public Boolean call(RWeatherBean rWeatherBean) {
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

                    }

                    @Override
                    public void onNext(RWeatherBean.HeWeatherEntity heWeatherEntity) {
                        HashMap<String, String> map = new HashMap<>();
                        if (heWeatherEntity.getBasic().getCnty().equals("中国")) {
                            if (heWeatherEntity.getAqi() == null){
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
                            if (type == 1) {
                                cities.add(cityName);
                                list.add(map);
                                adapter.notifyItemChanged(list.size());
                            } else {
                                for (int i = 0; i < cities.size(); i++) {
                                    if (cities.get(i).equals(cityName)) {
                                        list.remove(i);
                                        list.add(i, map);
                                        count++;
                                        if (count >= sum) {
                                            dialog.dismiss();
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        } else {
                            ShowToast("请输入正确的城市名");
                        }

                    }
                })
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
        subscriber.unsubscribe();
    }

    private void saveData() {
        SPHelper.setCitySum(actContext, cities.size());
        SPHelper.setCities(actContext, cities);
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
            case R.id.action_add:
                new MaterialDialog.Builder(this)
                        .title("请输入城市名")
                        .content("中文或者拼音,仅支持国内城市")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .inputRange(2, 18)
                        .positiveText("OK")
                        .input("", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                if (cities.size() < 10)
                                    getData(input.toString(), 1);
                                else
                                    ShowToast("最多添加10个城市");
                            }
                        }).show();
                return true;
            case R.id.action_refresh:
                if ((exitTime == 0 || System.currentTimeMillis() - exitTime > 8000)) {
                    sum = cities.size();
                    initList();
                    saveData();
                    reGetData();
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

    private void initList() {
        list = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            HashMap<String, String> map = new HashMap<>();
            list.add(map);
        }
    }

}
