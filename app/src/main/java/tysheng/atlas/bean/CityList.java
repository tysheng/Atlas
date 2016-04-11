package tysheng.atlas.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by shengtianyang on 16/4/11.
 */
public class CityList extends RealmObject {
    private String name;
    private RealmList<SimpleWeather> list;

    public RealmList<SimpleWeather> getList() {
        return list;
    }

    public void setList(RealmList<SimpleWeather> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
