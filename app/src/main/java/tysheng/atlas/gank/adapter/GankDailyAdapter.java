package tysheng.atlas.gank.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.gank.bean.GankCategory;

/**
 * Created by shengtianyang on 16/4/6.
 */
public class GankDailyAdapter extends RecyclerView.Adapter<GankDailyAdapter.MyViewHolder> {


    private Context context;
    GankCategory mItem;
    private OnItemClickListener onItemClickListener;
    int lastPosition = 0;

    public GankDailyAdapter(Context context, GankCategory list) {
        this.context = context;
        this.mItem = list;
    }

    public void add(GankCategory list) {
        if (list == null) {
            return;
        }
        mItem.results.addAll(list.results);
        notifyDataSetChanged();
    }

    public void clear() {
        mItem.results.clear();
        notifyDataSetChanged();
    }
    public GankCategory getItem(){
        return mItem;
    }

    public String[] getYMD(int pos) {
        String str = mItem.results.get(pos).publishedAt.substring(0, 10);
        return str.split("-");
    }

    public String getUrl(int pos) {
        return mItem.results.get(pos).url;
    }

    //
    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gank_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(context)
                .load(mItem.results.get(position).url)
                .centerCrop()
                .into(holder.image);
        holder.tvDate.setText(mItem.results.get(position).formatPublish());
        if (onItemClickListener != null) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(v, holder.getLayoutPosition());
                }
            });
            holder.tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(v, holder.getLayoutPosition());
                }
            });

        }
        showItemAnimation(holder, position);
    }

    /**
     * 上划动画效果
     */
    private void showItemAnimation(MyViewHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.mView, "translationY", 1f * holder.mView.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return mItem == null ? 0 : mItem.results.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_date)
        TextView tvDate;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);

        }
    }
}
