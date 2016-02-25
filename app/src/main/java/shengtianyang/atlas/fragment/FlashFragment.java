package shengtianyang.atlas.fragment;

import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import shengtianyang.atlas.R;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.utils.Flash;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class FlashFragment extends BaseFragment {

    private Flash flash = new Flash();
    @Bind(R.id.flashlightButton)
    ToggleButton flashlightButton;

    public FlashFragment() {
    }

    public static FlashFragment getInstance() {
        return new FlashFragment();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flash.close();
        ButterKnife.unbind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_flash;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.flashlightButton)
    public void onClick() {
        if (flashlightButton.isChecked()) {
            flash.open();
            flashlightButton.setKeepScreenOn(true);
        } else {
            flash.close();
            flashlightButton.setKeepScreenOn(false);
        }
    }

}
