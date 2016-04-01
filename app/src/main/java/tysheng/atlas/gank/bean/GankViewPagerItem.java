package tysheng.atlas.gank.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shengtianyang on 16/4/1.
 */
public class GankViewPagerItem implements Serializable {

    public List<String> mList;


    public String gank_android;
    public String gank_ios;
    public String gank_video;
    public String gank_fuli;
    public String gank_resource;
    public String gank_front;
    public String gank_recommend;
    public String gank_all;
    public String gank_app;


    {
        gank_all = "all";
        gank_android = "Android";
        gank_front = "前端";
        gank_recommend = "瞎推荐";
        gank_fuli = "福利";
        gank_resource = "拓展资源";
        gank_ios = "iOS";
        gank_video = "休息视频";
        gank_app = "App";
        mList = new ArrayList<>();
        mList.add(gank_all);
        mList.add(gank_android);
        mList.add(gank_ios);
        mList.add(gank_front);
        mList.add(gank_fuli);
        mList.add(gank_recommend);
        mList.add(gank_resource);
        mList.add(gank_video);
        mList.add(gank_app);
    }


}
