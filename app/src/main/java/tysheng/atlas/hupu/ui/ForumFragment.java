package tysheng.atlas.hupu.ui;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tysheng.atlas.R;
import tysheng.atlas.api.RetrofitSingleton;
import tysheng.atlas.app.MyApplication;
import tysheng.atlas.base.BaseFragment;
import tysheng.atlas.hupu.adapter.ThreadAdapter;
import tysheng.atlas.hupu.api.ForumApi;
import tysheng.atlas.hupu.bean.ForumsData;
import tysheng.atlas.hupu.bean.ThreadListData;
import tysheng.atlas.hupu.bean.ThreadListResult;
import tysheng.atlas.ui.fragment.WebviewFragment;

/**
 * Created by shengtianyang on 16/3/24.
 */
public class ForumFragment extends BaseFragment {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    Fragment forumFragment;


    //    ForumAdapter mAdapter;
    ThreadAdapter tAdapter;
    //    ForumsData data;
    ThreadListResult threadData;

    public ForumFragment() {
    }

    public static ForumFragment getInstance() {
        return new ForumFragment();
    }


    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.fm_hupu);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hupu;
    }

    @Override
    protected void initData() {
        forumFragment = getInstance();
        initSwipe();
//        data = new ForumsData();
        threadData = new ThreadListResult();
        tAdapter = new ThreadAdapter(frmContext, threadData);
//        mAdapter = new ForumAdapter(frmContext, data);
//        rv.setAdapter(mAdapter);
        rv.setAdapter(tAdapter);
        rv.setLayoutManager(new LinearLayoutManager(frmContext));
        tAdapter.setOnItemClickListener(new ThreadAdapter.OnItemClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                forumFragment = manager.findFragmentByTag("forum");
                WebviewFragment webviewFragment = new WebviewFragment("http://bbs.hupu.com/"+threadData.data.get(position).tid+".html");
                transaction.hide(forumFragment)
                        .addToBackStack(null)
                        .setCustomAnimations(0, 0, R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .add(R.id.fg_main, webviewFragment, "nodeweb" + position)
                        .commitAllowingStateLoss();
            }
        });
//        tAdapter.setOnLoadListener(new ThreadAdapter.onLoadListener() {
//            @Override
//            public void onLoad(View v) {
//                v.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ShowToast("正在加载");
//                        getThread();
//                    }
//                });
//            }
//        });
//        getForum();

//        getThreadPost();

//        getThread();

    }

    private void initSwipe() {
        swipe.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                threadData.data.clear();
                getThread();
            }
        });
        swipe.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipe.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                swipe.setRefreshing(true);
                getThread();
            }
        });
    }

    private void getThread() {
        Map<String, String> params = getHttpRequestMap();
        params.put("fid", "34");//data.data.get(6).sub.get(0).data.get(0).getFid()
        params.put("lastTid", "0");
        params.put("limit", String.valueOf(20));
        params.put("isHome", "1");
        params.put("stamp", System.currentTimeMillis() + "");//"1458824939059"
        params.put("password", "");
        params.put("token", "");
        params.put("night", "0");
        params.put("special", "1");
        params.put("type", "2");

        String sign = getRequestSign(params);
        RetrofitSingleton.getForumApi(MyApplication.getInstance(), ForumApi.BASE_URL)
                .getThreadsList(sign, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThreadListData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ThreadListData threadListData) {
                        threadData.data.addAll(threadListData.result.data);
                        tAdapter.notifyDataSetChanged();
                        stopSwipe();

                    }
                });
    }

    private void stopSwipe() {
        if (swipe.isRefreshing())
            swipe.setRefreshing(false);
    }

    private void getThreadPost() {
        Map<String, String> params = new HashMap<>();
        params.put("tid", "15806243");
        params.put("fid", "1048");
        params.put("page", "1");
        params.put("client", getDeviceId());

        RetrofitSingleton.getForumApi(MyApplication.getInstance(), ForumApi.BASE_URL_6)
                .getThreadPostList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }


    private void getForum() {
        Map<String, String> params = getHttpRequestMap();
        String sign = getRequestSign(params);
        RetrofitSingleton.getForumApi(MyApplication.getInstance(), ForumApi.BASE_URL)
                .getForums(sign, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForumsData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ForumsData forumsData) {
//                        data.data.addAll(forumsData.data);
//                        mAdapter.notifyDataSetChanged();

                    }
                });
    }

//    public Observable<ThreadListData> getThreadsList(String fid, String lastTid, int limit, String lastTamp, String type, List<String> list) {
//        Map<String, String> params = getHttpRequestMap();
//        params.put("fid", fid);
//        params.put("lastTid", lastTid);
//        params.put("limit", String.valueOf(limit));
//        params.put("isHome", "1");
//        params.put("stamp", lastTamp);
//        params.put("password", "0");
//        if (list == null) {
//            params.put("special", "0");
//            params.put("type", type);
//        } else {
//            JSONArray jSONArray = new JSONArray();
//            for (String str : list) {
//                jSONArray.put(str);
//            }
//            params.put("gids", jSONArray.toString());
//        }
//        String sign = getRequestSign(params);
//        return mForumService.getThreadsList(sign, params).subscribeOn(Schedulers.io());
//    }

    public String getRequestSign(Map<String, String> map) {
        ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().compareTo(rhs.getKey());
            }
        });
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i = i + 1) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            Map.Entry<String, String> map1 = list.get(i);
            builder.append(map1.getKey()).append("=").append(map1.getValue());
        }
        builder.append("HUPU_SALT_AKJfoiwer394Jeiow4u309");
        return getMD5(builder.toString());
    }

    /**
     * 使用MD5算法对传入的key进行加密并返回。
     */
    public String getMD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public Map<String, String> getHttpRequestMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("client", getDeviceId());
        map.put("night", "0");
//        if (mUserStorage.isLogin()) {
//            try {
//                map.put("token", URLEncoder.encode(mUserStorage.getToken(), "UTF-8"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return map;
    }

    public String getAndroidId() {
        return Settings.Secure.getString(frmContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getDeviceId() {
        String deviceId;
        TelephonyManager tm = (TelephonyManager) frmContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getDeviceId() == null) {
            deviceId = getAndroidId();
        } else {
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
