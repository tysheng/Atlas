package tysheng.atlas.presenter;

import com.android.volley.VolleyError;

import tysheng.atlas.net.VolleyIF;
import tysheng.atlas.net.VolleyUtils;

/**
 * Created by shengtianyang on 16/3/13.
 */
public class HotPresenterImpl implements HotPresenter {
    private HotView hotView;

    public HotPresenterImpl(HotView hotView) {
        this.hotView = hotView;
    }

    @Override
    public void getData(String url, String tag) {
        VolleyUtils.Get(url, tag, new VolleyIF(VolleyIF.mListener, VolleyIF.mErrorListener) {
            @Override
            public void onMyResponse(String response) {
                hotView.onSuccessResponse(response);
            }

            @Override
            public void onMyErrorResponse(VolleyError error) {
                hotView.onFailResponse(error);
            }
        });
    }

    @Override
    public void onDestroy() {
        hotView = null;
    }
}
