package shengtianyang.atlas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import shengtianyang.atlas.R;
import shengtianyang.atlas.utils.Flash;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class FlashFragment extends Fragment {

    private Flash flash = new Flash();
    @Bind(R.id.flashlightButton)
    ToggleButton flashlightButton;

    public FlashFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flash, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flash.close();
        ButterKnife.unbind(this);
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
