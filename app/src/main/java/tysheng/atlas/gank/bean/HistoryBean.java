package tysheng.atlas.gank.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shengtianyang on 16/3/28.
 */
public class HistoryBean implements Serializable {

    /**
     * error : false
     * results : ["2016-03-28","2016-03-25"]
     */

    public boolean error;
    public List<String> results;

    {
        results = new ArrayList<>();
        results.add("2016-04-07");
        results.add("2016-04-06");
        results.add("2016-04-05");
        results.add("2016-04-01");
    }
    public String[] getYMD(String str){
        return str.split("-");
    }


}
