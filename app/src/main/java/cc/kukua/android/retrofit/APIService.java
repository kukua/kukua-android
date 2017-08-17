package cc.kukua.android.retrofit;

import cc.kukua.android.model.DailyWeatherResponseModel;
import cc.kukua.android.model.HourlyWeatherResponseModel;
import cc.kukua.android.model.LoginResponseModel;
import cc.kukua.android.model.RegisterResponseModel;
import cc.kukua.android.model.RequestForecastResponseModel;
import cc.kukua.android.model.SendSmsResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by calistus on 30/07/2017.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("v1.0/register")
    Call<RegisterResponseModel> userSignUp(@Field("full_name") String full_name,
                                           @Field("email") String email,
                                           @Field("phone") String phone,
                                           @Field("password") String password);

    @FormUrlEncoded
    @POST("v1.0/login")
    Call<LoginResponseModel> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1.0/sendsms")
    Call<SendSmsResponseModel> sendsms(@Field("phonenumber") String phonenumber, @Field("message") String message);

    @FormUrlEncoded
    @POST("v1.0/requestweatherforecast")
    Call<RequestForecastResponseModel> requestweatherforecast(@Field("latitude") float latitude,
                                                              @Field("longtitude") float longtitude,
                                                              @Query("locality") String order);
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

}
