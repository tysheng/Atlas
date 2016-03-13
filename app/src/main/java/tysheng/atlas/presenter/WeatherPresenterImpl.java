package tysheng.atlas.presenter;

import com.android.volley.VolleyError;

import java.util.HashMap;

import tysheng.atlas.net.VolleyIF;
import tysheng.atlas.net.VolleyUtils;

/**
 * Created by shengtianyang on 16/3/13.
 */
public class WeatherPresenterImpl implements WeatherPresenter {
    private WeatherView view;

    public WeatherPresenterImpl(WeatherView view) {
        this.view = view;
    }

    @Override
    public void getData(String url, String tag, HashMap<String, String> header, HashMap<String, String> params) {
        VolleyUtils.Post(url, tag, header, params, new VolleyIF(VolleyIF.mListener, VolleyIF.mErrorListener) {
            @Override
            public void onMyResponse(String response) {
                view.onSuccessResponse(response);
            }

            @Override
            public void onMyErrorResponse(VolleyError error) {
                view.onFailResponse(error);
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
