package tysheng.atlas.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.gank.utils.GankUtils;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class WebviewFragment extends BaseFragment {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public static final String URL = "URL";
    public static final String TITLE="TITLE";
    private String mUrl;
    private String mTitle = "";

    public static WebviewFragment newInstance(String url, String title) {
        WebviewFragment fragment = new WebviewFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(TITLE, title);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(mTitle);
    }
    private void parseArguments() {
        Bundle bundle = getArguments();
        mUrl = bundle.getString(URL);
        mTitle = bundle.getString(TITLE);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void initData() {
        parseArguments();
        setHasOptionsMenu(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar != null) {
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                        progressBar.setProgress(0);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(newProgress);
                    }
                }
            }

        });

        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键 时的操作
                        webview.goBack();   //后退
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
        webview.loadUrl(mUrl);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_webview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                GankUtils.share(frmContext, mUrl, mTitle);
                return true;
            case R.id.action_back:
                if (webview.canGoBack())
                    webview.goBack();
                return true;
            case R.id.action_go:
                if (webview.canGoForward())
                    webview.goForward();
                return true;
            case R.id.action_refresh:
                webview.reload();
                return true;
            case R.id.action_open:
                GankUtils.openUrlByBrowser(frmContext, mUrl);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
