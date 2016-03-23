package tysheng.atlas.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import tysheng.atlas.bean.RWeatherBean;

/**
 * Created by shengtianyang on 16/3/19.
 */
public interface WeatherApi {
    String BASE_URL = "http://apis.baidu.com/heweather/weather/";

    @Headers("apikey: 72242cee88b7717053de6a045245e582")
    @GET("free/")
    Observable<RWeatherBean> getParams(@Query("city") String city);

}
