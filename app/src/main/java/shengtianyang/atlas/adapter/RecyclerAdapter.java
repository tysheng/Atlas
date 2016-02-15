package shengtianyang.atlas.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

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
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.mViewHolder> {

    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> data;
    private OnItemClickListener onItemClickListener;


    public RecyclerAdapter(Context context, List<HashMap<String, String>> data) {
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
        View view = layoutInflater.inflate(R.layout.item_v2hot, parent, false);
        mViewHolder viewHolder = new mViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        try {
            Map<String,String> map = data.get(position);
            holder.tvTitle.setText(map.get("title"));
            JSONObject nObject = new JSONObject(map.get("node"));
            JSONObject uObject = new JSONObject(map.get("member"));
            holder.tvNode.setText(nObject.optString("title"));
            holder.tvAuthor.setText(uObject.optString("username"));
            holder.draweePortrait.setImageURI(Uri.parse("http:"+ uObject.optString("avatar_large")));
            String time = TimeStamp.getTimeDifference(Integer.parseInt(map.get("last_touched")));
            if(time.equals("0")){
                time = "刚刚";
            }else if(time.equals("-1")){
                time = "很久很久前";
            }
            holder.tvTime.setText(time);
        } catch (JSONException e) {
            e.printStackTrace();
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


    static class mViewHolder extends RecyclerView.ViewHolder  {

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

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
