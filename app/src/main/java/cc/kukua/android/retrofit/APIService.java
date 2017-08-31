package cc.kukua.android.retrofit;

import cc.kukua.android.model.server_response_model.DailyWeatherResponseModel;
import cc.kukua.android.model.server_response_model.HourlyWeatherResponseModel;
import cc.kukua.android.model.server_response_model.LoginResponseModel;
import cc.kukua.android.model.server_response_model.RegisterResponseModel;
import cc.kukua.android.model.server_response_model.RequestForecastResponseModel;
import cc.kukua.android.model.server_response_model.SendSmsResponseModel;
import cc.kukua.android.model.server_request_model.RegisterQueryModel;
import cc.kukua.android.model.server_response_model.ForgotPasswordResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by calistus on 30/07/2017.
 */

public interface APIService {

    @Headers("Content-Type: application/json")
    @POST("api/user/login")
    Call<LoginResponseModel> login(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("/api/user/create")
    Call<RegisterResponseModel> register(@Body RegisterQueryModel body);

    @FormUrlEncoded
    @POST("v1.0/sendsms")
    Call<SendSmsResponseModel> sendsms(@Field("phonenumber") String phonenumber, @Field("message") String message);

    @Headers("Content-Type: application/json")
    @POST("api/forecast/create")
    Call<RequestForecastResponseModel> requestWeatherForecast(@Body String body);

    @FormUrlEncoded
    @POST("v1.0/dailyweatherforecast")
    Call<DailyWeatherResponseModel> dailyweatherforecast(@Field("latitude") float latitude,
                                                         @Field("longtitude") float longtitude,
                                                         @Query("locality") String order);

    @FormUrlEncoded
    @POST("v1.0/hourlyeatherforecast")
    Call<HourlyWeatherResponseModel> hourlyeatherforecast(@Field("latitude") float latitude,
                                                          @Field("longtitude") float longtitude,
                                                          @Query("locality") String order);

    @Headers("Content-Type: application/json")
    @POST("api/user/password/reset")
    Call<ForgotPasswordResponseModel> forgotPassword(@Body String body);
}
