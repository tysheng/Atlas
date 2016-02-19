package shengtianyang.atlas.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.RecyclerAdapter;
import shengtianyang.atlas.app.AtlasApp;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class V2HotFragment extends Fragment {

    @Bind(R.id.rv_v2)
    RecyclerView rvV2;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    private RecyclerAdapter mAdapter;
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
                        map.put("member", object.optString("member"));
                        map.put("node", object.optString("node"));
                        map.put("last_touched", object.optString("last_touched"));
                        map.put("url", object.optString("url"));
                        map.put("content", object.optString("content"));
                        data.add(map);
                    }
                    mAdapter = new RecyclerAdapter(getContext(), data);
                    mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {
                            getFragmentManager().beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.fg_main, new WebviewFragment(data.get(position).get("url")))
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
        AtlasApp.getRequestQueue().add(stringRequest);
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
