package cc.kukua.android.model.server_response_model.forecast;

/**
 * Created by calistus on 01/09/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fc {

    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("t")
    @Expose
    private String t;
    @SerializedName("tf")
    @Expose
    private String tf;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("wn")
    @Expose
    private String wn;
    @SerializedName("ws")
    @Expose
    private String ws;
    @SerializedName("rh")
    @Expose
    private String rh;
    @SerializedName("pp")
    @Expose
    private String pp;
    @SerializedName("pr")
    @Expose
    private String pr;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getTf() {
        return tf;
    }

    public void setTf(String tf) {
        this.tf = tf;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getWn() {
        return wn;
    }

    public void setWn(String wn) {
        this.wn = wn;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    @Override
    public String toString() {
        return "Fc{" +
                "dt='" + dt + '\'' +
                ", t='" + t + '\'' +
                ", tf='" + tf + '\'' +
                ", s='" + s + '\'' +
                ", wn='" + wn + '\'' +
                ", ws='" + ws + '\'' +
                ", rh='" + rh + '\'' +
                ", pp='" + pp + '\'' +
                ", pr='" + pr + '\'' +
                '}';
    }
}
