package tysheng.atlas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tysheng.atlas.R;

/**
 * Created by shengtianyang on 16/1/28.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.MyViewHolder> {


    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> data;
    private OnItemClickListener onItemClickListener;


    public WeatherListAdapter(Context context, List<HashMap<String, String>> data) {
        this.data=data;
        this.layoutInflater = LayoutInflater.from(context);
    }

//    private void initData(int num) {
//        data = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            HashMap<String, String> map = new HashMap<>();
//            data.add(map);
//        }
//    }

    public interface OnItemClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void addItem(int pos, HashMap<String, String> map) {
        data.add(pos, map);
        notifyDataSetChanged();
    }
    public void addItem(HashMap<String, String> map) {
        data.add(map);
        notifyDataSetChanged();
    }
    public void removeItem(int pos){
        data.remove(pos);
        notifyDataSetChanged();
    }
    public void clearItem(){
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_weatherlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (!data.get(position).isEmpty()) {
            if (data.get(position).get("ll_aqi") == null) {
                holder.tvAqi.setText(data.get(position).get("aqi"));
                holder.tcQlty.setText(data.get(position).get("qlty"));
                holder.tvTextaqi.setText("AQI");
                holder.llAqi.setVisibility(View.VISIBLE);
            } else {
                holder.llAqi.setVisibility(View.GONE);
            }
            holder.tvBrf.setText(data.get(position).get("brf"));
            holder.tvCity.setText(data.get(position).get("city_name"));
            holder.tvTmp.setText(data.get(position).get("tmp"));
            holder.tvTxt.setText(data.get(position).get("txt"));
            holder.tvCond.setText(data.get(position).get("cond"));

            if (onItemClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onItemClickListener.onClickListener(v, holder.getLayoutPosition());
                        return true;
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_aqi)
        TextView tvAqi;
        @BindView(R.id.tv_textaqi)
        TextView tvTextaqi;
        @BindView(R.id.tc_qlty)
        TextView tcQlty;
        @BindView(R.id.ll_aqi)
        LinearLayout llAqi;
        @BindView(R.id.tv_brf)
        TextView tvBrf;
        @BindView(R.id.tv_txt)
        TextView tvTxt;
        @BindView(R.id.tv_tmp)
        TextView tvTmp;
        @BindView(R.id.tv_cond)
        TextView tvCond;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
