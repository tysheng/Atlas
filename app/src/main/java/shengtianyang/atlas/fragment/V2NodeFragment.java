package shengtianyang.atlas.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import shengtianyang.atlas.R;
import shengtianyang.atlas.adapter.V2NodeRecyclerAdapter;
import shengtianyang.atlas.app.Constant;
import shengtianyang.atlas.app.MyApplication;
import shengtianyang.atlas.base.BaseFragment;
import shengtianyang.atlas.bean.V2NodesBean;
import shengtianyang.atlas.net.VolleyIF;
import shengtianyang.atlas.net.VolleyUtils;

/**
 * Created by shengtianyang on 16/2/2.
 */
public class V2NodeFragment extends BaseFragment {
    @Bind(R.id.rv_v2node)
    RecyclerView rvV2node;
    V2NodeRecyclerAdapter mAdapter;
    List<V2NodesBean> data;
    Fragment v2NodeFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2node;
    }

    public static V2NodeFragment getInstance() {
        return new V2NodeFragment();
    }

    @Override
    public void onStop() {
        super.onStop();
        MyApplication.getRequestQueue().cancelAll("V2NodeFragment");
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        mAdapter = new V2NodeRecyclerAdapter(frmContext, data);
        mAdapter.setOnItemClickListener(new V2NodeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                v2NodeFragment = manager.findFragmentByTag("node");
                WebviewFragment webviewFragment = new WebviewFragment(data.get(position).getUrl());
                transaction.hide(v2NodeFragment)
                        .addToBackStack(null)
                        .add(R.id.fg_main, webviewFragment, "nodeweb" + position)
                        .commitAllowingStateLoss();
            }
        });
        rvV2node.setLayoutManager(new GridLayoutManager(frmContext, 2));
        rvV2node.setAdapter(mAdapter);
        VolleyUtils.Get(Constant.URL_V2_NODE_ALL, "V2NodeFragment", new VolleyIF(VolleyIF.mListener) {
            @Override
            public void onMyResponse(String response) {
                data.addAll(JSON.parseArray(response, V2NodesBean.class));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}
