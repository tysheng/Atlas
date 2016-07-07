package tysheng.atlas.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shengtianyang on 16/5/1.
 */
public class SaveImage {

//    public static Observable<Uri> saveImageAndGetPathObservable(final Context context, final String url, final String title) {
//        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
//            @Override
//            public void call(Subscriber<? super Bitmap> subscriber) {
//                final Bitmap[] bitmap = new Bitmap[1];
//                Glide.with(context)
//                        .load(url)
//                        .asBitmap()
//                        .into(new SimpleTarget<Bitmap>() {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                bitmap[0] = resource;
//                            }
//                        });
//                if (bitmap[0] == null) {
//                    subscriber.onError(new Exception("无法下载到图片"));
//                }
//                subscriber.onNext(bitmap[0]);
//                subscriber.onCompleted();
//            }
//        }).flatMap(new Func1<Bitmap, Observable<? extends Uri>>() {
//            @Override
//            public Observable<? extends Uri> call(Bitmap bitmap) {
//                File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
//                if (!appDir.exists()) {
//                    appDir.mkdir();
//                }
//                String fileName = title.replace('/', '-') + ".jpg";
//                File file = new File(appDir, fileName);
//                try {
//                    FileOutputStream fos = new FileOutputStream(file);
//                    assert bitmap != null;
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                    fos.flush();
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Uri uri = Uri.fromFile(file);
//                // 通知图库更新
//                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
//                context.sendBroadcast(scannerIntent);
//                return Observable.just(uri);
//            }
//        }).subscribeOn(Schedulers.io());
//    }
    public static void saveImageToGallery(Context context, Bitmap bmp,String title) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = title.replace('/', '-') + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Uri uri = Uri.fromFile(file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }
}
