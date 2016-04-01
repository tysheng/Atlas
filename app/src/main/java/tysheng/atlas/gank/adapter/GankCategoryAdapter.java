package tysheng.atlas.gank.adapter;

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
import tysheng.atlas.R;
import tysheng.atlas.gank.bean.GankCategory;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class GankCategoryAdapter extends RecyclerView.Adapter<GankCategoryAdapter.MyViewHolder> {


    private LayoutInflater layoutInflater;
    private List<GankCategory.ResultsEntity> data;
    private OnItemClickListener onItemClickListener;


    public GankCategoryAdapter(Context context, List<GankCategory.ResultsEntity> data) {
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
        View view = layoutInflater.inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.desc.setText(data.get(position).desc);
        holder.type.setText(data.get(position).type);
        holder.who.setText(data.get(position).who);
        holder.publishedAt.setText(data.get(position).publishedAt.substring(0,10));
        if (data.get(position).type.equals("福利")){
            holder.image.setImageURI(Uri.parse(data.get(position).url));
            holder.image.setVisibility(View.VISIBLE);
        }
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

        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.publishedAt)
        TextView publishedAt;
        @Bind(R.id.type)
        TextView type;
        @Bind(R.id.who)
        TextView who;
        @Bind(R.id.image)
        SimpleDraweeView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
