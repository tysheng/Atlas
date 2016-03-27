package tysheng.atlas.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;
import tysheng.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/2/22.
 */
public abstract class BaseFragment extends Fragment{
    protected View rootView;
    protected Context frmContext;
    protected Toast toast;
    protected CompositeSubscription subscriber;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.frmContext = getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        subscriber = new CompositeSubscription();

        initData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setTitle();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    protected abstract void setTitle();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscriber.unsubscribe();
        ButterKnife.unbind(this);
    }
    protected abstract int getLayoutId();
    protected abstract void initData();
    protected void ShowToast(String msg){
        if (toast == null) {
            toast = Toast.makeText(frmContext.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
