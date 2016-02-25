package shengtianyang.atlas.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
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
import java.util.List;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2NodeRecyclerAdapter;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/2/2.
 */
public class V2NodeFragment extends BaseFragment {
    @Bind(R.id.rv_v2node)
    RecyclerView rvV2node;
    V2NodeRecyclerAdapter mAdapter;
    List<HashMap<String, String>> data;
    Fragment v2NodeFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2node;
    }

    public static V2NodeFragment getInstance() {
        return new V2NodeFragment();
    }


    @Override
    protected void initData() {

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
                        map.put("title_alternative", object.optString("title_alternative"));
//                        map.put("topics",object.optString("topics"));
//                        map.put("header",object.optString("header"));
                        map.put("url", object.optString("url"));
                        data.add(map);
                    }
                    mAdapter = new V2NodeRecyclerAdapter(frmContext, data);
                    mAdapter.setOnItemClickListener(new V2NodeRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {
//                            getFragmentManager().beginTransaction()
//                                    .addToBackStack(null)
//                                    .add(R.id.fg_main, new WebviewFragment(data.get(position).get("url")))
//                                    .commit();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            v2NodeFragment = manager.findFragmentByTag("node");
                            WebviewFragment webviewFragment = new WebviewFragment(data.get(position).get("url"));
                            transaction.hide(v2NodeFragment)
                                    .addToBackStack(null)
                                    .add(R.id.fg_main, webviewFragment, "nodeweb" + position)
                                    .commitAllowingStateLoss();


                        }
                    });
                    rvV2node.setLayoutManager(new GridLayoutManager(frmContext, 2));
                    rvV2node.setAdapter(mAdapter);
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
    }

}
