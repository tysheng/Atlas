package tysheng.atlas.gank.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.gank.bean.GankCategory;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class GankCategoryLoadAdapter extends BaseLoadMoreRecyclerAdapter<GankCategory.ResultsEntity, GankCategoryLoadAdapter.MyViewHolder> {


    private OnItemClickListener onItemClickListener;


    public GankCategoryLoadAdapter(Context context, List<GankCategory.ResultsEntity> data) {
        appendToList(data);
    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final MyViewHolder mHolder, int position) {
        mHolder.desc.setText(getItem(position).desc);
        mHolder.type.setText(getItem(position).type);
        mHolder.who.setText(getItem(position).who);
        mHolder.publishedAt.setText(getItem(position).publishedAt.substring(0, 10));
        if (getItem(position).type.equals("福利")) {
            mHolder.image.setImageURI(Uri.parse(getItem(position).url));
            mHolder.image.setVisibility(View.VISIBLE);
        } else {
            mHolder.image.setVisibility(View.GONE);
        }
        if (onItemClickListener != null) {
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickListener(v, mHolder.getLayoutPosition());
                }
            });
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.publishedAt)
        TextView publishedAt;
        @Bind(R.id.type)
        TextView type;
        @Bind(R.id.who)
        TextView who;
        @Bind(R.id.image)
        SimpleDraweeView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
