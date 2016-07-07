package tysheng.atlas.gank.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.ui.fragment.SortFragment;

/**
 * Created by shengtianyang on 16/4/6.
 */
public class SortActivity extends BaseActivity {
    @BindView(R.id.tl_setting)
    Toolbar toolbar;


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
        jumpFragment(null, new SortFragment(), R.id.fl, "");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sort;
    }


}
