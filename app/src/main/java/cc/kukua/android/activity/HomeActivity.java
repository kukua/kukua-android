package cc.kukua.android.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.LayoutGameActivity;
import org.andengine.util.HashUtils;
import org.andengine.util.debug.Debug;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.auth.LoginActivity;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.adapters.CustomizeListAdapter;
import cc.kukua.android.model.CustomizeList;
import cc.kukua.android.model.server_response_model.forecast.RequestForecastResponseModel;
import cc.kukua.android.retrofit.APIService;
import cc.kukua.android.retrofit.RetrofitClient;
import cc.kukua.android.utils.APIUtils;
import cc.kukua.android.utils.DoneCallback;
import cc.kukua.android.utils.LogUtils;
import cc.kukua.android.utils.UiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends LayoutGameActivity {
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

    SessionManager session;
    private String TAG = HomeActivity.class.getSimpleName();
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 640; //480

    // ===========================================================
    // Fields
    // ===========================================================
    private BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;


    private ITexture mParallaxLayerBackTexture;
    private ITexture mParallaxLayerMidTexture;

    private ITextureRegion mParallaxLayerBackTextureRegion;
    private ITextureRegion mParallaxLayerMidTextureRegion;
    private ITextureRegion mSVGTestTextureRegion;
    private ITextureRegion mSVGPreview1;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        ButterKnife.bind(this);
//        session = new SessionManager(getApplicationContext());
//        session.checkLogin();
//
//        leftNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO load next day weather
//            }
//        });
//
//        // Images right navigatin
//        rightNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO load previous day weather
//            }
//        });
//    }

    public void openSettings(View view) {
        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
    }


    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {

        this.mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.NEAREST);
        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mSVGTestTextureRegion = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "cloud.svg", 200, 200);
        this.mSVGPreview1 = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBuildableBitmapTextureAtlas, this, "category_preview1.svg", 294, 521);

        try {
            this.mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.mBuildableBitmapTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }

        this.mParallaxLayerBackTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_back.jpg");
        this.mParallaxLayerBackTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerBackTexture);
        this.mParallaxLayerBackTexture.load();

        this.mParallaxLayerMidTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/parallax_background_layer_mid.png");
        this.mParallaxLayerMidTextureRegion = TextureRegionFactory.extractFromTexture(this.mParallaxLayerMidTexture);
        this.mParallaxLayerMidTexture.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();

        ButterKnife.bind(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                session = new SessionManager(getApplicationContext());
                session.checkLogin(HomeActivity.this);

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
            }
        });


    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();

        final Scene scene = new Scene();
        final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
        scene.setBackground(autoParallaxBackground);

        final Sprite parallaxLayerBackSprite = new Sprite(0, 0, this.mParallaxLayerBackTextureRegion, vertexBufferObjectManager);
        parallaxLayerBackSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0.0f, parallaxLayerBackSprite));

        final Sprite parallaxLayerMidSprite = new Sprite(0, CAMERA_HEIGHT - this.mSVGTestTextureRegion.getHeight() - 2, this.mSVGTestTextureRegion, vertexBufferObjectManager);
        parallaxLayerMidSprite.setOffsetCenter(0, 0);
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5.0f, parallaxLayerMidSprite));


        final Sprite previewOneSprite = new Sprite(0, CAMERA_HEIGHT - this.mSVGPreview1.getHeight() - 100, this.mSVGPreview1, vertexBufferObjectManager);
        //previewOneSprite.setOffsetCenter(0, 0);
        scene.attachChild(previewOneSprite);
        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected int getRenderSurfaceViewID() {
        return R.id.xml_rendersurfaceview;

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
                            tvDayNumber.setText(getCalendarDayNumber()+"");
                            tvMonth.setText(getMonth());

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
}
