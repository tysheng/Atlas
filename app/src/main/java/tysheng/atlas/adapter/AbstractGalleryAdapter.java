package tysheng.atlas.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shengtianyang on 16/4/30.
 */
public abstract class AbstractGalleryAdapter<T> extends PagerAdapter {
    protected List<T> images;
    protected Context mContext;

    public AbstractGalleryAdapter(List<T> images, Context context) {
        this.images = images;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(setLayoutId(), container, false);
        final PhotoViewAttacher mAttacher;
        final ImageView imageView = (ImageView) view.findViewById(setImageViewId());
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(mContext)
                .load(setItemUrl(position))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                        mAttacher.update();
                    }
                });
        container.addView(view);
        return view;
    }

    protected abstract String setItemUrl(int position);

    protected abstract int setLayoutId();

    protected abstract int setImageViewId();

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return setPageTitle(position);
    }

    protected abstract CharSequence setPageTitle(int position);

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
