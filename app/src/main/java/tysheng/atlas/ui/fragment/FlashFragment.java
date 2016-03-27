package tysheng.atlas.ui.fragment;

import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.utils.Flash;

/**
 * Created by shengtianyang on 16/2/1.
 */
public class FlashFragment extends BaseFragment {

    private Flash flash = new Flash();
    @Bind(R.id.flashlightButton)
    ToggleButton flashlightButton;

    public FlashFragment() {
    }

    public static FlashFragment getInstance() {
        return new FlashFragment();
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.fm_flash);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flash.close();
        ButterKnife.unbind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_flash;
    }

    @Override
    protected void initData() {
//        subscriber.add(RetrofitSingleton.getWeatherApi(frmContext)
//                .getParams("shaoxing")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Func1<RWeatherBean, Boolean>() {
//                    @Override
//                    public Boolean call(RWeatherBean rWeatherBean) {
//                        return rWeatherBean.getHeWeather().get(0).getStatus().equals("ok");
//                    }
//                })
//                .map(new Func1<RWeatherBean, RWeatherBean.HeWeatherEntity>() {
//
//                    @Override
//                    public RWeatherBean.HeWeatherEntity call(RWeatherBean rWeatherBean) {
//                        return rWeatherBean.getHeWeather().get(0);
//                    }
//                })
//                .subscribe(new Subscriber<RWeatherBean.HeWeatherEntity>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(RWeatherBean.HeWeatherEntity heWeatherEntity) {
//                        String s =heWeatherEntity.getAqi().getCity().getAqi();
//                        ShowToast(s);
//
//                    }
//                }));


    }

    @OnClick(R.id.flashlightButton)
    public void onClick() {
        if (flashlightButton.isChecked()) {
            flash.open();
            flashlightButton.setKeepScreenOn(true);
        } else {
            flash.close();
            flashlightButton.setKeepScreenOn(false);
        }
    }

}
