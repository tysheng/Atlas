package shengtianyang.atlas.fragment;

import android.webkit.WebView;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/2/1.
 */
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
        webview.loadUrl(url);
    }
}
