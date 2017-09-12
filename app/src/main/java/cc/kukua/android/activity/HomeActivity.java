package cc.kukua.android.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.model.server_response_model.forecast.RequestForecastResponseModel;
import cc.kukua.android.retrofit.APIService;
import cc.kukua.android.retrofit.RetrofitClient;
import cc.kukua.android.utils.LogUtils;
import cc.kukua.android.utils.UiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseCharacterActivity {
    @BindView(R.id.left_nav)
    LinearLayout leftNav;
    @BindView(R.id.right_nav)
    LinearLayout rightNav;
    @BindView(R.id.menu_temperature_text)
    TextView tvTemperature;
    @BindView(R.id.menu_calendar_day_word)
    TextView tvDayWord;
    @BindView(R.id.menu_calendar_day)
    TextView tvDayNumber;
    @BindView(R.id.menu_calendar_month)
    TextView tvMonth;
    @BindView(R.id.menu_share)
    ImageView menuShare;
    @BindView(R.id.menu_sms)
    ImageView menuSms;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.menu_prev_day)
    TextView menuPrevDay;
    @BindView(R.id.menu_prev_month)
    TextView menuPrevMonth;
    @BindView(R.id.menu_next_day)
    TextView menuNextDay;
    @BindView(R.id.menu_next_month)
    TextView menuNextMonth;
    @BindView(R.id.weather_details)
    TextView weatherDetails;
    @BindView(R.id.iv_settings)
    ImageView ivSettings;

    JSONObject response, profile_pic_data, profile_pic_url;
    ShareDialog shareDialog;

    private String TAG = HomeActivity.class.getSimpleName();


    public void openSettings(View view) {
        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
    }

    public void shareWeatherInfo(View view) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Weather Forecast for " +
                            getMonthWord())
                    .setContentDescription("temperature is "+ tvTemperature.getText() +" degrees")
                    .setShareHashtag(new ShareHashtag.Builder()
                            .setHashtag("#kuka")
                            .setHashtag("#weather")
                            .setHashtag("#kukaweather").build())
                    .build();
            shareDialog.show(linkContent);  // Show facebook ShareDialog
        }
    }


    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {

        super.onCreateResources(pOnCreateResourcesCallback);


        ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //session.checkLogin(HomeActivity.this);

                /**
                 * Get's weather from server and populate View with data
                 * @param lon the longitude
                 * @param lat the latitude
                 * @param timezone user location timezone
                 */
                Log.d(TAG, "Lat: " + session.getLatitude());
                getDayWeather(
                        Float.parseFloat(session.getLatitude()),
                        Float.parseFloat(session.getLongitude()),
                        session.getTimezone());

                menuSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeActivity.this, SendMessageActivity.class));
                    }
                });
                leftNav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO load next day weather
                    }
                });

                // Images right navigation
                rightNav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO load previous day weather
                    }
                });

                shareDialog = new ShareDialog(HomeActivity.this);  // initialize facebook shareDialog.

            }
        });


    }

    public void getDayWeather(float lat, float lon, String timezone) {
        UiUtils.showProgressDialog(HomeActivity.this, getString(R.string.please_wait));

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
                    LogUtils.log(TAG, "OnResponse: " + response.body().toString());
                    if (response.isSuccessful()) {
                        if (response.body().getType().equalsIgnoreCase("ForecastData")) {
                            tvTemperature.setText(response.body().getForecast().getWeather().getLoc().getObs().getT());

                            /**
                             * Display Calendar information from mobile phone
                             * You change ot to Date sent by the server based on user's/client recommnedation
                             */
                            tvDayWord.setText(getCalenderDay());
                            tvDayNumber.setText(getCalendarDayNumber() + "");
                            tvMonth.setText(getMonth());
                            menuNextMonth.setText(getMonthWord() + "");
                            menuNextDay.setText(getCalendarDayNumber() + 1 + "");
                            menuPrevDay.setText(getCalendarDayNumber() - 1 + "");
                            menuPrevMonth.setText(getMonthWord() + "");


                            LogUtils.log(TAG, "Temperature: " + response.body().getForecast().getWeather().getLoc().getObs().getT());
                        }
                    }

                }

                @Override
                public void onFailure(Call<RequestForecastResponseModel> call, Throwable t) {
                    UiUtils.dismissAllProgressDialogs();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getCalenderDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        // full name form of the day
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
    }

    private int getCalendarDayNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private String getMonth() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("MMMM").format(calendar.getTime());
    }

    private String getMonthWord() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("MMM").format(calendar.getTime());
    }

    private int getMonthNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected synchronized void onResume() {
        super.onResume();
        try {
            renderCharacter();
        } catch (Exception e) {

        }
    }
}
