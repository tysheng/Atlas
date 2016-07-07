package tysheng.atlas.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.utils.GankUtils;
import tysheng.atlas.ui.fragment.MyPreferenceFragment;
import tysheng.atlas.utils.SPHelper;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shengtianyang on 16/3/27.
 */
public class PictureActivity extends BaseActivity implements PhotoViewAttacher.OnViewTapListener, View.OnLongClickListener {
    @BindView(R.id.picture)
    ImageView picture;
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String mImageUrl;
    private String mImageTitle;
    protected boolean mIsHidden = false;
    private PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;

    @Override
    public void initData() {
        SPHelper spHelper = new SPHelper(actContext);
        if (spHelper.getSpString(Constant.GANK_TIP, MyPreferenceFragment.ON).equals(MyPreferenceFragment.ON))
            initSnackBar();
        parseIntent();
        initToolbar();
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
        mAttacher = new PhotoViewAttacher(picture);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(this)
                .load(mImageUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mBitmap = resource;
                        picture.setImageBitmap(mBitmap);
                        mAttacher.update();
                    }
                });
        mAttacher.setOnLongClickListener(this);
        mAttacher.setOnViewTapListener(this);
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_picture);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        saveImageToGallery();
                        break;
                    case R.id.action_share:
                        GankUtils.share(actContext, mImageUrl, mImageTitle);
                    default:
                        break;
                }
                return true;
            }
        });
        toolbar.setBackgroundColor(Color.TRANSPARENT);
    }

    private void hideOrShowToolbar() {
        toolbar.animate()
                .translationY(mIsHidden ? 0 : -toolbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }

    private Bitmap getBitmap() {
        if (mBitmap != null)
            return mBitmap;
        return null;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmap != null)
            mBitmap = null;
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
                        showSnackbar(picture, msg);
                    }
                });

    }

    public Observable<Uri> saveImageAndGetPathObservable(final Context context, String url, final String title) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                if (getBitmap() == null)
                    return;
                subscriber.onNext(getBitmap());
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

    @Override
    public void onViewTap(View view, float v, float v1) {
        hideOrShowToolbar();
    }

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
}
