package tysheng.atlas.mvp.retrofit;

import tysheng.atlas.bean.RWeatherBean;

/**
 * Created by shengtianyang on 16/3/13.
 */
public interface MRetrofitListener {
    void onSuccessResponse(RWeatherBean.HeWeatherEntity heWeatherEntity);
    void onFailResponse(Throwable e);
}
