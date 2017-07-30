package cc.kukua.android.model;

/**
 * Created by calistus on 30/07/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("result")
    @Expose
    private String result;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "success=" + success +
                ", result='" + result + '\'' +
                '}';
    }

}
