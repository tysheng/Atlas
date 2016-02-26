package shengtianyang.atlas.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2ThreadAdapter;
import shengtianyang.atlas.app.Constant;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.bean.HeaderBean;
import shengtianyang.atlas.bean.V2ReplyBean;
import shengtianyang.atlas.net.VolleyIF;
import shengtianyang.atlas.net.VolleyUtils;
import shengtianyang.atlas.utils.ItemDivider;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class V2ThreadFragment extends BaseFragment {

    @Bind(R.id.rc_thread)
    RecyclerView rcThread;

    private int id;
    //    private List<HashMap<String, String>> data;
    private V2ThreadAdapter adapter;
    private Bundle bundle;
    private List<V2ReplyBean> data;

    public V2ThreadFragment(int id) {
        this.id = id;
    }

    public HeaderBean getHeader() {
        HeaderBean headerBean = new HeaderBean();
        if (bundle != null) {
            headerBean.setDraweeTopic(bundle.getString("avatar_normal"));
            headerBean.setTvTopicAuthor(bundle.getString("username"));
            headerBean.setTvTopicNode(bundle.getString("node_title"));
            headerBean.setTvTopicContent(bundle.getString("content"));
            headerBean.setTvTopicTitle(bundle.getString("title"));
            headerBean.setTvTopicTime(bundle.getInt("last_modified"));
        }
        return headerBean;
    }

    private void getReply() {
        data = new ArrayList<>();
        adapter = new V2ThreadAdapter(frmContext, data, getHeader());
        rcThread.setAdapter(adapter);
        rcThread.setLayoutManager(new LinearLayoutManager(frmContext));
        rcThread.addItemDecoration(new ItemDivider(frmContext));

        VolleyUtils.Get(Constant.URL_V2_REPLY + "?topic_id=" + id, "V2ThreadFragment", new VolleyIF(VolleyIF.mListener) {
            @Override
            public void onMyResponse(String response) {
                data.addAll(JSON.parseArray(response, V2ReplyBean.class));
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("V2ThreadFragment");
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
