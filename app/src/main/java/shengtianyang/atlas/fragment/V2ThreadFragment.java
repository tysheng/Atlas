package shengtianyang.atlas.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import shengtianyang.atlas.R;
import shengtianyang.atlas.bean.HeaderBean;
import shengtianyang.atlas.adapter.V2ThreadAdapter;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.app.Constant;
import shengtianyang.atlas.utils.ItemDivider;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class V2ThreadFragment extends BaseFragment {

    @Bind(R.id.rc_thread)
    RecyclerView rcThread;

    private String id;
    private List<HashMap<String, String>> data;
    private V2ThreadAdapter adapter;
    private Bundle bundle;

    public V2ThreadFragment(String id) {
        this.id = id;
    }
    
    public HeaderBean getHeader(){
        HeaderBean headerBean = new HeaderBean();
        if (bundle != null) {
            headerBean.setDraweeTopic(bundle.getString("avatar_normal"));
            headerBean.setTvTopicAuthor(bundle.getString("username"));
            headerBean.setTvTopicNode(bundle.getString("node_title"));
            headerBean.setTvTopicContent(bundle.getString("content"));
            headerBean.setTvTopicTitle(bundle.getString("title"));
            headerBean.setTvTopicTime(bundle.getString("last_modified"));
        }
        return headerBean;
    }
    
    private void getReply() {
        data = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Constant.URL_V2_REPLY + "?topic_id=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject member = object.optJSONObject("member");
                        map.put("avatar_normal", member.optString("avatar_normal"));
                        map.put("username", member.optString("username"));
                        map.put("last_modified", object.optString("last_modified"));
                        map.put("content", object.optString("content"));
                        data.add(map);
                    }
                    adapter = new V2ThreadAdapter(frmContext, data,getHeader());
                    rcThread.setAdapter(adapter);
                    rcThread.setLayoutManager(new LinearLayoutManager(frmContext, LinearLayoutManager.VERTICAL, false));
                    rcThread.addItemDecoration(new ItemDivider(frmContext));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setTag("V2ThreadFragment2");
        MyApplication.getRequestQueue().add(stringRequest);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2thread;
    }

    @Override
    protected void initData() {
        bundle = getArguments();
        getReply();
    }
}
