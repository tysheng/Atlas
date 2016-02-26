package shengtianyang.atlas.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import shengtianyang.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/2/25.
 */
public class VolleyUtils {
    public static void Get(String url, String tag, VolleyIF vif) {
        StringRequest stringRequest = new StringRequest(url, vif.getmListener(), vif.getmErrorListener());
        stringRequest.setTag(tag);
        MyApplication.getRequestQueue().add(stringRequest);
    }
    public static void Post(String url, String tag, final HashMap<String, String> header, final HashMap<String, String> params,VolleyIF vif) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, vif.getmListener(), vif.getmErrorListener()){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getRequestQueue().add(stringRequest);
    }
}
