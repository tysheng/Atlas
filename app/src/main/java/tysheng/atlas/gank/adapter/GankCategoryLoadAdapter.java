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
import tysheng.atlas.gank.bean.ResultsEntity;
import tysheng.atlas.gank.view.SectionsDecoration;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class GankCategoryLoadAdapter
        extends BaseLoadMoreRecyclerAdapter<ResultsEntity, GankCategoryLoadAdapter.MyViewHolder>
        implements SectionsDecoration.Adapter<GankCategoryLoadAdapter.SectionHeaderView> {


    private OnItemClickListener onItemClickListener;
    Context context;

    public GankCategoryLoadAdapter(Context context, List<ResultsEntity> data) {
        appendToList(data);
        this.context = context;
    }

    @Override
    public long getHeaderId(int position) {
        if (getItem(position) != null)
            return getItem(position).getPublish();
        return 0;
    }

    @Override
    public SectionHeaderView onCreateHeaderViewHolder(ViewGroup parent) {
        return new SectionHeaderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_section_header, parent, false));

    }

    @Override
    public void onBindHeaderViewHolder(SectionHeaderView viewHolder, int position) {
        if (getItem(position) != null)
            viewHolder.tv.setText(getItem(position).publishedAt.substring(5, 10));
    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final MyViewHolder mHolder, int position) {
        mHolder.type.setText(getItem(position).type);
        mHolder.who.setText(getItem(position).who);
        mHolder.publishedAt.setText(getItem(position).publishedAt.substring(0, 10));
        if (getItem(position).type.equals("福利")) {
            mHolder.image.setImageURI(Uri.parse(getItem(position).url));
            mHolder.image.setVisibility(View.VISIBLE);
            mHolder.desc.setVisibility(View.GONE);
        } else {
            mHolder.image.setVisibility(View.GONE);
            mHolder.desc.setVisibility(View.VISIBLE);
            mHolder.desc.setText(getItem(position).desc);
        }
        if (onItemClickListener != null) {
            mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickListener(v, mHolder.getLayoutPosition());
                }
            });
        }
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

    static class SectionHeaderView extends RecyclerView.ViewHolder {
        @Bind(R.id.tv)
        TextView tv;

        public SectionHeaderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
