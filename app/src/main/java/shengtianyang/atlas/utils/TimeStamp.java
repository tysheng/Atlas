package shengtianyang.atlas.utils;

/**
 * Created by shengtianyang on 16/2/1.
 */
public final class TimeStamp {

    public static String getTimeDifference(int now) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


//        Date d1 = df.parse("2012-11-05 12:00:00");//后的时间
//        Date d2 = df.parse("2012-11-04 11:10:00"); //前的时间
        Long diff = System.currentTimeMillis() / 1000 - now;   //两时间差，精确到毫秒

        Long day = diff / (60 * 60 * 24);          //以天数为单位取整
        Long hour = (diff / (60 * 60) - day * 24);             //以小时为单位取整
        Long min = ((diff / 60) - day * 24 * 60 - hour * 60);    //以分钟为单位取整
//        Long secone = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (hour >= 1) {
            return hour + "小时前";
        } else if (min > 0) {
            return min + "分钟前";
        } else if (min == 0) {
            //just now
            return "0";
        } else {
            //long long ago
            return "-1";
        }
    }

}
