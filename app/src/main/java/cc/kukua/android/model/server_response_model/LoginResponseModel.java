package cc.kukua.android.model.server_response_model;

/**
 * Created by calistus on 30/07/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("login")
    @Expose
    private Boolean login;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "state=" + state +
                ", login='" + login + '\'' +
                '}';
    }

}
