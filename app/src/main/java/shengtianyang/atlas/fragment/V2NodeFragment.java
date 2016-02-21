package shengtianyang.atlas.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2NodeRecyclerAdapter;
import shengtianyang.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/2/2.
 */
public class V2NodeFragment extends Fragment {
    @Bind(R.id.rv_v2node)
    RecyclerView rvV2node;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    V2NodeRecyclerAdapter mAdapter;
    List<HashMap<String, String>> data;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v2node, container, false);
//        sharedPreferences = getContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        ButterKnife.bind(this, view);
        data = new ArrayList<>();
        StringRequest request = new StringRequest("https://www.v2ex.com/api/nodes/all.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < 120; i++) {
                        JSONObject object = array.optJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
//                        map.put("name",object.optString("name"));
                        map.put("title_alternative",object.optString("title_alternative"));
//                        map.put("topics",object.optString("topics"));
//                        map.put("header",object.optString("header"));
                        map.put("url",object.optString("url"));
                        data.add(map);
                    }
                    mAdapter = new V2NodeRecyclerAdapter(getContext(),data);
                    mAdapter.setOnItemClickListener(new V2NodeRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {
                            getFragmentManager().beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.fg_main, new WebviewFragment(data.get(position).get("url")))
                                    .commit();
                        }
                    });
                    rvV2node.setAdapter(mAdapter);
                    rvV2node.setLayoutManager(new GridLayoutManager(getContext(),2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setTag("V2NodeFragment");
        MyApplication.getRequestQueue().add(request);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
