package tysheng.atlas.activity;

import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.util.DialogUtils;

import butterknife.Bind;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.utils.SPHelper;

public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    private int primaryPreselect;
    Intent intent;
    @Bind(R.id.tl_setting)
    Toolbar tbSetting;


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

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int color) {
        primaryPreselect = color;
        String s = String.format("#%06X", (0xFFFFFF & color));
        switch(s){
            case "#FFEB3B":
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
        intent = new Intent(this,MainActivity.class);
        primaryPreselect = DialogUtils.resolveColor(this, R.attr.themeColor);


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


