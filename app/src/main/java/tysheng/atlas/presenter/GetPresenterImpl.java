package tysheng.atlas.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import tysheng.atlas.app.MyApplication;

/**
 * Created by shengtianyang on 16/3/13.
 */
public class GetPresenterImpl implements GetPresenter {
    private VolleyView view;

    public GetPresenterImpl(VolleyView view) {
        this.view = view;
    }

    @Override
    public void getData(String url, String tag) {
        MyApplication.getRequestQueue().cancelAll(tag);
        StringRequest r = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                view.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onFailResponse(error);
            }
        });
        r.setTag(tag);
        MyApplication.getRequestQueue().add(r);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
