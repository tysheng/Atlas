package tysheng.atlas.bean;

import java.io.Serializable;

/**
 * Created by shengtianyang on 16/4/16.
 */
public class FileInfo implements Serializable {
    public String url;
    public int size;
    public String name;

    public FileInfo(String url, int size, String name) {
        this.url = url;
        this.size = size;
        this.name = name;
    }
}
