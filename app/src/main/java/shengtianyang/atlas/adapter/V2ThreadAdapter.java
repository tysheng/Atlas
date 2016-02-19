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
import shengtianyang.atlas.utils.TimeStamp;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2ThreadAdapter extends RecyclerView.Adapter<V2ThreadAdapter.mViewHolder> {


    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> data;
    private OnItemClickListener onItemClickListener;


    public V2ThreadAdapter(Context context, List<HashMap<String, String>> data) {
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
        View view = layoutInflater.inflate(R.layout.item_v2thread, parent, false);
        mViewHolder viewHolder = new mViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
            Map<String, String> map = data.get(position);
            holder.tvReplyContent.setText(map.get("content"));
            holder.tvReplyAuthor.setText(map.get("username"));
            holder.draweeReply.setImageURI(Uri.parse("http:" + map.get("avatar_normal")));
            String time = TimeStamp.getTimeDifference(Integer.parseInt(map.get("last_modified")));
            if (time.equals("0")) {
                time = "刚刚";
            } else if (time.equals("-1")) {
                time = "很久很久前";
            }
            holder.tvReplyTime.setText(time);

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
    public int getItemCount() {
        return data.size();
    }
//    public void addItem(int position){
//        data.add(position,"new  "+String.format("%.2f",Math.random()*100));
//        notifyItemInserted(position);
//    }
//    public void deleteItem(int position){
//        data.remove(position);
//        notifyItemRemoved(position);
//    }


    static class mViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.drawee_reply)
        SimpleDraweeView draweeReply;
        @Bind(R.id.tv_reply_author)
        TextView tvReplyAuthor;
        @Bind(R.id.tv_reply_time)
        TextView tvReplyTime;
        @Bind(R.id.tv_reply_content)
        TextView tvReplyContent;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
