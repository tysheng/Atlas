package tysheng.atlas.ui.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import tysheng.atlas.R;
import tysheng.atlas.ui.SettingActivity;
import tysheng.atlas.utils.SPHelper;

/**
 * Created by shengtianyang on 16/3/23.
 */
public class MyPreferenceFragment extends PreferenceFragment {
    private FragmentCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        callback = (SettingActivity) getActivity();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key.equals("gank_tip")) {
            if (((CheckBoxPreference)preference).isChecked())
                SPHelper.setGankTip(getActivity(), "on");
            else
                SPHelper.setGankTip(getActivity(), "off");
        } else
            callback.func1(key);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

}
