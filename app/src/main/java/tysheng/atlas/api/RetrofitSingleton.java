package tysheng.atlas.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tysheng.atlas.hupu.api.ForumApi;

/**
 * Created by shengtianyang on 16/3/19.
 */
public class RetrofitSingleton {
    private static WeatherApi weatherApi = null;
    private static Retrofit retrofit = null;
    private static ForumApi forumApi = null;
    private static V2exApi sV2exApi = null;
    public static Context context;

    public static void init(Context context, String url) {

        Executor executor = Executors.newCachedThreadPool();

        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
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

    public static Retrofit getInstance(Context context, String url) {
        if (retrofit == null) {
            init(context, url);
            return retrofit;
        }
        return retrofit;
    }

}

