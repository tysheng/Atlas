package tysheng.atlas.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tysheng.atlas.gank.api.GankApi;
import tysheng.atlas.hupu.api.ForumApi;

/**
 * Created by shengtianyang on 16/3/19.
 */
public class RetrofitSingleton {
    private static WeatherApi weatherApi = null;
    private static Retrofit retrofit = null;
    private static ForumApi forumApi = null;
    private static V2exApi sV2exApi = null;
    private static GankApi sGankApi = null;
    public static Context context;
    public static final int TIME_MAX = 6;

    public static void init(Context context, String url) {

        Executor executor = Executors.newCachedThreadPool();
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response response = chain.proceed(request);
//
//                String cacheControl = request.cacheControl().toString();
//                if (TextUtils.isEmpty(cacheControl)) {
//                    cacheControl = "public, max-age=60";
//                }
//                return response.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();
//            }
//        };
        final File baseDir = context.getCacheDir();
        Cache cache = null;
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            cache = new Cache(cacheDir, 10 * 1024 * 1024);
        }
        //设置缓存 10M


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(TIME_MAX, TimeUnit.SECONDS);
        builder.connectTimeout(TIME_MAX, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_MAX, TimeUnit.SECONDS);
        OkHttpClient client = builder.cache(cache).build();


        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(url)
                .callbackExecutor(executor)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }


    public static WeatherApi getWeatherApi(Context context, String url) {
        if (weatherApi != null) return weatherApi;
        init(context, url);
        weatherApi = retrofit.create(WeatherApi.class);
        return getWeatherApi(context, url);
    }

    public static ForumApi getForumApi(Context context, String url) {
        if (forumApi != null) return forumApi;
        init(context, url);
        forumApi = retrofit.create(ForumApi.class);
        return getForumApi(context, url);
    }

    public static V2exApi getV2exApi(Context context, String url) {
        if (sV2exApi != null) return sV2exApi;
        init(context, url);
        sV2exApi = retrofit.create(V2exApi.class);
        return getV2exApi(context, url);
    }

    public static GankApi getGankApi(Context context, String url) {
        if (sGankApi != null) return sGankApi;
        init(context, url);
        sGankApi = retrofit.create(GankApi.class);
        return getGankApi(context, url);
    }

//    public static Retrofit getInstance(Context context, String url) {
//        if (retrofit == null) {
//            init(context, url);
//            return retrofit;
//        }
//        return retrofit;
//    }

}

