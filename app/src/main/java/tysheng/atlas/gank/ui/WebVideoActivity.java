package tysheng.atlas.gank.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import butterknife.BindView;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.widget.LoveVideoView;

/**
 * Created by shengtianyang on 16/4/8.
 */
public class WebVideoActivity extends BaseActivity {
    @BindView(R.id.video)
    LoveVideoView webVideo;
    String url;
public static final String URL="url";
    @Override
    public void initData() {
        url = getIntent().getStringExtra(URL);
        loadWebVideo(webVideo,url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    public void loadWebVideo(WebView webView, String url) {
        webView.setWebChromeClient(new Chrome());
        webView.loadUrl(url);
    }

    private class Chrome extends WebChromeClient
            implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer player) {
            if (player != null) {
                if (player.isPlaying()) player.stop();
                player.reset();
                player.release();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webVideo != null) {
            webVideo.resumeTimers();
            webVideo.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webVideo != null) {
            webVideo.onPause();
            webVideo.pauseTimers();
        }
    }

    @Override
    protected void onDestroy() {
        if (webVideo != null) {
            webVideo.setWebViewClient(null);
            webVideo.setWebChromeClient(null);
            webVideo.destroy();
            webVideo = null;
        }
        super.onDestroy();
    }
}
