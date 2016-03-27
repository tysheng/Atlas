package tysheng.atlas.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import tysheng.atlas.bean.GankCategory;

/**
 * Created by shengtianyang on 16/3/26.
 */
public interface GankApi {
    String BASE_URL = "http://gank.io/api/";//data/Android/10/1

    @GET("data/{category}/{counts}/{page}")
    Observable<GankCategory> getParams(@Path("category") String category,@Path("counts") int counts,@Path("page") int page);

}
