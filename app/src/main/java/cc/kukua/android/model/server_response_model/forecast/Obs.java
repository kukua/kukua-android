package cc.kukua.android.model.server_response_model.forecast;

/**
 * Created by calistus on 01/09/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Obs {

    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("dist")
    @Expose
    private String dist;
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
    @SerializedName("p")
    @Expose
    private String p;
    @SerializedName("rh")
    @Expose
    private String rh;
    @SerializedName("v")
    @Expose
    private String v;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

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

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Obs{" +
                "station='" + station + '\'' +
                ", dist='" + dist + '\'' +
                ", dt='" + dt + '\'' +
                ", t='" + t + '\'' +
                ", tf='" + tf + '\'' +
                ", s='" + s + '\'' +
                ", wn='" + wn + '\'' +
                ", ws='" + ws + '\'' +
                ", p='" + p + '\'' +
                ", rh='" + rh + '\'' +
                ", v='" + v + '\'' +
                '}';
    }
}
