package tysheng.atlas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.bean.V2HotBean;
import tysheng.atlas.utils.TimeStamp;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2HotAdapter extends RecyclerView.Adapter<V2HotAdapter.MyViewHolder> {



    private LayoutInflater layoutInflater;
    private List<V2HotBean> data;
    private OnItemClickListener onItemClickListener;
    private Context mContext;


    public V2HotAdapter(Context context, List<V2HotBean> data) {
        this.data = data;
        this.layoutInflater = layoutInflater.from(context);
        mContext = context;
    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_v2hot, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvNode.setText(data.get(position).getNode().getTitle());
        holder.tvAuthor.setText(data.get(position).getMember().getUsername());
        holder.tvReplies.setText(data.get(position).getReplies()+"");
        holder.tvTime.setText(TimeStamp.getFinalTimeDiffrence(data.get(position).getLast_modified()));
        Glide.with(mContext)
                .load("http:" + data.get(position).getMember().getAvatar_normal())
                .centerCrop()
                .into(holder.draweePortrait);
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
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.drawee_portrait)
        ImageView draweePortrait;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_node)
        TextView tvNode;
        @BindView(R.id.tv_replies)
        TextView tvReplies;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
