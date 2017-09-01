package cc.kukua.android.model.server_response_model.forecast;

/**
 * Created by calistus on 01/09/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("loc")
    @Expose
    private Loc loc;

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "loc=" + loc +
                '}';
    }
}