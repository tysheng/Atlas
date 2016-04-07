package tysheng.atlas.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.utils.SPHelper;
import tysheng.atlas.utils.phtodraweeview.PhotoDraweeView;

/**
 * Created by shengtianyang on 16/3/27.
 */
public class PictureActivity extends BaseActivity {
    @Bind(R.id.picture)
    PhotoDraweeView picture;
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    //    @Bind(R.id.app_bar_layout)
//    AppBarLayout appBarLayout;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    private String mImageUrl;
    private String mImageTitle;

    @Override
    public void initData() {
        if (SPHelper.getGankTip(actContext).equals("on"))
            initSnackBar();
        parseIntent();
//        initToolbar();
        initPicture();
    }

    private void initSnackBar() {
        final Snackbar snackbar = Snackbar.make(picture, "长按可保存图片", Snackbar.LENGTH_SHORT);
        ViewGroup viewGroup = (ViewGroup) snackbar.getView();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof TextView)
                ((TextView) v).setTextColor(Color.WHITE);
        }
        snackbar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void initPicture() {
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setOldController(picture.getController());
        controller.setUri(Uri.parse(mImageUrl));
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                picture.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        picture.setController(controller.build());
        picture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(actContext)
                        .title("是否保存图片")
                        .positiveText("好的,就要她啦!")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                saveImageToGallery();
                            }
                        }).show();

                return true;
            }
        });
    }

//    private void initToolbar() {
//        appBarLayout.setAlpha(0.1f);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        setTitle(mImageTitle);
//    }

    private Bitmap getBitmap() {
        final Bitmap[] mBitmap = new Bitmap[1];
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(mImageUrl))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, actContext);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {

                                 @Override
                                 public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                     // You can use the bitmap in only limited ways
                                     // No need to do any cleanup.
                                     mBitmap[0] = bitmap;

                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                     // No cleanup required here.
                                 }
                             },
                CallerThreadExecutor.getInstance());
        return mBitmap[0];
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture;
    }

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    private void parseIntent() {
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
    }

    private void saveImageToGallery() {
        saveImageAndGetPathObservable(this, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                        String msg = String.format(getString(R.string.picture_has_save_to),
                                appDir.getAbsolutePath());
                        showToast(msg);
                    }
                });

    }

    public Observable<Uri> saveImageAndGetPathObservable(final Context context, String url, final String title) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = getBitmap();
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Bitmap, Observable<? extends Uri>>() {
            @Override
            public Observable<? extends Uri> call(Bitmap bitmap) {
                File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = title.replace('/', '-') + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri uri = Uri.fromFile(file);
                // 通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                context.sendBroadcast(scannerIntent);
                return Observable.just(uri);
            }
        }).subscribeOn(Schedulers.io());
    }

}
