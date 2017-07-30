package cc.kukua.android.retrofit;

import cc.kukua.android.model.LoginResponseModel;
import cc.kukua.android.model.RegisterResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by calistus on 30/07/2017.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("v1.0/register")
    Call<RegisterResponseModel> userSignUp(@Field("full_name") String full_name, @Field("email") String email, @Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1.0/login")
    Call<LoginResponseModel> login(@Field("email") String email, @Field("password") String password);
}
