package tysheng.atlas.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import tysheng.atlas.R;
import tysheng.atlas.activity.SettingActivity;

/**
 * Created by shengtianyang on 16/3/23.
 */
public class MyPreferenceFragment extends PreferenceFragment  {
    FragmentCallback callback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        callback.func1(preference.getKey());
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingActivity)
            callback = (SettingActivity)getActivity();
    }
}
