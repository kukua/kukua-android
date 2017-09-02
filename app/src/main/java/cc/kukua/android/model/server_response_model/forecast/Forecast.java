package cc.kukua.android.model.server_response_model.forecast;

/**
 * Created by calistus on 01/09/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Forecast {

    @SerializedName("weather")
    @Expose
    private Weather weather;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "weather=" + weather +
                '}';
    }
}
