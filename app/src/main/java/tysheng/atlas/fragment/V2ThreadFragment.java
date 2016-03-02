package tysheng.atlas.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.adapter.V2ThreadAdapter;
import tysheng.atlas.app.Constant;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.HeaderBean;
import tysheng.atlas.bean.V2ReplyBean;
import tysheng.atlas.net.VolleyIF;
import tysheng.atlas.net.VolleyUtils;
import tysheng.atlas.utils.ItemDivider;

/**
 * Created by shengtianyang on 16/2/1.
 */
@SuppressLint("ValidFragment")
public class V2ThreadFragment extends BaseFragment {

    @Bind(R.id.rc_thread)
    RecyclerView rcThread;

    public V2ThreadFragment() {
    }

    private int id;
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

        VolleyUtils.Get(Constant.URL_V2_REPLY + "?topic_id=" + id, "V2ThreadFragment", new VolleyIF(VolleyIF.mListener,VolleyIF.mErrorListener) {
            @Override
            public void onMyResponse(String response) {
                data.addAll(JSON.parseArray(response, V2ReplyBean.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onMyErrorResponse(VolleyError error) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("V2ThreadFragment");
    }

    @Override
    protected void setTitle() {

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
