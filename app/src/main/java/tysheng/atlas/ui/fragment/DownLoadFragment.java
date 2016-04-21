package tysheng.atlas.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.bean.FileInfo;
import tysheng.atlas.service.DownloadService;
import tysheng.atlas.service.MyService;
import tysheng.atlas.utils.kbv.KenBurnsView;

/**
 * Created by shengtianyang on 16/4/16.
 */
public class DownLoadFragment extends BaseFragment {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.kView)
    KenBurnsView mKenBurnsView;
    View view1;
    FileInfo mFileInfo;
    DownloadService mService;
    MyService mMyService;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /** 获取服务对象时的操作 */
            mService = ((DownloadService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            /** 意外,被强杀,无法获取到服务对象时的操作 */
            mService = null;

        }
    };

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadService.START)) {
            }
        }
    };

    @Bind(R.id.editText)
    EditText mEditText;
    @Bind(R.id.textLayout)
    TextInputLayout mTextLayout;
    @Bind(R.id.editText2)
    EditText mEditText2;
    @Bind(R.id.textLayout2)
    TextInputLayout mTextLayout2;
    private MyTextWatcher mTextWatcher;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_download;
    }

    @Override
    protected void initData() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.START);
        getActivity().registerReceiver(mReceiver, filter);
        mTextWatcher = new MyTextWatcher(mEditText2, mTextLayout2);
        mEditText.addTextChangedListener(new MyTextWatcher(mEditText, mTextLayout));
        mEditText2.addTextChangedListener(mTextWatcher);
        mKenBurnsView.setImageResource(R.drawable.screen);
    }

    class MyTextWatcher implements TextWatcher {
        public MyTextWatcher(EditText editText, TextInputLayout layout) {
            this.mEditText = editText;
            this.mLayout = layout;
        }

        private CharSequence charSequence;
        private int editStart;
        private int editEnd;
        private EditText mEditText;
        private TextInputLayout mLayout;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            charSequence = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = this.mEditText.getSelectionStart();
            editEnd = this.mEditText.getSelectionEnd();

//            if (charSequence.length() > 10) {
////                s.delete(editStart,editEnd);
//                mLayout.setErrorEnabled(true);
//                mLayout.setError("请检查格式");
//            } else {
//                mLayout.setErrorEnabled(false);
//            }
            switch (mEditText.getId()) {
                case R.id.editText2:
                    validatePassword();
                    break;
                default:
                    break;
            }
        }
    }

    private boolean validatePassword() {
        if (mEditText2.getText().toString().trim().length() > 8) {
            mTextLayout2.setErrorEnabled(true);
            mTextLayout2.setError("搓啊啊啊");
            mTextLayout2.refreshDrawableState();
            requestFocus(mEditText2);
            return false;
        } else {
            mTextLayout2.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @OnClick({R.id.start, R.id.stop, R.id.tv, R.id.go_on})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.start:
                AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(frmContext, R.anim.fade_in);
                alphaAnimation.setInterpolator(frmContext, android.R.anim.bounce_interpolator);
                imageView.startAnimation(alphaAnimation);
                break;
            case R.id.stop:
                ScaleAnimation zoomOutAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(frmContext, R.anim.scale);
                zoomOutAnimation.setInterpolator(frmContext, android.R.anim.bounce_interpolator);
                imageView.startAnimation(zoomOutAnimation);
                break;
            case R.id.tv:
                TranslateAnimation moveAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(frmContext, R.anim.translate);
                moveAnimation.setInterpolator(frmContext, android.R.anim.bounce_interpolator);
                imageView.startAnimation(moveAnimation);
                break;
            case R.id.go_on:
                AnimationSet set = (AnimationSet) AnimationUtils.loadAnimation(frmContext, R.anim.alpha_scale);
//                RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(frmContext, R.anim.rotate);
                set.setRepeatMode(Animation.REVERSE);
                set.setRepeatCount(Animation.INFINITE);
                imageView.startAnimation(set);
                break;


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

}
