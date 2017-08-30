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
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ForgotPasswordResponse{" +
                "state=" + state +
                ", message='" + message + '\'' +
                '}';
    }
}