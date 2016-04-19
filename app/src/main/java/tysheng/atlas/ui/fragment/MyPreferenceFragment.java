package tysheng.atlas.ui.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.ui.SettingActivity;
import tysheng.atlas.utils.SPHelper;

/**
 * Created by shengtianyang on 16/3/23.
 */
public class MyPreferenceFragment extends PreferenceFragment {
    private FragmentCallback callback;
    private SPHelper mSPHelper;
    public static final String ON = "on";
    public static final String OFF = "off";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        callback = (SettingActivity) getActivity();
        mSPHelper = new SPHelper(getActivity());
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key.equals(Constant.GANK_TIP)) {
            if (((CheckBoxPreference)preference).isChecked())
                mSPHelper.setSpString(ON,ON);
            else
                mSPHelper.setSpString(OFF,ON);
        } else
            callback.func1(key);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

}
