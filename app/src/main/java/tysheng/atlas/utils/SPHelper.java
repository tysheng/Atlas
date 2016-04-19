package tysheng.atlas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StyleRes;

import tysheng.atlas.R;
import tysheng.atlas.app.Constant;

/**
 * Created by shengtianyang on 16/2/22.
 */
public class SPHelper {
    private SharedPreferences mSharedPreferences;

    public SPHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
    }
    public void setSpString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getSpString(String key,String defaultValue){
        return mSharedPreferences.getString(key,defaultValue);
    }
    public void setSpBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public Boolean getSpBoolean(String key, boolean def) {
        return mSharedPreferences.getBoolean(key, def);
    }
    public void setSpInt(String key, @StyleRes int themeResId) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, themeResId);
        editor.apply();
    }
    public int getSpInt(String key, int def) {
        return mSharedPreferences.getInt(key, def);
    }

    /**
     * 设置主题
     *
     * @param context
     * @return
     */
//    public static void setTheme(Context context,  @StyleRes int themeResId) {
//        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
//        sp.edit().putInt("theme", themeResId).apply();
//
//
//    }

    /**
     * 得到主题
     *
     * @param context
     * @return
     */
    public static int getTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(Constant.THEME, R.style.BlueTheme);
    }
}
