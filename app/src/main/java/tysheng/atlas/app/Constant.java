package tysheng.atlas.app;

/**
 * Created by shengtianyang on 16/2/2.
 */
public interface Constant {
    //V2EX
    String URL_V2_LASTED = "https://www.v2ex.com/api/topics/latest.json";
    String URL_V2_HOT = "https://www.v2ex.com/api/topics/hot.json";
    String URL_V2_TOPIC = "https://www.v2ex.com/api/topics/show.json";
    String URL_V2_REPLY = "https://www.v2ex.com/api/replies/show.json";
    String URL_V2_NODE_ALL = "https://www.v2ex.com/api/nodes/all.json";
    String URL_V2_NODE = "https://www.v2ex.com/api/nodes/show.json?name=";
    String URL_V2_NODE_GO = "https://www.v2ex.com/go/";
    String URL_V2_MEMBER = "https://www.v2ex.com/api/members/show.json";

    //和风天气
    String HF_WEATHER_APIKEY = "72242cee88b7717053de6a045245e582";
    String HF_WEATHER_API = "http://apis.baidu.com/heweather/weather/free";

    //百度车联网
    String URL_BAIDU_WEATHER = "http://api.map.baidu.com/telematics/v3/weather";
    String BAIDU_AK = "EIBnkLNbPzLIrKZoWIMXhb37";

    String SP_NAME = "sp_name";

    //微信
    String WX_APP_ID = "wx029d7cfe5492d0c3";
    String WX_APP_SECRET = "5ee73f83b047053ea4f773add5a3025d";

    //SharedPreferences
    String AVATAR_BITMAP = "avatar_bitmap";
    String GANK_TIP = "gank_tip";
    String USER_NAME = "USER_NAME";
    String USER_EMAIL = "USER_EMAIL";
    String IS_SETTING = "IS_SETTING";
    String THEME = "THEME";
    String IS_NIGHT = "IS_NIGHT";
}
/*
        最热主题

        相当于首页右侧的 10大每天的内容。

        https://www.v2ex.com/api/topics/hot.json

        Method:GET
        Authentication:None
        ---------------------------------------
        最新主题

        相当于首页的“全部”这个 tab 下的最新内容。

        https://www.v2ex.com/api/topics/latest.json

        Method:GET
        Authentication:None
        ---------------------------------------
        节点信息

        获得指定节点的名字，简介，URL 及头像图片的地址。

        https://www.v2ex.com/api/nodes/show.json

        Method:GET
        Authentication:None
        接受参数：

        name:节点名（V2EX 的节点名全是半角英文或者数字）
        例如：

        https://www.v2ex.com/api/nodes/show.json?name=python
        ---------------------------------------
        用户主页

        获得指定用户的自我介绍，及其登记的社交网站信息。

        https://www.v2ex.com/api/members/show.json

        Method:GET
        Authentication:None
        接受以下参数之一：

        username:用户名
        id:用户在 V2EX 的数字 ID
        例如：

        https://www.v2ex.com/api/members/show.json?username=Livid
        https://www.v2ex.com/api/members/show.json?id=1
*/
