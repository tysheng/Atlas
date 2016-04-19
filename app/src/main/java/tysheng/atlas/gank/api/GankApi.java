package tysheng.atlas.gank.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import tysheng.atlas.gank.bean.GankCategory;
import tysheng.atlas.gank.bean.GankDaily;
import tysheng.atlas.gank.bean.HistoryBean;

/**
 * Created by shengtianyang on 16/3/26.
 */
public interface GankApi {
    String BASE_URL = "http://gank.io/api/";//data/Android/10/1

    @GET("data/{category}/{counts}/{page}")
    Observable<GankCategory>
    getCategory(@Path("category") String category, @Path("counts") int counts, @Path("page") int page);

//    String DAILY_URL = "http://gank.io/api/day/";//2015/08/07

    @GET("day/{year}/{month}/{day}")
    Observable<GankDaily>
    getDaily(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    @GET("day/history")
    Observable<HistoryBean>
    getHistory();
}
