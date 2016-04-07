package tysheng.atlas.gank.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GankUtils {
    private GankUtils() {

    }

    public static void share(Context context, String text, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, title));
    }

//    public static void copyText(Context context, String text) {
//        ClipboardManager c = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
//        c.setText(text);
//    }

    public static void openUrlByBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static long getDataTime(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = format.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
