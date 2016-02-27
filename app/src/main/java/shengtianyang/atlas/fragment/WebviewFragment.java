package shengtianyang.atlas.fragment;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/2/1.
 */
@SuppressLint("ValidFragment")
public class WebviewFragment extends BaseFragment {
    @Bind(R.id.webview)
    WebView webview;

    private String url;
    public WebviewFragment(String url) {
        this.url = url;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void initData() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(frmContext)
                .progress(true, 0)
                .content(R.string.please_wait)

                .progressIndeterminateStyle(false);

        final MaterialDialog dialog = builder.build();
//        dialog.setCancelable(false);
        dialog.show();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress>=60){
                    dialog.dismiss();
                }

            }
            
        });
        webview.loadUrl(url);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
//            webview.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
