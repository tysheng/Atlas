package tysheng.atlas.mvp.volley_get;

import com.android.volley.VolleyError;

/**
 * Created by shengtianyang on 16/3/23.
 */
public class PGet {
    private MGet mGet;
    private VGet VGet;

    public PGet(VGet VGet) {
        this.VGet = VGet;
        this.mGet = new MGetImpl();
    }

    public void func(String url, String tag) {
        mGet.getData(url, tag, new MVolleyListener() {
            @Override
            public void onSuccessResponse(String response) {
                VGet.onSuccess(response);
                VGet.stopSwipe();

            }

            @Override
            public void onFailResponse(VolleyError error) {
                VGet.onFailedError(error);
                VGet.stopSwipe();
            }
        });
    }
}
