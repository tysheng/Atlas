package shengtianyang.atlas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2HotAdapter;
import shengtianyang.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class V2HotFragment extends Fragment {

    @Bind(R.id.rv_v2)
    RecyclerView rvV2;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    private V2HotAdapter mAdapter;
    private ArrayList<HashMap<String, String>> data;

    public V2HotFragment(String url) {
        this.url = url;
    }

    private String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v2hot, container, false);
        ButterKnife.bind(this, view);
        initView();
        getHotThread();

        return view;
    }

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
                    mAdapter = new V2HotAdapter(getContext(), data);
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
                            getFragmentManager().beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.fg_main, v2ThreadFragment)
                                    .commit();
                        }
                    });
                    rvV2.setAdapter(mAdapter);
//        设置布局
                    rvV2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

//        设置Item动画效果
                    rvV2.setItemAnimator(new DefaultItemAnimator());

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
        stringRequest.setTag("V2HotFragment");
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
