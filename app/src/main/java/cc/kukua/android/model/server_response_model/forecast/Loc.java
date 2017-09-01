package cc.kukua.android.model.server_response_model.forecast;

/**
 * Created by calistus on 01/09/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loc {

    @SerializedName("obs")
    @Expose
    private Obs obs;
    @SerializedName("fc")
    @Expose
    private List<Fc> fc = null;

    public Obs getObs() {
        return obs;
    }

    public void setObs(Obs obs) {
        this.obs = obs;
    }

    public List<Fc> getFc() {
        return fc;
    }

    public void setFc(List<Fc> fc) {
        this.fc = fc;
    }

    @Override
    public String toString() {
        return "Loc{" +
                "obs=" + obs +
                ", fc=" + fc +
                '}';
    }
}