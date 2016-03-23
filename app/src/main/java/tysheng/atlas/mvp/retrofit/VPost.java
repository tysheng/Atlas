package tysheng.atlas.mvp.retrofit;

import tysheng.atlas.bean.RWeatherBean;

/**
 * Created by shengtianyang on 16/3/23.
 */
public interface VPost {
    void onFailedError(Throwable e);
    void onSuccess(RWeatherBean.HeWeatherEntity heWeatherEntity);

}
