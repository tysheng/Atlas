package tysheng.atlas.presenter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import tysheng.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/3/13.
 */
public class PostPresenterImpl implements PostPresenter {
    private VolleyView view;

    public PostPresenterImpl(VolleyView view) {
        this.view = view;
    }

    @Override
    public void getData(String url, String tag,final HashMap<String, String> header,final HashMap<String, String> params) {
        MyApplication.getRequestQueue().cancelAll(tag);
        StringRequest r = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onFailResponse(error);
            }
        }){
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

    @Override
    public void onDestroy() {
        view = null;
    }
}
