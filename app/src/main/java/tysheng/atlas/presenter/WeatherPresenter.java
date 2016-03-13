package tysheng.atlas.presenter;

import java.util.HashMap;

/**
 * Created by shengtianyang on 16/3/13.
 */
public interface WeatherPresenter {
    void getData(String url, String tag, HashMap<String, String> header,HashMap<String, String> params);
    void onDestroy();
}
