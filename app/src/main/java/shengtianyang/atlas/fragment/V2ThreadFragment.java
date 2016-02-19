package shengtianyang.atlas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2ThreadAdapter;
import shengtianyang.atlas.app.AtlasApp;
import shengtianyang.atlas.utils.Constant;
import shengtianyang.atlas.utils.ItemDivider;
import shengtianyang.atlas.utils.TimeStamp;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class V2ThreadFragment extends Fragment {

    @Bind(R.id.drawee_topic)
    SimpleDraweeView draweeTopic;
    @Bind(R.id.tv_topic_author)
    TextView tvTopicAuthor;
    @Bind(R.id.tv_topic_node)
    TextView tvTopicNode;
    @Bind(R.id.tv_topic_time)
    TextView tvTopicTime;
    @Bind(R.id.tv_topic_content)
    TextView tvTopicContent;
    @Bind(R.id.tv_topic_title)
    TextView tvTopicTitle;
    @Bind(R.id.rc_thread)
    RecyclerView rcThread;
    @Bind(R.id.recyclerviewheader)
    RecyclerViewHeader recyclerviewheader;
    private String id;
    private List<HashMap<String, String>> data;
    private V2ThreadAdapter adapter;
    private Context context;

    public V2ThreadFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v2thread, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        Bundle bundle = getArguments();
        if (bundle != null) {
            draweeTopic.setImageURI(Uri.parse("http:" + bundle.getString("avatar_normal")));
            tvTopicAuthor.setText(bundle.getString("username"));
            tvTopicNode.setText(bundle.getString("node_title"));
            tvTopicContent.setText(bundle.getString("content"));
            tvTopicTitle.setText(bundle.getString("title"));
            String time = TimeStamp.getTimeDifference(Integer.parseInt(bundle.getString("last_modified")));
                    if (time.equals("0")) {
                        time = "刚刚";
                    } else if (time.equals("-1")) {
                        time = "很久很久前";
                    }
                    tvTopicTime.setText(time);
        }
        getReply();

        return view;
    }

    private void getReply() {
        data = new ArrayList<HashMap<String, String>>();
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
                    adapter = new V2ThreadAdapter(context, data);
                    rcThread.setAdapter(adapter);
                    rcThread.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    rcThread.addItemDecoration(new ItemDivider(context));
                    recyclerviewheader.attachTo(rcThread,true);
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
        AtlasApp.getRequestQueue().add(stringRequest);
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
//                    tvTopicNode.setText(object.optJSONObject("node").optString("title"));
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
//        AtlasApp.getRequestQueue().add(stringRequest);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
