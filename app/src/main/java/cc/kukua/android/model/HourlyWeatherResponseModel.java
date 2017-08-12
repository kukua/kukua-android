package cc.kukua.android.model;

/**
 * Created by acellam guy
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyWeatherResponseModel {

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
        return "HourlyWeatherResponseModel{" +
                "success=" + success +
                ", result='" + result + '\'' +
                '}';
    }

}
