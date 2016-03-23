package tysheng.atlas.mvp.volley_get;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import tysheng.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/3/13.
 */
public class MGetImpl implements MGet {
    @Override
    public void getData(String url, String tag, final MVolleyListener listener) {
        MyApplication.getRequestQueue().cancelAll(tag);
        StringRequest r = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccessResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailResponse(error);
            }
        });
        r.setTag(tag);
        MyApplication.getRequestQueue().add(r);
    }

}
