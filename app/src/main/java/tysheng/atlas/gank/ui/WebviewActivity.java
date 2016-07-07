package tysheng.atlas.gank.ui;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.ui.fragment.WebviewFragment;

/**
 * Created by shengtianyang on 16/4/3.
 */
public class WebviewActivity extends BaseActivity {
    public static final String URL = "url";
    public static final String TITLE = "title";
    String url;
    String title;
    @BindView(R.id.tl_setting)
    Toolbar toolbar;
    @BindView(R.id.cl)
    CoordinatorLayout cl;

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        url = intent.getStringExtra(WebviewActivity.URL);
        title = intent.getStringExtra(WebviewActivity.TITLE);
        jumpFragment(null, WebviewFragment.newInstance(url,title), R.id.fl, "");


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

}
