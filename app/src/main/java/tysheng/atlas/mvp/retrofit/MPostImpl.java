package tysheng.atlas.mvp.retrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.bean.RWeatherBean;
import tysheng.atlas.api.MyRetrofit;
import tysheng.atlas.api.WeatherApi;

/**
 * Created by shengtianyang on 16/3/13.
 */
public class MPostImpl implements MPost {

    @Override
    public void getData(String cityName, final MRetrofitListener listener) {
        MyRetrofit.getWeatherApi(MyApplication.getInstance(), WeatherApi.BASE_URL)
                .getParams(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<RWeatherBean, Boolean>() {
                    @Override
                    public Boolean call(RWeatherBean rWeatherBean) {
                        return rWeatherBean.getHeWeather().get(0).getStatus().equals("ok");
                    }
                })
                .map(new Func1<RWeatherBean, RWeatherBean.HeWeatherEntity>() {

                    @Override
                    public RWeatherBean.HeWeatherEntity call(RWeatherBean rWeatherBean) {
                        return rWeatherBean.getHeWeather().get(0);
                    }
                })
                .subscribe(new Subscriber<RWeatherBean.HeWeatherEntity>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailResponse(e);
                    }

                    @Override
                    public void onNext(RWeatherBean.HeWeatherEntity heWeatherEntity) {
                        listener.onSuccessResponse(heWeatherEntity);

                    }
                });
    }
}
