package tysheng.atlas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StyleRes;

import tysheng.atlas.R;
import tysheng.atlas.app.Constant;

/**
 * Created by shengtianyang on 16/2/22.
 */
public final class SPHelper {

    /**
     * 设置主题
     *
     * @param context
     * @return
     */
    public static void setTheme(Context context,  @StyleRes int themeResId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("theme", themeResId).commit();


    }

    /**
     * 得到主题
     *
     * @param context
     * @return
     */
    public static int getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("theme", R.style.BlueTheme);
    }
    /**
     * 切换天气模式
     * 0 : weathertab
     * 1 : weather
     */
    public static void SwitchWeatherMode(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("weather",0) == 0){
            sharedPreferences.edit().putInt("weather", 1).commit();
        } else if (sharedPreferences.getInt("weather",0) == 1){
            sharedPreferences.edit().putInt("weather", 0).commit();
        }

    }


}
