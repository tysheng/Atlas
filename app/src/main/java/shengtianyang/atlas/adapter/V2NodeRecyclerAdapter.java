package shengtianyang.atlas.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2NodeRecyclerAdapter extends RecyclerView.Adapter<V2NodeRecyclerAdapter.mViewHolder> {

    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> data;
    private OnItemClickListener onItemClickListener;


    public V2NodeRecyclerAdapter(Context context, List<HashMap<String, String>> data) {
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
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_v2node, parent, false);
        mViewHolder viewHolder = new mViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        Map<String, String> map = data.get(position);
        holder.tvNodeTitle.setText(map.get("title"));
        holder.draweeNodeAvatar.setImageURI(Uri.parse(map.get("avatar_normal")));
        holder.tvNodeHeader.setText(map.get("header"));
        holder.tvNodeTopic.setText("主题 "+map.get("topics"));
        holder.tvNodeStar.setText("收藏 "+map.get("stars"));

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

    public void addItem(int position,HashMap<String,String> map) {
        data.add(position, map);
        notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


    static class mViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.drawee_node_avatar)
        SimpleDraweeView draweeNodeAvatar;
        @Bind(R.id.tv_node_header)
        TextView tvNodeHeader;
        @Bind(R.id.tv_node_title)
        TextView tvNodeTitle;
        @Bind(R.id.tv_node_topic)
        TextView tvNodeTopic;
        @Bind(R.id.tv_node_star)
        TextView tvNodeStar;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
