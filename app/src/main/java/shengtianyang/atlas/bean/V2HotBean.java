package shengtianyang.atlas.bean;

/**
 * Created by shengtianyang on 16/2/26.
 */
public class V2HotBean {

    /**
     * id : 259190
     * title : 大学生可以办理招商银行信用卡了
     * url : http://www.v2ex.com/t/259190
     * content : 招商银行 YOUNG 卡功能介绍：
     1)在校大学生专属
     2)达标即赠吉列男士理容尊享套装

     https://ccclub.cmbchina.com/MCA/Student/sselectcard.aspx?cardSel=2245&mgm=Qs7IcWtpag9Ufv%2fnmsu28KxDhHeDUP2wMa6GiRLKCGA%3d&WTmc_id=M17SJYH205BH026100MT

     申请链接（对我有点小好处，对你没有坏处 :）
     * content_rendered : 招商银行 YOUNG 卡功能介绍：<br />1)在校大学生专属 <br />2)达标即赠吉列男士理容尊享套装 <br /><br /><a target="_blank" href="https://ccclub.cmbchina.com/MCA/Student/sselectcard.aspx?cardSel=2245&amp;mgm=Qs7IcWtpag9Ufv/nmsu28KxDhHeDUP2wMa6GiRLKCGA=&amp;WTmc_id=M17SJYH205BH026100MT" rel="nofollow">https://ccclub.cmbchina.com/MCA/Student/sselectcard.aspx?cardSel=2245&amp;mgm=Qs7IcWtpag9Ufv%2fnmsu28KxDhHeDUP2wMa6GiRLKCGA%3d&amp;WTmc_id=M17SJYH205BH026100MT</a><br /><br />申请链接（对我有点小好处，对你没有坏处 :）
     * replies : 64
     * member : {"id":9071,"username":"Ryans","tagline":"","avatar_mini":"//cdn.v2ex.co/avatar/4271/8466/9071_mini.png?m=1337600408","avatar_normal":"//cdn.v2ex.co/avatar/4271/8466/9071_normal.png?m=1337600408","avatar_large":"//cdn.v2ex.co/avatar/4271/8466/9071_large.png?m=1337600408"}
     * node : {"id":16,"name":"share","title":"分享发现","title_alternative":"Share","url":"http://www.v2ex.com/go/share","topics":18404,"avatar_mini":"//cdn.v2ex.co/navatar/c74d/97b0/16_mini.png?m=1456308353","avatar_normal":"//cdn.v2ex.co/navatar/c74d/97b0/16_normal.png?m=1456308353","avatar_large":"//cdn.v2ex.co/navatar/c74d/97b0/16_large.png?m=1456308353"}
     * created : 1456447942
     * last_modified : 1456447942
     * last_touched : 1456481001
     */

    private int id;
    private String title;
    private String url;
    private String content;
    private String content_rendered;
    private int replies;
    /**
     * id : 9071
     * username : Ryans
     * tagline :
     * avatar_mini : //cdn.v2ex.co/avatar/4271/8466/9071_mini.png?m=1337600408
     * avatar_normal : //cdn.v2ex.co/avatar/4271/8466/9071_normal.png?m=1337600408
     * avatar_large : //cdn.v2ex.co/avatar/4271/8466/9071_large.png?m=1337600408
     */

    private MemberEntity member;
    /**
     * id : 16
     * name : share
     * title : 分享发现
     * title_alternative : Share
     * url : http://www.v2ex.com/go/share
     * topics : 18404
     * avatar_mini : //cdn.v2ex.co/navatar/c74d/97b0/16_mini.png?m=1456308353
     * avatar_normal : //cdn.v2ex.co/navatar/c74d/97b0/16_normal.png?m=1456308353
     * avatar_large : //cdn.v2ex.co/navatar/c74d/97b0/16_large.png?m=1456308353
     */

    private NodeEntity node;
    private int last_modified;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent_rendered(String content_rendered) {
        this.content_rendered = content_rendered;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public void setNode(NodeEntity node) {
        this.node = node;
    }

    public void setLast_modified(int last_modified) {
        this.last_modified = last_modified;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public String getContent_rendered() {
        return content_rendered;
    }

    public int getReplies() {
        return replies;
    }

    public MemberEntity getMember() {
        return member;
    }

    public NodeEntity getNode() {
        return node;
    }

    public int getLast_modified() {
        return last_modified;
    }

    public static class MemberEntity {
        private String username;
        private String avatar_normal;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setAvatar_normal(String avatar_normal) {
            this.avatar_normal = avatar_normal;
        }

        public String getUsername() {
            return username;
        }

        public String getAvatar_normal() {
            return avatar_normal;
        }
    }

    public static class NodeEntity {
        private String name;
        private String title;
        private String title_alternative;
        private String url;
        private int topics;
        private String avatar_normal;

        public void setName(String name) {
            this.name = name;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTitle_alternative(String title_alternative) {
            this.title_alternative = title_alternative;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setTopics(int topics) {
            this.topics = topics;
        }

        public void setAvatar_normal(String avatar_normal) {
            this.avatar_normal = avatar_normal;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public String getTitle_alternative() {
            return title_alternative;
        }

        public String getUrl() {
            return url;
        }

        public int getTopics() {
            return topics;
        }

        public String getAvatar_normal() {
            return avatar_normal;
        }
    }
}
