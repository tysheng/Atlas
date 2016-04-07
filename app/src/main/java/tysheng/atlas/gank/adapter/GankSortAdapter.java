package tysheng.atlas.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.gank.bean.GankViewPagerItem;

/**
 * Created by shengtianyang on 16/4/6.
 */
public class GankSortAdapter extends RecyclerView.Adapter<GankSortAdapter.MyViewHolder> {


    private Context context;
    private GankViewPagerItem mItem;
//    private OnItemClickListener onItemClickListener;

    public GankSortAdapter(Context context, GankViewPagerItem list) {
        this.context = context;
        this.mItem = list;
    }
//
//    interface OnItemClickListener {
//        void OnItemClick(View view, int position);
//    }
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.onItemClickListener = listener;
//    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_draggable, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(mItem.mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mItem == null ? 0 : mItem.mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
