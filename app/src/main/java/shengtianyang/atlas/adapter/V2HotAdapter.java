package shengtianyang.atlas.adapter;

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
import shengtianyang.atlas.R;
import shengtianyang.atlas.bean.V2HotBean;
import shengtianyang.atlas.utils.TimeStamp;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2HotAdapter extends RecyclerView.Adapter<V2HotAdapter.MyViewHolder> {



    private LayoutInflater layoutInflater;
    private List<V2HotBean> data;
    private OnItemClickListener onItemClickListener;


    public V2HotAdapter(Context context, List<V2HotBean> data) {
        this.data = data;
        this.layoutInflater = layoutInflater.from(context);
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
        holder.draweePortrait.setImageURI(Uri.parse("http:" + data.get(position).getMember().getAvatar_normal()));
        holder.tvTime.setText(TimeStamp.getFinalTimeDiffrence(data.get(position).getLast_modified()));

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
//    public void addItem(int position){
//        data.toolbar_add(position,"new  "+String.format("%.2f",Math.random()*100));
//        notifyItemInserted(position);
//    }
//    public void deleteItem(int position){
//        data.remove(position);
//        notifyItemRemoved(position);
//    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.drawee_portrait)
        SimpleDraweeView draweePortrait;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_author)
        TextView tvAuthor;
        @Bind(R.id.tv_node)
        TextView tvNode;
        @Bind(R.id.tv_replies)
        TextView tvReplies;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
