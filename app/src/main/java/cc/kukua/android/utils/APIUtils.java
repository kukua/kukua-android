package cc.kukua.android.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cc.kukua.android.R;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.model.server_response_model.UserDetailResponseModel;
import cc.kukua.android.model.server_response_model.forecast.RequestForecastResponseModel;
import cc.kukua.android.retrofit.APIService;
import cc.kukua.android.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by calistus on 31/08/2017.
 */

@SuppressWarnings({"ConstantConditions", "Convert2Lambda", "unchecked"})
public class APIUtils {

    SessionManager session;

    public static void getDayWeather(final Context context, double lon, double lat, String timezone, final DoneCallback<HashMap> getDayWeatherDoneCallback) {
        UiUtils.showProgressDialog(context, context.getString(R.string.please_wait));

        APIService apiService = RetrofitClient.getClient().create(APIService.class);
        try {
            JSONObject parameterObject = new JSONObject();
            parameterObject.put("lat", lat);
            parameterObject.put("lon", lon);
            parameterObject.put("timezone", timezone);

            Call<RequestForecastResponseModel> call = apiService.requestWeatherForecast(parameterObject.toString());
            call.enqueue(new Callback<RequestForecastResponseModel>() {
                @Override
                public void onResponse(Call<RequestForecastResponseModel> call, Response<RequestForecastResponseModel> response) {
                    UiUtils.dismissAllProgressDialogs();
                    LogUtils.log("HomeCharacterActivity", "OnResponse: " + response.body().toString());
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getType().equalsIgnoreCase("ForecastData")) {
                                HashMap homeDetail = new HashMap();
                                homeDetail.put("temp", response.body().getForecast().getWeather().getLoc().getObs().getT());
                                getDayWeatherDoneCallback.done(homeDetail, null);
                            } else {
                                getDayWeatherDoneCallback.done(null, spitException(response.errorBody().toString()));
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<RequestForecastResponseModel> call, Throwable t) {
                    UiUtils.dismissAllProgressDialogs();
                    getDayWeatherDoneCallback.done(null, spitException(t.getMessage()));

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void getUserDetail(final Context context, final String id, final DoneCallback<HashMap> userDetailMap) {
        APIService apiService = RetrofitClient.getClient().create(APIService.class);

        JSONObject callParams = new JSONObject();
        try {
            callParams.put("user_id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<UserDetailResponseModel> call = apiService.getUserDetail(callParams.toString());

        call.enqueue(new Callback<UserDetailResponseModel>() {
            @Override
            public void onResponse(Call<UserDetailResponseModel> call, Response<UserDetailResponseModel> response) {
                UiUtils.dismissAllProgressDialogs();
                Log.d("UserDetail", "Response: " + response.body().toString());
                SessionManager session = new SessionManager(context);
                session.createLoginSession(response.body().getFirstName(),
                        response.body().getLastName(),
                        response.body().getEmail(),
                        response.body().getPhoneNumber(),
                        "",
                        response.body().getCharacterId(),
                        response.body().getPassword(),
                        response.body().getCharacterId(),
                        response.body().getLocation().toString(),
                        response.body().getTimezone(),
                        response.body().getPurpose(),
                        response.body().getLocation().get(0),
                        response.body().getLocation().get(1),
                        id);
                userDetailMap.done(session.getUserDetails(), null);
            }

            @Override
            public void onFailure(Call<UserDetailResponseModel> call, Throwable t) {
                UiUtils.dismissAllProgressDialogs();

            }
        });
    }

    private static Exception spitException(String message) {
        return new Exception(message != null ? message : "Error in operation");
    }
}


