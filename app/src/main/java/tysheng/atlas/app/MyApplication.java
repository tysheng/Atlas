package tysheng.atlas.app;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MyApplication extends Application {
    private static RequestQueue requestQueue;
//    SQLiteDatabase db;
//    DaoMaster daoMaster;
//    public static DaoSession daoSession;
    private static IWXAPI api;
    private static MyApplication instance;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        requestQueue = Volley.newRequestQueue(this);
//        setupDatabase();
        regTowX();
        refWatcher = LeakCanary.install(this);

    }
    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }
//    public void setupDatabase() {
//        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
//        db = helper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//    }

//    public static DaoSession getSession() {
//        return daoSession;
//    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(instance);
        }
        return requestQueue;
    }
    public static MyApplication getInstance() {
        return instance;
    }

    public static IWXAPI getWxApi() {
        return api;
    }

    private void regTowX() {
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true);
        api.registerApp(Constant.WX_APP_ID);
    }

}
