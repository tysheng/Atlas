package tysheng.atlas.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import tysheng.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/2/25.
 */
public class VolleyUtils {
    public static Context context;
    public static StringRequest r;

    public static void Get(String url, String tag, VolleyIF vif) {
        MyApplication.getRequestQueue().cancelAll(tag);
        r = new StringRequest(url, vif.getmListener(), vif.getmErrorListener());
        r.setTag(tag);
        MyApplication.getRequestQueue().add(r);
    }

    public static void Post(String url, String tag, final HashMap<String, String> header, final HashMap<String, String> params, VolleyIF vif) {
        MyApplication.getRequestQueue().cancelAll(tag);
        r = new StringRequest(Request.Method.POST, url, vif.getmListener(), vif.getmErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        r.setTag(tag);
        MyApplication.getRequestQueue().add(r);
    }
}
