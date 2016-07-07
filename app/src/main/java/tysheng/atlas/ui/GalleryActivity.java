package tysheng.atlas.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import tysheng.atlas.R;
import tysheng.atlas.adapter.MyGalleryAdapter;
import tysheng.atlas.base.BaseActivity;
import tysheng.atlas.gank.bean.GankCategory;
import tysheng.atlas.gank.bean.GankResult;
import tysheng.atlas.gank.utils.GankUtils;
import tysheng.atlas.utils.SaveImage;
import tysheng.atlas.widget.ViewPagerFixed;

/**
 * Created by shengtianyang on 16/4/30.
 */
public class GalleryActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private static final String POSITION = "POSITION";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPagerFixed mViewPager;
    @BindView(R.id.indicator)
    TextView mIndicator;
    @BindView(R.id.desc)
    TextView mDesc;
    private List<GankResult> mList;
    private MyGalleryAdapter mAdapter;
    public static final String GALLERY_LIST = "GALLERY_LIST";
    private int mPosition;
    String mUrl;
    String mTitle;
    @Override
    public void initData() {
        parseIntent();
        initBar();
        mAdapter = new MyGalleryAdapter(mList, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDesc.setText(mAdapter.getPageTitle(position));
                mIndicator.setText(String.valueOf(position + 1) + "/" + mAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(mPosition);
        mDesc.setText(mAdapter.getPageTitle(mPosition));
        mIndicator.setText(String.valueOf(1 + mPosition) + "/" + mAdapter.getCount());

    }

    private void parseIntent() {
        GankCategory category = (GankCategory) getIntent().getSerializableExtra(GALLERY_LIST);
        mList = category.results;
        mPosition = getIntent().getIntExtra(POSITION, 0);
    }

    public static Intent newIntent(Context context, Serializable list, int pos) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(GALLERY_LIST, list);
        intent.putExtra(POSITION, pos);
        return intent;
    }

    private void initBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setTitle("");
        mToolbar.inflateMenu(R.menu.menu_picture);
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveImageToGallery();
                break;
            case R.id.action_share:
                mUrl = mList.get(mViewPager.getCurrentItem()).url;
                mTitle = mList.get(mViewPager.getCurrentItem()).desc;
                GankUtils.share(actContext, mUrl, mTitle);
            default:
                break;
        }
        return true;
    }
    private void saveImageToGallery() {
        mUrl = mList.get(mViewPager.getCurrentItem()).url;
        mTitle = mList.get(mViewPager.getCurrentItem()).desc;
//        SaveImage.saveImageAndGetPathObservable(this, mUrl, mTitle)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Uri>() {
//                    @Override
//                    public void call(Uri uri) {
//                        File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
//                        String msg = String.format(getString(R.string.picture_has_save_to),
//                                appDir.getAbsolutePath());
//                        showSnackbar(mToolbar, msg);
//                    }
//                });
        Glide.with(this)
                .load(mUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        SaveImage.saveImageToGallery(actContext,resource,mTitle);
                        File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                        String msg = String.format(getString(R.string.picture_has_save_to),
                                appDir.getAbsolutePath());
                        showSnackbar(mToolbar, msg);
                    }
                });

    }

}
