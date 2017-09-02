package cc.kukua.android.model.server_response_model;

/**
 * Created by calistus on 30/08/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponseModel {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("password")
    @Expose
    private String password;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ForgotPasswordResponseModel{" +
                "state=" + state +
                ", password='" + password + '\'' +
                '}';
    }
}
