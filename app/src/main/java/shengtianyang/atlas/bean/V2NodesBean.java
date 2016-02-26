package shengtianyang.atlas.bean;

/**
 * Created by shengtianyang on 16/2/26.
 */
public class V2NodesBean {

    /**
     * id : 1
     * name : babel
     * url : http://www.v2ex.com/go/babel
     * title : Project Babel
     * title_alternative : Project Babel
     * topics : 1117
     * header : Project Babel - 帮助你在云平台上搭建自己的社区
     * footer : V2EX 基于 Project Babel 驱动。Project Babel 是用 Python 语言写成的，运行于 Google App Engine 云计算平台上的社区软件。Project Babel 当前开发分支 2.5。最新版本可以从 <a href="http://github.com/livid/v2ex" target="_blank">GitHub</a> 获取。
     * created : 1272206882
     */

    private String url;
    private String title_alternative;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle_alternative(String title_alternative) {
        this.title_alternative = title_alternative;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle_alternative() {
        return title_alternative;
    }
}
