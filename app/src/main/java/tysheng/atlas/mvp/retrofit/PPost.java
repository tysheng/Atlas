package tysheng.atlas.mvp.retrofit;

import tysheng.atlas.bean.RWeatherBean;

/**
 * Created by shengtianyang on 16/3/23.
 */
public class PPost {
    private MPost mPost;
    private VPost vdo;

    public PPost(VPost vdo) {
        this.vdo = vdo;
        this.mPost = new MPostImpl();
    }

    public void func(String cityName) {
        mPost.getData(cityName, new MRetrofitListener() {
            @Override
            public void onSuccessResponse(RWeatherBean.HeWeatherEntity heWeatherEntity) {
                vdo.onSuccess(heWeatherEntity);
            }

            @Override
            public void onFailResponse(Throwable e) {
                vdo.onFailedError(e);
            }
        });
    }
}
