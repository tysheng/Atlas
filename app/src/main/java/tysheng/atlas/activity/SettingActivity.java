package tysheng.atlas.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.util.DialogUtils;

import butterknife.Bind;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.greendao.DaoSession;
import tysheng.atlas.greendao.NoteDao;
import tysheng.atlas.utils.SPHelper;

public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    private static final int RESULT_LOAD_IMAGE = 100;
    private int primaryPreselect;
    Intent intent;
    @Bind(R.id.tl_setting)
    Toolbar tbSetting;
    DaoSession session;


    @OnClick(R.id.ll_color_chooser)
    public void showColorChooserCustomColorsNoSub() {
        new ColorChooserDialog.Builder(this, R.string.color_palette)
                .titleSub(R.string.colors)
                .accentMode(false)
                .allowUserColorInput(false)
                .preselect(primaryPreselect)
                .customColors(R.array.custom_colors, null)
                .show();
    }

    @OnClick(R.id.ll_switch_weather)
    public void onClick() {
//        SPHelper.SwitchWeatherMode(actContext);
//        ShowToast("天气模式已改变");
        Intent i = new Intent(Intent.ACTION_PICK, null);
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            String uri = data.getData().toString();
            SPHelper.setAvatar(actContext, uri);
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

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void initData() {
        setSupportActionBar(tbSetting);
        tbSetting.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent = new Intent(this, MainActivity.class);
        primaryPreselect = DialogUtils.resolveColor(this, R.attr.themeColor);
        initDao();

    }

    private void initDao() {
        session = MyApplication.getSession();
        NoteDao notedao = session.getNoteDao();
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
        finish();
    }

}


