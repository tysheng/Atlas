package tysheng.atlas.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import tysheng.atlas.utils.SPHelper;

/**
 * Created by shengtianyang on 16/2/22.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context actContext;
    protected Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SPHelper.getTheme(this));
        setContentView(getLayoutId());
        if (savedInstanceState != null) {
//            restoreFragments();
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        actContext = this;
        ButterKnife.bind(this);
        initData();


    }

    private void restoreFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            boolean showFlag = false;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (int i = fragments.size() - 1; i >= 0; i--) {
                Fragment fragment = fragments.get(i);
                if (fragment != null) {
                    if (!showFlag) {
                        ft.show(fragments.get(i));
                        showFlag = true;
                    } else {
                        ft.hide(fragments.get(i));
                    }
                }
            }
            ft.commit();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * setContentView
     */
    public abstract int getLayoutId();

    /**
     * activity之间的跳转
     *
     * @param clazz    目标activity
     * @param isfinish 是否关闭
     */
    protected void jumpActivity(Class clazz, boolean isfinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isfinish) {
            this.finish();
        }
    }

    /**
     * Fragment之间的切换
     *
     * @param from 当前
     * @param to   目标
     * @param id
     * @param tag
     */
    protected void jumpFragment(Fragment from, Fragment to, int id, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        doJumpFragment(manager, from, to, id, tag);
    }

    protected void jumpFragment(FragmentManager manager, Fragment from, Fragment to, int id, String tag) {
        doJumpFragment(manager, from, to, id, tag);
    }

    private void doJumpFragment(FragmentManager manager, Fragment from, Fragment to, int id, String tag) {
        if (to == null) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if (from == null) {
            transaction.add(id, to, tag);
        } else {
            transaction.hide(from);
            if (to.isAdded()) {
                transaction.show(to);
            } else {
                transaction.add(id, to, tag);
            }
        }
        transaction
//                .setCustomAnimations(0, 0, R.anim.abc_fade_in, R.anim.abc_fade_out)
                .commitAllowingStateLoss();
    }

    protected void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(actContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    protected void showSnackbar(View view, String msg) {
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
