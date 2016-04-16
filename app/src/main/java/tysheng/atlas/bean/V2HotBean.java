package tysheng.atlas.bean;

import java.io.Serializable;

/**
 * Created by shengtianyang on 16/2/26.
 */
public class V2HotBean implements Serializable{
    

    public int id;
    public String title;
    public String url;
    public String content;
    public String content_rendered;
    public int replies;


    public MemberEntity member;

    public NodeEntity node;
    public int last_modified;

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

    public static class MemberEntity implements Serializable{
        public String username;
        public String avatar_normal;

        public String getUsername() {
            return username;
        }

        public String getAvatar_normal() {
            return avatar_normal;
        }
    }

    public static class NodeEntity implements Serializable{
        public String name;
        public String title;
        public String title_alternative;
        public String url;
        public int topics;
        public String avatar_normal;

        public void setName(String name) {
            this.name = name;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public void setUrl(String url) {
            this.url = url;
        }


        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }


        public String getUrl() {
            return url;
        }

    }
}
