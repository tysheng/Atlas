package tysheng.atlas.hupu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tysheng.atlas.R;
import tysheng.atlas.hupu.bean.ThreadListResult;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class ThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_Footer = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater layoutInflater;
    private ThreadListResult data;
    private OnItemClickListener onItemClickListener;

    public ThreadAdapter(Context context, ThreadListResult data) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.item_hpthread, parent, false);
            return new Item(view);
        } else if (viewType == TYPE_Footer) {
            View view = layoutInflater.inflate(R.layout.item_footer_load, parent, false);
            return new Footer(view);
        }
        throw new RuntimeException("No matches with " + viewType);
    }

    @Override
    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()) {
//            return TYPE_Footer;
//        }
        return TYPE_ITEM;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Item) {
            final Item item = (Item) holder;
            item.title.setText(data.data.get(position).title);
            item.name.setText(data.data.get(position).userName);
            item.reply.setText(data.data.get(position).replies);
            item.time.setText(data.data.get(position).time);
            if (data.data.get(position).lightReply.equals("0"))
                item.light.setVisibility(View.INVISIBLE);
            else
                item.light.setText(data.data.get(position).lightReply);

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClickListener(v, item.getLayoutPosition());
                    }
                });
            }
        }
//        else if (holder instanceof Footer) {
//            Footer footer = (Footer) holder;
//
//            footer.pb.setVisibility(View.VISIBLE);
//
//        }

    }

    @Override
    public int getItemCount() {
        return data.data.size();
    }


    static class Item extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.reply)
        TextView reply;
        @BindView(R.id.light)
        TextView light;

        public Item(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    static class Footer extends RecyclerView.ViewHolder {

        @BindView(R.id.pb)
        ProgressBar pb;

        public Footer(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
