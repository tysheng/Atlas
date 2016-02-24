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
import shengtianyang.atlas.bean.HeaderBean;
import shengtianyang.atlas.utils.TimeStamp;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2ThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> data;
    private HeaderBean headerBean;
    private OnItemClickListener onItemClickListener;


    public V2ThreadAdapter(Context context, List<HashMap<String, String>> data, HeaderBean headerBean) {
        this.data = data;
        this.layoutInflater = layoutInflater.from(context);
        this.headerBean = headerBean;
    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = layoutInflater.inflate(R.layout.thread_header, parent, false);
            return new VHHeader(view);
        } else if (viewType == TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.item_v2thread, parent, false);
            return new VHItem(view);
        }
        throw new RuntimeException("No matches with " + viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if(holder instanceof VHHeader){
            VHHeader vhHeader = (VHHeader)holder;
            vhHeader.draweeTopic.setImageURI(Uri.parse("http:" + headerBean.getDraweeTopic()));
            vhHeader.tvTopicAuthor.setText(headerBean.getTvTopicAuthor());
            vhHeader.tvTopicContent.setText(headerBean.getTvTopicContent());
            vhHeader.tvTopicNode.setText(headerBean.getTvTopicNode());
            vhHeader.tvTopicTitle.setText(headerBean.getTvTopicTitle());
            vhHeader.tvTopicTime.setText(TimeStamp.getFinalTimeDiffrence(headerBean.getTvTopicTime()));
        }else if (holder instanceof VHItem){
            VHItem vhItem = (VHItem)holder;
            Map<String, String> map = data.get(position-1);
            vhItem.tvReplyContent.setText(map.get("content"));
            vhItem.tvReplyAuthor.setText(map.get("username"));
            vhItem.draweeReply.setImageURI(Uri.parse("http:" + map.get("avatar_normal")));
            vhItem.tvReplyTime.setText(TimeStamp.getFinalTimeDiffrence(map.get("last_modified")));
            vhItem.tvReplyFloor.setText(position+"");
        }


//        holder.tv_time.setText(data.get(position).get("content").equals("")
//                ? "作者很懒,什么都没写~" : data.get(position).get("content"));

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
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }
    private boolean isPositionHeader(int position){
        return position == 0;
    }
    @Override
    public int getItemCount() {
        return data.size()+1;
    }
//    public void addItem(int position){
//        data.toolbar_add(position,"new  "+String.format("%.2f",Math.random()*100));
//        notifyItemInserted(position);
//    }
//    public void deleteItem(int position){
//        data.remove(position);
//        notifyItemRemoved(position);
//    }


    static class VHItem extends RecyclerView.ViewHolder {
        @Bind(R.id.drawee_reply)
        SimpleDraweeView draweeReply;
        @Bind(R.id.tv_reply_author)
        TextView tvReplyAuthor;
        @Bind(R.id.tv_reply_time)
        TextView tvReplyTime;
        @Bind(R.id.tv_reply_content)
        TextView tvReplyContent;
        @Bind(R.id.tv_reply_floor)
        TextView tvReplyFloor;

        public VHItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    static class VHHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.drawee_topic)
        SimpleDraweeView draweeTopic;
        @Bind(R.id.tv_topic_author)
        TextView tvTopicAuthor;
        @Bind(R.id.tv_topic_node)
        TextView tvTopicNode;
        @Bind(R.id.tv_topic_time)
        TextView tvTopicTime;
        @Bind(R.id.tv_topic_title)
        TextView tvTopicTitle;
        @Bind(R.id.tv_topic_content)
        TextView tvTopicContent;

        public VHHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
