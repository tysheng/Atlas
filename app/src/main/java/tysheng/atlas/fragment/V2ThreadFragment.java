package tysheng.atlas.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

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
import tysheng.atlas.presenter.GetPresenter;
import tysheng.atlas.presenter.GetPresenterImpl;
import tysheng.atlas.presenter.VolleyView;
import tysheng.atlas.utils.ItemDivider;


/**
 * Created by shengtianyang on 16/2/1.
 */
public class V2ThreadFragment extends BaseFragment implements VolleyView {

    @Bind(R.id.rc_thread)
    RecyclerView rcThread;
    private GetPresenter presenter;
    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll(getClass().getSimpleName());
    }

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

        presenter.getData(Constant.URL_V2_REPLY + "?topic_id=" + id,getClass().getSimpleName());
    }
    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_wxshare, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_session:
                sendToWx(bundle.getString("url"),bundle.getString("title"),bundle.getString("content"),0);
                return true;
            case R.id.action_timeline:
                sendToWx(bundle.getString("url"),bundle.getString("title"),bundle.getString("content"),1);
                return true;
            case R.id.action_favorite:
                sendToWx(bundle.getString("url"),bundle.getString("title"),bundle.getString("content"),2);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        setHasOptionsMenu(true);
        presenter = new GetPresenterImpl(this);
        bundle = getArguments();
        getReply();
    }

    private void sendToWx(String url,String title,String content, int mode)  {
        WXWebpageObject web = new WXWebpageObject(url);
        final WXMediaMessage msg = new WXMediaMessage(web);
        msg.title=title;
        msg.description = content;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (mode == 0)
            req.scene = SendMessageToWX.Req.WXSceneSession;
        else if (mode == 1)
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        else if (mode == 2)
            req.scene = SendMessageToWX.Req.WXSceneFavorite;

        MyApplication.getWxApi().sendReq(req);
    }

    @Override
    public void onSuccessResponse(String response) {
        data.addAll(JSON.parseArray(response, V2ReplyBean.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailResponse(VolleyError error) {

    }
}
