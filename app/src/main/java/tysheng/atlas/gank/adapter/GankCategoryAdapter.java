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
import tysheng.atlas.gank.bean.GankDaily;
import tysheng.atlas.gank.bean.ResultsEntity;
import tysheng.atlas.gank.utils.GankUtils;
import tysheng.atlas.gank.view.SectionsDecoration;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class GankCategoryAdapter
        extends RecyclerView.Adapter<GankCategoryAdapter.MyViewHolder>
        implements SectionsDecoration.Adapter<GankCategoryAdapter.SectionHeaderView> {


    private List<ResultsEntity> data;
    private OnItemClickListener onItemClickListener;
    private Context context;


    public GankCategoryAdapter(Context context, List<ResultsEntity> data) {
        this.data = data;
        this.context = context;
    }

    public void add(GankDaily daily) {
        if (daily.results.androidList != null) data.addAll(daily.results.androidList);
        if (daily.results.iOSList != null) data.addAll(daily.results.iOSList);
        if (daily.results.前端List != null) data.addAll(daily.results.前端List);
        if (daily.results.appList != null) data.addAll(daily.results.appList);
        if (daily.results.拓展资源List != null) data.addAll(daily.results.拓展资源List);
        if (daily.results.瞎推荐List != null) data.addAll(daily.results.瞎推荐List);
        if (daily.results.休息视频List != null) data.addAll(0, daily.results.休息视频List);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public long getHeaderId(int position) {
        if (data.get(position).type.equals("福利"))
            return 0;
        if (data.get(position) != null)
            return data.get(position).getPublish();
        return 0;
    }

    @Override
    public SectionHeaderView onCreateHeaderViewHolder(ViewGroup parent) {
        return new SectionHeaderView(LayoutInflater.from(context).inflate(R.layout.item_gank_section_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(SectionHeaderView viewHolder, int position) {
        if (data.get(position) != null && !data.get(position).type.equals("福利")) {
            viewHolder.tv.setText(formatTime(data.get(position).publishedAt.substring(5, 10)));
        }
    }

    public String formatTime(String str) {
        String[] strings = str.split("-");
        return strings[0].replace("0", "") + "月" + strings[1].replace("0", "") + "日";
    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder mHolder, final int position) {
        mHolder.who.setText(GankUtils.getGankStyleStr(data.get(position).who == null ? "None" : data.get(position).who,
                data.get(position).desc));

        if (data.get(position).type.equals("福利")) {
            mHolder.image.setImageURI(Uri.parse(data.get(position).url));
            mHolder.image.setVisibility(View.VISIBLE);
        } else {
            mHolder.image.setVisibility(View.GONE);

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

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

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
