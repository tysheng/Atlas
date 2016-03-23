package tysheng.atlas.mvp.volley_get;

import com.android.volley.VolleyError;

/**
 * Created by shengtianyang on 16/3/13.
 */
public interface MVolleyListener {
    void onSuccessResponse(String response);
    void onFailResponse(VolleyError error);
}
