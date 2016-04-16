package tysheng.atlas.ui.fragment;

import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/4/16.
 */
public class DownLoadFragment extends BaseFragment {
    @Bind(R.id.start)
    Button start;
    @Bind(R.id.stop)
    Button stop;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.imageView)
    ImageView imageView;

    View view1;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_download;
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.start, R.id.stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (view1 == null)
                    view1 = ((ViewStub) ButterKnife.findById(getActivity(), R.id.viewStub)).inflate();
                view1.setVisibility(View.VISIBLE);
                break;
            case R.id.stop:
                view1.setVisibility(View.GONE);
                break;
        }
    }
}
