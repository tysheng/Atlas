package tysheng.atlas.activity;

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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.WeatherRVAdapter;
import tysheng.atlas.app.Constant;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.utils.SPHelper;


/**
 * Created by shengtianyang on 16/2/29.
 */
public class WeatherListActivity extends BaseActivity  {
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

    @Override
    public void initData() {
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

    private void getData(final String cityname, final int type) {

        StringRequest request = new StringRequest(Request.Method.POST, Constant.HF_WEATHER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    HashMap<String, String> map = new HashMap<>();
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.optJSONArray("HeWeather data service 3.0");
                    JSONObject object1 = array.optJSONObject(0);
                    if (object1.optString("status").equals("ok")) {
                        JSONObject basic = object1.optJSONObject("basic");
                        if (basic.optString("cnty").equals("中国")) {
                            JSONObject object2 = object1.optJSONObject("aqi");
                            if (object2 != null) {
                                JSONObject object3 = object2.optJSONObject("city");
                                String aqi = object3.optString("aqi");
                                String qlty = object3.optString("qlty");
                                map.put("aqi", aqi);
                                map.put("qlty", qlty);
                            } else {
                                map.put("ll_aqi", "ll_aqi");
                            }
                            JSONObject now = object1.optJSONObject("now");
                            JSONObject suggestion = object1.optJSONObject("suggestion");
                            JSONObject comf = suggestion.optJSONObject("comf");
                            JSONArray hourly_forecast = object1.optJSONArray("hourly_forecast");
                            JSONObject hourforecast = hourly_forecast.getJSONObject(0);

                            String brf = comf.optString("brf");
                            String txt = comf.optString("txt");
                            String tmp = hourforecast.optString("tmp");
                            String city_name = basic.optString("city");
                            String cond = now.optJSONObject("cond").optString("txt");
                            map.put("brf", brf);
                            map.put("city_name", city_name);
                            map.put("tmp", tmp + "°");
                            map.put("txt", txt);
                            map.put("cond", cond);
                            if (type == 1){
                                cities.add(cityname);
                                list.add(map);
                                adapter.notifyItemChanged(list.size());
                            } else {
                                for (int i = 0; i < cities.size(); i++) {
                                    if (cities.get(i).equals(cityname)){
                                        list.remove(i);
                                        list.add(i,map);
                                        count++;
                                        if (count >= sum){
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("apikey", Constant.HF_WEATHER_APIKEY);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("city", cityname);
                return params;
            }
        };
        request.setTag(cityname);
        MyApplication.getRequestQueue().add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
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
