package tysheng.atlas.utils;

/**
 * Created by shengtianyang on 16/2/1.
 */
public final class TimeStamp {

    public static String getFinalTimeDiffrence(int t){
        String time = "";
        Long diff = System.currentTimeMillis() / 1000 - t;   //两时间差，精确到毫秒

        Long day = diff / (60 * 60 * 24);          //以天数为单位取整
        Long hour = (diff / (60 * 60) - day * 24);             //以小时为单位取整
        Long min = ((diff / 60) - day * 24 * 60 - hour * 60);    //以分钟为单位取整
//        Long secone = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (hour >= 1) {
            time = hour + "小时前";
        } else if (min > 0) {
            time = min + "分钟前";
        } else if (min == 0) {
            //just now
            time = "0";
        } else {
            //long long ago
            time = "-1";
        }

        if (time.equals("0")) {
            time = "刚刚";
        } else if (time.equals("-1")) {
            time = "很久很久前";
        }
        return time;
    }

}
