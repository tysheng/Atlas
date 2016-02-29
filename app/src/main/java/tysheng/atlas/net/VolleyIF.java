package tysheng.atlas.net;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by shengtianyang on 16/2/25.
 */
public abstract class VolleyIF {
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListener;

    public VolleyIF(Response.Listener<String> mListener, Response.ErrorListener mErrorListener) {
        VolleyIF.mListener = mListener;
        VolleyIF.mErrorListener = mErrorListener;
    }

    public Response.Listener<String> getmListener() {
        mListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onMyResponse(response);
            }
        };
        return mListener;
    }
    public Response.ErrorListener getmErrorListener() {
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onMyErrorResponse(error);
            }
        };
        return mErrorListener;
    }
    public abstract void onMyResponse(String response);

    public abstract void onMyErrorResponse(VolleyError error);


}
