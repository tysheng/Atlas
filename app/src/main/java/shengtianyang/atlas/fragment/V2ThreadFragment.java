package shengtianyang.atlas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.ThreadHeader;
import shengtianyang.atlas.adapter.V2ThreadAdapter;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.utils.Constant;
import shengtianyang.atlas.utils.ItemDivider;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class V2ThreadFragment extends Fragment {

    @Bind(R.id.rc_thread)
    RecyclerView rcThread;

    private String id;
    private List<HashMap<String, String>> data;
    private V2ThreadAdapter adapter;
    private Context context;
    private Bundle bundle;

    public V2ThreadFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v2thread, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        bundle = getArguments();
        getReply();

        return view;
    }
    public ThreadHeader getHeader(){
        ThreadHeader threadHeader = new ThreadHeader();
        if (bundle != null) {
            threadHeader.setDraweeTopic(bundle.getString("avatar_normal"));
            threadHeader.setTvTopicAuthor(bundle.getString("username"));
            threadHeader.setTvTopicNode(bundle.getString("node_title"));
            threadHeader.setTvTopicContent(bundle.getString("content"));
            threadHeader.setTvTopicTitle(bundle.getString("title"));
            threadHeader.setTvTopicTime(bundle.getString("last_modified"));
        }
        return threadHeader;
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
                    adapter = new V2ThreadAdapter(context, data,getHeader());
                    rcThread.setAdapter(adapter);
                    rcThread.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    rcThread.addItemDecoration(new ItemDivider(context));
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

//    private void getTopic() {
//        StringRequest stringRequest = new StringRequest(Constant.URL_V2_TOPIC + "?id=" + id, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                getReply();
//                try {
//                    JSONArray array = new JSONArray(response);
//                    JSONObject object = array.optJSONObject(0);
//                    JSONObject member = object.optJSONObject("member");
//                    draweeTopic.setImageURI(Uri.parse("http:" + member.optString("avatar_normal")));
//                    tvTopicAuthor.setText(member.optString("username"));
//                    tvTopicNode.setText(object.optJSONObject("menu_node").optString("title"));
//                    tvTopicContent.setText(object.optString("content"));
//                    tvTopicTitle.setText(object.optString("title"));
//                    String time = TimeStamp.getTimeDifference(Integer.parseInt(object.optString("last_modified")));
//                    if (time.equals("0")) {
//                        time = "刚刚";
//                    } else if (time.equals("-1")) {
//                        time = "很久很久前";
//                    }
//                    tvTopicTime.setText(time);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        stringRequest.setTag("V2ThreadFragment1");
//        MyApplication.getRequestQueue().toolbar_add(stringRequest);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
