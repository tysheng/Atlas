package tysheng.atlas.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;


public class DownloadService extends Service implements MediaPlayer.OnPreparedListener {
    public DownloadService() {
    }

    public static final String START = "START";
    public static final String STOP = "STOP";
    private MediaPlayer mMediaPlayer;

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    public class MyBinder extends Binder {
        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();

    }

    @Override
    public void onCreate() {
        super.onCreate();

//        mMediaPlayer = MediaPlayer.create(this, R.raw.demo);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(100, 100);


    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public void goOn() {
        mMediaPlayer.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}
