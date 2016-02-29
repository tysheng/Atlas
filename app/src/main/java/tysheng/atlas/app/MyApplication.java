package tysheng.atlas.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

import tysheng.atlas.net.OkHttpStack;

public class MyApplication extends Application {
    public static RequestQueue requestQueue;
//    public static Toast toast;
//    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        requestQueue = Volley.newRequestQueue(this, new OkHttpStack());
//        context= this;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

//    public static void ShowToast(String msg) {
//        if (toast == null) {
//            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(msg);
//        }
//        toast.show();
//    }
}
