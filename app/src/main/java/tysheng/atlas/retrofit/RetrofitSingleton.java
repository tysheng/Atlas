package tysheng.atlas.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shengtianyang on 16/3/19.
 */
public class RetrofitSingleton {
    private static WeatherApi weatherApi = null;
    private static Retrofit retrofit = null;
    private static final String TAG = RetrofitSingleton.class.getSimpleName();
    public static Context context;
    public static void init(final Context context) {

        Executor executor = Executors.newCachedThreadPool();

        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(WeatherApi.BASE_URL)
                .callbackExecutor(executor)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        weatherApi = retrofit.create(WeatherApi.class);
    }
    public static WeatherApi getWeatherApi(Context context) {
        if (weatherApi != null) return weatherApi;
        init(context);
        return getWeatherApi(context);
    }

}

