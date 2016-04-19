package tysheng.atlas.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("MyService onBind()：");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        System.out.println("MyService onCreate()：");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("MyService onUnbind()：");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("MyService onStartCommand()：");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("MyService onDestroy()：");
        super.onDestroy();
    }

    public void method1() {
        System.out.println("MyService is method1");

        sendBroadcast(new Intent(DownloadService.START));
    }

    public void method2() {
        System.out.println("MyService is method2");
    }

   public class MyBinder extends Binder {

        public void callMethod1() {
            method1();
        }

        public void callMethod2() {
            method2();
        }
    }
}
