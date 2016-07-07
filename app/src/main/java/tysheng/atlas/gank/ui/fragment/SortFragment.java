package tysheng.atlas.gank.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import tysheng.atlas.R;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.gank.adapter.DragSortRecycler;
import tysheng.atlas.gank.adapter.GankSortAdapter;
import tysheng.atlas.gank.bean.GankViewPagerItem;
import tysheng.atlas.utils.ACache;

/**
 * Created by shengtianyang on 16/4/2.
 */
public class SortFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    GankSortAdapter mAdapter;
    private GankViewPagerItem mItem;
    ACache mCache;

    @Override
    protected void setTitle() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initData() {
        mCache = ACache.get(frmContext);
        mItem = (GankViewPagerItem) mCache.getAsObject("GankViewPagerItem");
        if (mItem == null)
            mItem = new GankViewPagerItem();
        mAdapter = new GankSortAdapter(frmContext, mItem);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(frmContext));
        recyclerView.setItemAnimator(null);
        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.tv);
        dragSortRecycler.setFloatingAlpha(0.4f);
        dragSortRecycler.setFloatingBgColor(Color.LTGRAY);
        recyclerView.addItemDecoration(dragSortRecycler);
        recyclerView.addOnItemTouchListener(dragSortRecycler);
        recyclerView.addOnScrollListener(dragSortRecycler.getScrollListener());
        dragSortRecycler.setOnItemMovedListener(new DragSortRecycler.OnItemMovedListener() {
            @Override
            public void onItemMoved(int from, int to) {
                String temp = mItem.mList.remove(from);
                mItem.mList.add(to, temp);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void saveData() {
        mCache.put("GankViewPagerItem", mItem, ACache.TIME_DAY * 30);
    }

    @OnClick(R.id.save_fab)
    public void onClick() {
        saveData();
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
