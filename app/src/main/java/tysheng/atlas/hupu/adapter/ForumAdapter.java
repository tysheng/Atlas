package tysheng.atlas.hupu.adapter;

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
import tysheng.atlas.hupu.bean.ForumsData;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private ForumsData data;
    private OnItemClickListener onItemClickListener;
private Context mContext;

    public ForumAdapter(Context context, ForumsData data) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
mContext = context;    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_forum, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(data.data.get(position).sub.get(0).data.get(0).getName());
        Glide.with(mContext)
                .load(data.data.get(position).sub.get(0).data.get(0).getLogo())
                .into(holder.logo);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickListener(v, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.logo)
        ImageView logo;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
