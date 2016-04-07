package tysheng.atlas.gank.bean;

import java.io.Serializable;

import tysheng.atlas.gank.utils.GankUtils;

/**
 * Created by shengtianyang on 16/4/7.
 */
public class ResultsEntity implements Serializable{
    public String _id;
    public String _ns;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
    public String formatData(){
        String monthAndDay = publishedAt.substring(5,10);
        String[] strings = monthAndDay.split("-");
        return strings[0].replace("0", "") + "月" + strings[1].replace("0", "") + "日";
    }

    public long getPublish() {
        return GankUtils.getDataTime(publishedAt);
    }
}
