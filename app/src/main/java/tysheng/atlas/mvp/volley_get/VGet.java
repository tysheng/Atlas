package tysheng.atlas.mvp.volley_get;

import com.android.volley.VolleyError;

/**
 * Created by shengtianyang on 16/3/23.
 */
public interface VGet {
    void stopSwipe();
    void onFailedError(VolleyError error);
    void onSuccess(String s);

}
