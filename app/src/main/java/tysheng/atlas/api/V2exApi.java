package tysheng.atlas.api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import tysheng.atlas.bean.V2HotBean;
import tysheng.atlas.bean.V2ReplyBean;

/**
 * Created by shengtianyang on 16/4/16.
 */
public interface V2exApi {
    String BASE_URL = "https://www.v2ex.com/api/";

    @GET("topics/{hot}.json")
    Observable<List<V2HotBean>> getV2Hot(@Path("hot") String hot);

    @GET("replies/show.json")
    Observable<List<V2ReplyBean>> getV2Reply(@Query("topic_id") int topic_id);


}
