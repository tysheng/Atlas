package tysheng.atlas.bean;


import java.io.Serializable;

/**
 * Created by shengtianyang on 16/2/26.
 */
public class V2ReplyBean implements Serializable{


    /**
     * id : 2931245
     * thanks : 0
     * content : 任务队列方面可以看看 celery
     * content_rendered : 任务队列方面可以看看 celery
     * member : {"id":14615,"username":"aggron","tagline":"","avatar_mini":"//cdn.v2ex.co/avatar/5c31/65e9/14615_mini.png?m=1348125897","avatar_normal":"//cdn.v2ex.co/avatar/5c31/65e9/14615_normal.png?m=1348125897","avatar_large":"//cdn.v2ex.co/avatar/5c31/65e9/14615_large.png?m=1348125897"}
     * created : 1456393732
     * last_modified : 1456393732
     */

    private int id;
    private String content;
    /**
     * id : 14615
     * username : aggron
     * tagline :
     * avatar_mini : //cdn.v2ex.co/avatar/5c31/65e9/14615_mini.png?m=1348125897
     * avatar_normal : //cdn.v2ex.co/avatar/5c31/65e9/14615_normal.png?m=1348125897
     * avatar_large : //cdn.v2ex.co/avatar/5c31/65e9/14615_large.png?m=1348125897
     */

    private MemberEntity member;
    private int last_modified;

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public MemberEntity getMember() {
        return member;
    }

    public int getLast_modified() {
        return last_modified;
    }

    public static class MemberEntity implements Serializable {
        private String username;
        private String avatar_normal;


        public String getUsername() {
            return username;
        }

        public String getAvatar_normal() {
            return avatar_normal;
        }
    }
}
