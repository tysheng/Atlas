package shengtianyang.atlas.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
