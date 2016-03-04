package tysheng.atlas.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.util.DialogUtils;

import butterknife.Bind;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.utils.SPHelper;

public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    private static final int RESULT_LOAD_IMAGE = 100;

    private int primaryPreselect;
    Intent intent;
    @Bind(R.id.tl_setting)
    Toolbar tbSetting;

    @OnClick({R.id.info_avatar, R.id.info_name, R.id.info_email, R.id.ll_color_chooser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_avatar:
                Intent i = new Intent(Intent.ACTION_PICK, null);
                i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.info_name:
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
            case R.id.info_email:
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
            case R.id.ll_color_chooser:
                new ColorChooserDialog.Builder(this, R.string.color_palette)
                        .titleSub(R.string.colors)
                        .accentMode(false)
                        .allowUserColorInput(false)
                        .preselect(primaryPreselect)
                        .customColors(R.array.custom_colors, null)
                        .show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            final String uri = data.getData().toString();
//            String s2= pathSplit(uri);
//            compressImage(uri,s2);
            new MaterialDialog.Builder(this)
                    .title("是否更改头像")
                    .positiveText("OK")
                    .negativeText("No")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SPHelper.setAvatar(actContext, uri);
                            SPHelper.setIsSetting(actContext, true);
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
//    private void compressImage(String filePath,String s) {
//        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            return;
//        }
//        String destPath = "res:/"+s;
////        content://media/external/images/media/3206
//        File file = new File(filePath);
//        if(file.exists()) {
////            Bitmap src = BitmapFactory.decodeFile(filePath);
////			byte[] bytes = CompressImageUtil.compressImage(src, 200);
//            byte[] bytes = ImageCompress.getimage(filePath);
//            InputStream in = new ByteArrayInputStream(bytes);
//            try {
//                OutputStream out = new FileOutputStream(destPath);
//                int length = 0;
//                byte[] b = new byte[1024];
//                while((length=in.read(b))>0) {
//                    out.write(b, 0, length);
//                }
//                out.flush();
//                out.close();
//                in.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    private String pathSplit(String s){
//        String[] array = s.split(":");
//        return array[1];
//    }

}


