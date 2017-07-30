package cc.kukua.android.model;

/**
 * Created by calistus on 26/07/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponseModel {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("token")
    @Expose
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterResponseModel{" +
                "success=" + success +
                ", result='" + result + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
