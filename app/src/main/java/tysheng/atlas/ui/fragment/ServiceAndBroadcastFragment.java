package tysheng.atlas.ui.fragment;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.service.DownloadService;
import tysheng.atlas.service.MyService;

/**
 * Created by shengtianyang on 16/3/25.
 */
public class ServiceAndBroadcastFragment extends BaseFragment {

    MyService.MyBinder mBinder;
    @BindView(R.id.bind)
    Button mBind;

    Intent intent;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_io;
    }

    @Override
    protected void initData() {
//        Glide.with(frmContext)
//                .load("http://i3.hoopchina.com.cn/blogfile/201604/04/BbsImg145974633089957_339x476.gif")
//                .asGif()
//                .into(circle)
//        ;

        intent = new Intent(getActivity(), MyService.class);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.START);
        getActivity().registerReceiver(mReceiver, filter);


    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /** 获取服务对象时的操作 */
            mBinder = (MyService.MyBinder) service;
            System.out.println("onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            /** 意外,被强杀,无法获取到服务对象时的操作 */
            System.out.println("onServiceDisconnected");
            mBinder = null;

        }

    };
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadService.START)) {
                showSnackbar(mBind, "get broadcast");
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @OnClick({R.id.bind, R.id.unbind, R.id.start, R.id.stop, R.id.circle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind:
                getActivity().bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                getActivity().unbindService(mConnection);
                mBinder = null;
                break;
            case R.id.start:
                getActivity().startService(intent);
                break;
            case R.id.stop:
                getActivity().stopService(intent);
                break;
            case R.id.circle:
                if (mBinder != null)
                    mBinder.callMethod1();
                else
                showSnackbar(mBind,"没有收到消息");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }
}
