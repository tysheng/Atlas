package tysheng.atlas.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.adapter.V2ThreadAdapter;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.api.V2exApi;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.HeaderBean;
import tysheng.atlas.bean.V2HotBean;
import tysheng.atlas.bean.V2ReplyBean;
import tysheng.atlas.utils.ItemDivider;


/**
 * Created by shengtianyang on 16/2/1.
 */
public class V2ThreadFragment extends BaseFragment {
    public static final String TAG = V2ThreadFragment.class.getSimpleName();
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
            V2HotBean bean = (V2HotBean) bundle.getSerializable(TAG);
            if (bean != null) {
                headerBean.setDraweeTopic(bean.getMember().getAvatar_normal());
                headerBean.setTvTopicAuthor(bean.getMember().getUsername());
                headerBean.setTvTopicNode(bean.getNode().getTitle());
                headerBean.setTvTopicContent(bean.getContent());
                headerBean.setTvTopicTitle(bean.getTitle());
                headerBean.setTvTopicTime(bean.getLast_modified());
            }
        }
        return headerBean;
    }

    private void getReply() {
        data = new ArrayList<>();
        adapter = new V2ThreadAdapter(frmContext, data, getHeader());
        rcThread.setAdapter(adapter);
        rcThread.setLayoutManager(new LinearLayoutManager(frmContext));
        rcThread.addItemDecoration(new ItemDivider(frmContext));
        getData();
    }
    private void getData() {
        subscriber.add(
                RetrofitSingleton.getV2exApi(MyApplication.getInstance(), V2exApi.BASE_URL)
                        .getV2Reply(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<V2ReplyBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                showSnackbar(rcThread, getString(R.string.on_error));
                            }

                            @Override
                            public void onNext(List<V2ReplyBean> v2ReplyBeen) {
                                adapter.addItem(v2ReplyBeen);
                            }
                        })
        );
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
                sendToWx(bundle.getString("url"), bundle.getString("title"), bundle.getString("content"), 0);
                return true;
            case R.id.action_timeline:
                sendToWx(bundle.getString("url"), bundle.getString("title"), bundle.getString("content"), 1);
                return true;
            case R.id.action_favorite:
                sendToWx(bundle.getString("url"), bundle.getString("title"), bundle.getString("content"), 2);
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
        bundle = getArguments();
        getReply();
    }

    private void sendToWx(String url, String title, String content, int mode) {
        WXWebpageObject web = new WXWebpageObject(url);
        final WXMediaMessage msg = new WXMediaMessage(web);
        msg.title = title;
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

}
