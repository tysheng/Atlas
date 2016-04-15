package tysheng.atlas.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.util.DialogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.app.Constant;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.ui.fragment.FragmentCallback;
import tysheng.atlas.ui.fragment.MyPreferenceFragment;
import tysheng.atlas.utils.ACache;
import tysheng.atlas.utils.SPHelper;

public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback, FragmentCallback {

    private static final int RESULT_LOAD_IMAGE = 100;
    @Bind(R.id.cl)
    CoordinatorLayout cl;

    private int primaryPreselect;
    Intent intent;
    @Bind(R.id.tl_setting)
    Toolbar tbSetting;

    ACache mCache;

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            final String uri = data.getData().toString();
            new MaterialDialog.Builder(this)
                    .title("是否更改头像")
                    .positiveText("OK")
                    .negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            SPHelper.setAvatar(actContext, uri);
                            SPHelper.setIsSetting(actContext, true);

                            Glide.with(actContext)
                                    .loadFromMediaStore(data.getData())
                                    .asBitmap()
                                    .into(new SimpleTarget<Bitmap>(75,75) {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            mCache.put(Constant.AVATAR_BITMAP, resource, ACache.TIME_DAY * 30);
                                        }
                                    });

                        }
                    }).show();
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int color) {
        primaryPreselect = color;
        String s = String.format("#%06X", (0xFFFFFF & color));
        switch (s) {
            case "#3C515C":
                SPHelper.setTheme(SettingActivity.this, R.style.YellowTheme);
                break;
            case "#F44336":
                SPHelper.setTheme(SettingActivity.this, R.style.RedTheme);
                break;
            case "#202020":
                SPHelper.setTheme(SettingActivity.this, R.style.BlackTheme);
                break;
            case "#4CAF50":
                SPHelper.setTheme(SettingActivity.this, R.style.GreenTheme);
                break;
            case "#9C27B0":
                SPHelper.setTheme(SettingActivity.this, R.style.PurpleTheme);
                break;
            case "#F06292":
                SPHelper.setTheme(SettingActivity.this, R.style.PinkTheme);
                break;
            default:
                SPHelper.setTheme(SettingActivity.this, R.style.BlueTheme);
                break;
        }

        jumpIntent();
    }

    @Override
    public void initData() {
        setSupportActionBar(tbSetting);
        tbSetting.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        intent = new Intent(this, MainActivity.class);
        primaryPreselect = DialogUtils.resolveColor(this, R.attr.themeColor);
        mCache = ACache.get(actContext);
        getFragmentManager().beginTransaction()
                .replace(R.id.fl, new MyPreferenceFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit();
    }

    private void exit() {
        if (!SPHelper.getIsSetting(actContext))
            finish();
        else
            jumpIntent();
    }

    private void jumpIntent() {
        SPHelper.setIsSetting(actContext, false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void func1(String s) {
        switch (s) {
            case "avatar":
                Intent i = new Intent(Intent.ACTION_PICK, null);
                i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case "name":
                new MaterialDialog.Builder(this)
                        .title("输入你的姓名")
                        .inputRange(1, 15)
                        .positiveText("OK")
                        .input("", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                SPHelper.setName(actContext, input.toString());
                                SPHelper.setIsSetting(actContext, true);
                            }
                        }).show();
                break;
            case "email":
                new MaterialDialog.Builder(this)
                        .title("输入你的邮箱")
                        .inputRange(1, 15)
                        .positiveText("OK")
                        .input("", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                SPHelper.setEmail(actContext, input.toString());
                                SPHelper.setIsSetting(actContext, true);
                            }
                        }).show();
                break;
            case "theme":
                new ColorChooserDialog.Builder(this, R.string.color_palette)
                        .titleSub(R.string.colors)
                        .accentMode(false)
                        .allowUserColorInput(false)
                        .preselect(primaryPreselect)
                        .customColors(R.array.custom_colors, null)
                        .show();
                break;
            case "cache":
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                imagePipeline.clearCaches();
                showSnackbar(cl, "已清除缓存");
                break;
        }
    }

}


