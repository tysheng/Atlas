package tysheng.atlas.wxapi;

//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//
//import tysheng.atlas.R;
//import tysheng.atlas.app.MyApplication;
//
///**
// * Created by shengtianyang on 16/3/3.
// */
//public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        MyApplication.getWxApi().handleIntent(getIntent(), this);
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//    }
//
//    @Override
//    public void onResp(BaseResp baseResp) {
//        int result = 0;
//        switch (baseResp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                result = R.string.errcode_success;
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                result = R.string.errcode_cancel;
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                result = R.string.errcode_deny;
//                break;
//            default:
//                result = R.string.errcode_unknown;
//                break;
//        }
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//        finish();
//    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        setIntent(intent);
//        MyApplication.getWxApi().handleIntent(intent, this);
//    }
//}
