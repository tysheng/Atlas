package tysheng.atlas.bean;

import io.realm.RealmObject;

/**
 * Created by shengtianyang on 16/4/11.
 */
public class SimpleWeather extends RealmObject {
    private String ll_aqi;
    private String aqi;
    private String qlty;
    private String brf;
    private String city_name;
    private String temp;
    private String txt;
    private String cond;


    public String getLl_aqi() {
        return ll_aqi;
    }

    public void setLl_aqi(String ll_aqi) {
        this.ll_aqi = ll_aqi;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }
}
