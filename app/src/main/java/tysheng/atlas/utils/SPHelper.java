package tysheng.atlas.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StyleRes;

import java.util.ArrayList;

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
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt("theme", themeResId).commit();


    }

    /**
     * 得到主题
     *
     * @param context
     * @return
     */
    public static int getTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt("theme", R.style.BlueTheme);
    }
//    /**
//     * 切换天气模式
//     * 0 : weathertab
//     * 1 : weather
//     */
//    public static void SwitchWeatherMode(Context context){
//        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
//        if (sp.getInt("weather",0) == 0){
//            sp.edit().putInt("weather", 1).apply();
//        } else if (sp.getInt("weather",0) == 1){
//            sp.edit().putInt("weather", 0).apply();
//        }
//
//    }
    //weather list mode
    public static void setCitySum(Context context,int sum){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt("sum",sum).commit();
    }
    public static int getCitySum(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt("sum",1);
    }

    public static void setCities(Context context, ArrayList<String> list){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        for (int i = 0; i < list.size() ; i++) {
            sp.edit().putString(""+i,list.get(i)).commit();
        }
    }
    public static ArrayList<String> getCities(Context context,int sum){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < sum ; i++) {
            list.add(sp.getString(""+i,"shaoxing"));
        }
        return list;
    }


    //personal info
    public static void setAvatar(Context context,String s){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString("avatar",s).apply();
    }
    public static String getAvatar(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return  sp.getString("avatar","res:///"+R.drawable.menu_myavatar);
    }
    public static void setName(Context context,String s){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString("name",s).apply();
    }
    public static String getName(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return  sp.getString("name","Tianyang Sheng");
    }
    public static void setEmail(Context context,String s){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString("email",s).apply();
    }
    public static String getEmail(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return  sp.getString("email","tyshengsx@gmail.com");
    }
    public static void setIsSetting(Context context,boolean s){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean("issetting",s).apply();
    }
    public static boolean getIsSetting(Context context){
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        return  sp.getBoolean("issetting",false);
    }




}
