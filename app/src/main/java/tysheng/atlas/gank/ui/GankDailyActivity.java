package tysheng.atlas.gank.ui;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.ui.fragment.GankDailyFragment;

/**
 * Created by shengtianyang on 16/4/1.
 */
public class GankDailyActivity extends BaseActivity {
    @Bind(R.id.tl_setting)
    Toolbar toolbar;

    @Override
    public void initData() {
        toolbar.setTitle("");
        toolbar.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        jumpFragment(null, new GankDailyFragment(), R.id.fl, "");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily;
    }

}
