package tysheng.atlas.hupu.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import tysheng.atlas.hupu.bean.ForumsData;
import tysheng.atlas.hupu.bean.ThreadListData;

/**
 * Created by shengtianyang on 16/3/24.
 */
public interface ForumApi {
    String BASE_URL = "http://bbs.mobileapi.hupu.com/1/7.0.7/";
    String BASE_URL_6 = "http://bbs.mobileapi.hupu.com/1/7.0.6/";

    @GET("forums/getForums")
    Observable<ForumsData> getForums(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("forums/getForumsInfoList")
    Observable<ThreadListData> getThreadsList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("/threads/getsThreadPostList")
    Observable<Object> getThreadPostList(@QueryMap Map<String,String> params);

}
