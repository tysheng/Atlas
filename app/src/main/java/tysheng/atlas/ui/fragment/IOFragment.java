package tysheng.atlas.ui.fragment;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;

/**
 * Created by shengtianyang on 16/3/25.
 */
public class IOFragment extends BaseFragment {
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.circle)
    ImageView circle;

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_io;
    }

    @Override
    protected void initData() {
        //判断是否正在运行
        Glide.with(frmContext)
                .load("http://i3.hoopchina.com.cn/blogfile/201604/04/BbsImg145974633089957_339x476.gif")
                .asGif()
                .into(circle)
        ;

    }

}
