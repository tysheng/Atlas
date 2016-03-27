package tysheng.atlas.ui.fragment;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;
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

    @OnClick(R.id.button)
    public void onClick() {
        InputStream is = getResources().openRawResource(R.raw.info);
        BufferedInputStream bufferedReader = new BufferedInputStream(is);
        String temp = "";
        String str = "";


        try {
            int length = bufferedReader.available();
            byte [] buffer = new byte[length];
            bufferedReader.read(buffer);
            while (bufferedReader.read() != -1) {
                str += temp;
                Log.d("sty", temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.setText(str);

//        try {
//            OutputStreamWriter writer = new OutputStreamWriter(frmContext.openFileOutput("ttt", Context.MODE_PRIVATE));
//            writer.write("asdasd");
//            writer.flush();
//            writer.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_io;
    }

    @Override
    protected void initData() {


    }

}
