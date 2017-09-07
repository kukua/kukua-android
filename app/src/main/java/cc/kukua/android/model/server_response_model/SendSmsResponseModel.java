package cc.kukua.android.model.server_response_model;

/**
 * Created by Ilo Calistus
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendSmsResponseModel {

    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("id")
    @Expose
    private String id;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SendSMSResponseModel{" +
                "state=" + state +
                ", id='" + id + '\'' +
                '}';
    }
}
