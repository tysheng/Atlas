package shengtianyang.atlas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.bean.V2NodesBean;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class V2NodeRecyclerAdapter extends RecyclerView.Adapter<V2NodeRecyclerAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<V2NodesBean> data;
    private OnItemClickListener onItemClickListener;


    public V2NodeRecyclerAdapter(Context context, List<V2NodesBean> data) {
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_v2node, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvNodeTitle.setText(data.get(position).getTitle_alternative());


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

//    public void addItem(int position,HashMap<String,String> map) {
////        data.toolbar_add(position, map);
//        notifyItemInserted(position);
//    }
//
//    public void deleteItem(int position) {
//        data.remove(position);
//        notifyItemRemoved(position);
//    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_node_title)
        TextView tvNodeTitle;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
