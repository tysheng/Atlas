package tysheng.atlas.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

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
//        Log.d("sty",toString());
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
    protected void addFragment(@Nullable FragmentManager manager, @NonNull Fragment from, @NonNull Fragment to, @IdRes int id, String tag,  String backStackTag){
        doAddFragment(manager, from, to, id, tag, backStackTag);
    }
    protected void addFragment(@Nullable FragmentManager manager, @NonNull Fragment from, @NonNull Fragment to, @IdRes int id, String tag){
        doAddFragment(manager, from, to, id, tag,null);
    }

    private void doAddFragment(@Nullable FragmentManager manager, @NonNull Fragment from, @NonNull Fragment to, @IdRes int id, String tag, String backStackTag) {
        if (manager==null)
            manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .hide(from)
                .addToBackStack(backStackTag)
                .add(id, to, tag)
                .commit();
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
            toast = Toast.makeText(frmContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    protected void showSnackbar(View view, String msg){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        ViewGroup viewGroup = (ViewGroup) snackbar.getView();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof TextView)
                ((TextView) v).setTextColor(Color.WHITE);
        }
        snackbar.show();
    }
}
