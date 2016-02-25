package shengtianyang.atlas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2HotAdapter;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class V2NewFragment extends BaseFragment {

    @Bind(R.id.rv_v2)
    RecyclerView rvV2;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    private V2HotAdapter mAdapter;
    private ArrayList<HashMap<String, String>> data;
    Fragment fragment;

    public V2NewFragment(String url) {
        this.url = url;
    }

    public static V2NewFragment getInstance(String url) {
        return new V2NewFragment(url);
    }

    private String url;

    private void getHotThread() {
        data = new ArrayList<HashMap<String, String>>();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("title", object.optString("title"));
                        map.put("avatar_normal", object.optJSONObject("member").optString("avatar_normal"));
                        map.put("username", object.optJSONObject("member").optString("username"));
                        map.put("node_title", object.optJSONObject("node").optString("title"));
                        map.put("last_modified", object.optString("last_modified"));
                        map.put("url", object.optString("url"));
                        map.put("id", object.optString("id"));
                        map.put("content", object.optString("content"));
                        map.put("replies", object.optString("replies"));
                        data.add(map);
                    }
                    mAdapter = new V2HotAdapter(frmContext, data);
                    mAdapter.setOnItemClickListener(new V2HotAdapter.OnItemClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {
                            Map<String, String> map = data.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("avatar_normal", map.get("avatar_normal"));
                            bundle.putString("username", map.get("username"));
                            bundle.putString("node_title", map.get("node_title"));
                            bundle.putString("content", map.get("content"));
                            bundle.putString("title", map.get("title"));
                            bundle.putString("last_modified", map.get("last_modified"));
                            V2ThreadFragment v2ThreadFragment = new V2ThreadFragment(data.get(position).get("id"));
                            v2ThreadFragment.setArguments(bundle);
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            fragment = manager.findFragmentByTag("new");
                            transaction.hide(fragment)
                                    .addToBackStack(null)
                                    .add(R.id.fg_main, v2ThreadFragment, "thread" + position)
                                    .commitAllowingStateLoss();
                        }
                    });
                    rvV2.setLayoutManager(new LinearLayoutManager(frmContext));
                    rvV2.setItemAnimator(new DefaultItemAnimator());
                    rvV2.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (swipe.isRefreshing())
                    swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("V2NewFragment");
        MyApplication.getRequestQueue().add(stringRequest);
    }

    private void initView() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHotThread();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2hot;
    }

    @Override
    protected void initData() {
        initView();
        getHotThread();
    }
}
