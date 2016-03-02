package tysheng.atlas.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/2/1.
 */
@SuppressLint("ValidFragment")
public class WebviewFragment extends BaseFragment {
    @Bind(R.id.webview)
    WebView webview;
    private MaterialDialog dialog;
    public WebviewFragment() {
    }

    private String url;

    public WebviewFragment(String url) {
        this.url = url;
    }

    @Override
    protected void setTitle() {

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

        dialog = builder.build();
//        dialog.setCancelable(false);
        dialog.show();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 60) {
                    dialog.dismiss();
                }

            }

        });
        webview.loadUrl(url);
    }



    @OnClick({R.id.ib_web_refresh, R.id.ib_web_back, R.id.ib_web_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_web_refresh:
                webview.reload();
                break;
            case R.id.ib_web_back:
                if (webview.canGoBack())
                    webview.goBack();
                break;
            case R.id.ib_web_go:
                if (webview.canGoForward())
                    webview.goForward();
                break;
        }
    }


}
