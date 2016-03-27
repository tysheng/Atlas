package tysheng.atlas.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tysheng.atlas.R;

/**
 * Created by shengtianyang on 16/3/4.
 */
public class SettingItem extends LinearLayout {

    TextView main;
    TextView sub;

    public void setMain(String main) {
        this.main.setText(main);
    }

    public void setSub(String sub) {
        this.sub.setText(sub);
    }

    public SettingItem(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.setting_item, this);
        main = (TextView) findViewById(R.id.main);
        sub = (TextView) findViewById(R.id.sub);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setAttr(attrs);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        setAttr(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        setAttr(attrs);
    }

    private void setAttr(AttributeSet attrs) {
        String tmain = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "setting_title_main");
        String tsub = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "setting_title_sub");
        setMain(tmain);
        setSub(tsub);
    }

}
