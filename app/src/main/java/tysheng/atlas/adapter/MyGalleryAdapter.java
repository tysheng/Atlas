package tysheng.atlas.adapter;

import android.content.Context;

import java.util.List;

import tysheng.atlas.R;
import tysheng.atlas.gank.bean.GankResult;

/**
 * Created by shengtianyang on 16/4/30.
 */
public class MyGalleryAdapter extends AbstractGalleryAdapter<GankResult> {
    public MyGalleryAdapter(List<GankResult> images, Context context) {
        super(images, context);
    }

    @Override
    protected String setItemUrl(int position) {
        return images.get(position).url;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_gallery;
    }

    @Override
    protected int setImageViewId() {
        return R.id.imageView;
    }

    @Override
    protected CharSequence setPageTitle(int position) {
        return images.get(position).desc;
    }
}
