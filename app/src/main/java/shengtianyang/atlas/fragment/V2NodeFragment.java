package shengtianyang.atlas.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2NodeRecyclerAdapter;
import shengtianyang.atlas.app.AtlasApp;
import shengtianyang.atlas.utils.Constant;

/**
 * Created by shengtianyang on 16/2/2.
 */
public class V2NodeFragment extends Fragment {
    @Bind(R.id.rv_v2node)
    RecyclerView rvV2node;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private HashMap<String, String> map;
    private V2NodeRecyclerAdapter mAdapter;
    List<HashMap<String, String>> data;
    @Bind(R.id.et_v2node)
    EditText etV2node;
    @Bind(R.id.btn_v2node)
    Button btnV2node;
    @Bind(R.id.rl_confirm)
    RelativeLayout rlConfirm;


    @OnClick(R.id.btn_v2node)
    void setBtnV2node() {
        editor = sharedPreferences.edit();
        if (!etV2node.getText().toString().equals(""))
            editor.putString("node", etV2node.getText().toString()).apply();
        StringRequest stringRequest = new StringRequest(Constant.URL_V2_NODE + sharedPreferences.getString("node", "android"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    map.put("url", object.optString("url"));
                    map.put("avatar_normal", object.optString("avatar_normal"));
                    map.put("header", object.optString("header"));
                    map.put("title", object.optString("title"));
                    map.put("topics", object.optString("topics"));
                    map.put("stars", object.optString("stars"));
                    mAdapter.addItem(data.size(), map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("V2NodeFragment");
        AtlasApp.getRequestQueue().add(stringRequest);
//        wvV2node.loadUrl(Constant.URL_V2_NODE + sharedPreferences.getString("node", "android"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v2node, container, false);
        sharedPreferences = getContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        ButterKnife.bind(this, view);
        map = new HashMap<String, String>();
        data = new ArrayList<HashMap<String, String>>();
        mAdapter = new V2NodeRecyclerAdapter(getContext(), data);
        rvV2node.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
//                mAdapter.addItem(data.size());
                break;
            case R.id.action_delete:
                mAdapter.deleteItem(data.size());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
