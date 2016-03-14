package tysheng.atlas.presenter;

import com.android.volley.VolleyError;

/**
 * Created by shengtianyang on 16/3/13.
 */
public interface VolleyView {
    void onSuccessResponse(String response);
    void onFailResponse(VolleyError error);
}
