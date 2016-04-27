package tysheng.atlas.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.bean.HeaderBean;
import tysheng.atlas.bean.V2ReplyBean;
import tysheng.atlas.utils.TimeStamp;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2ThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater layoutInflater;
    private List<V2ReplyBean> data;
    private HeaderBean headerBean;
//    private OnItemClickListener onItemClickListener;
private Context mContext;

    public V2ThreadAdapter(Context context, List<V2ReplyBean> data, HeaderBean headerBean) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
        this.headerBean = headerBean;
        mContext = context;
    }

//    public interface OnItemClickListener {
//        void onClickListener(View view, int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.onItemClickListener = listener;
//    }
    /**
     * 上划动画效果
     */
    private void showItemAnimation(VHItem holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.mView, "translationY", 1f * holder.mView.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }
    int lastPosition = 0;
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

    public void addItem(List<V2ReplyBean> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHHeader) {
            VHHeader vhHeader = (VHHeader) holder;
            Glide.with(mContext)
                    .load("http:" + headerBean.getDraweeTopic())
                    .centerCrop()
                    .into(vhHeader.draweeTopic);
            vhHeader.tvTopicAuthor.setText(headerBean.getTvTopicAuthor());
            vhHeader.tvTopicContent.setText(headerBean.getTvTopicContent());
            vhHeader.tvTopicNode.setText(headerBean.getTvTopicNode());
            vhHeader.tvTopicTitle.setText(headerBean.getTvTopicTitle());
            vhHeader.tvTopicTime.setText(TimeStamp.getFinalTimeDiffrence(headerBean.getTvTopicTime()));
        } else if (holder instanceof VHItem) {
            VHItem vhItem = (VHItem) holder;
            vhItem.tvReplyContent.setText(data.get(position - 1).getContent());
            vhItem.tvReplyAuthor.setText(data.get(position - 1).getMember().getUsername());
            Glide.with(mContext)
                    .load("http:" + data.get(position - 1).getMember().getAvatar_normal())
                    .centerCrop()
                    .into(vhItem.draweeReply);
            vhItem.tvReplyTime.setText(TimeStamp.getFinalTimeDiffrence(data.get(position - 1).getLast_modified()));
            vhItem.tvReplyFloor.setText(position + "");
            showItemAnimation(vhItem,position);
        }


//        if (onItemClickListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onClickListener(v, holder.getLayoutPosition());
//                }
//            });
//        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }


    static class VHItem extends RecyclerView.ViewHolder {
        @Bind(R.id.drawee_reply)
        ImageView draweeReply;
        @Bind(R.id.tv_reply_author)
        TextView tvReplyAuthor;
        @Bind(R.id.tv_reply_time)
        TextView tvReplyTime;
        @Bind(R.id.tv_reply_content)
        TextView tvReplyContent;
        @Bind(R.id.tv_reply_floor)
        TextView tvReplyFloor;
        View mView;
        public VHItem(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

    }

    static class VHHeader extends RecyclerView.ViewHolder {
        @Bind(R.id.drawee_topic)
        ImageView draweeTopic;
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
