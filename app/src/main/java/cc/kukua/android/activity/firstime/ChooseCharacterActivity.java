package cc.kukua.android.activity.firstime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.CharacterCustomizationActivity;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.adapters.ChooseCharacterPagerAdapter;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.model.server_response_model.RegisterResponseModel;
import cc.kukua.android.model.server_request_model.RegisterQueryModel;
import cc.kukua.android.retrofit.APIService;
import cc.kukua.android.retrofit.RetrofitClient;
import cc.kukua.android.utils.LogUtils;
import cc.kukua.android.utils.UiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseCharacterActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager pager;
    @BindView(R.id.left_nav)
    ImageView leftNav;
    @BindView(R.id.right_nav)
    ImageView rightNav;
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_next3)
    Button btnNextButton;

    SessionManager session;
    private String TAG = ChooseCharacterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());

        initCharacter2Session();
        
        tvToolbarTitle.setText(getString(R.string.choose_your_character));

        pager.setAdapter(new ChooseCharacterPagerAdapter(getSupportFragmentManager()));

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCharacter1Session();
                int tab = pager.getCurrentItem();
                if (tab ==1) {
                    tab--;
                    DummyDataProvider.userDetail.put("character",String.valueOf(tab));
                    pager.setCurrentItem(tab);
                } else if (tab == 0) {
                    pager.setCurrentItem(tab);


                    DummyDataProvider.userDetail.put("character",String.valueOf(tab));
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCharacter2Session();
                int tab = pager.getCurrentItem();
                tab++;
                pager.setCurrentItem(tab);
            }
        });
        btnNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DummyDataProvider.userDetail.put("timezone", TimeZone.getDefault().getID());
                LogUtils.log(TAG,DummyDataProvider.userDetail.toString());
                registerUser();
            }
        });
    }

    public void initCharacter1Session() {
        session.saveHat(R.drawable.item_hat1);
        session.saveHead(R.drawable.item_face1);
        session.saveShirt(R.drawable.item_torso1);
        session.saveShoes(R.drawable.item_lower_leg1);
        session.savePants(R.drawable.item_upper_leg1);
        session.saveHand(R.drawable.item_lower_arm1);
        session.savePants(R.drawable.item_upper_arm1);
    }

    private void initCharacter2Session() {
        session.saveHat(R.drawable.item_hat2);
        session.saveHead(R.drawable.item_face2);
        session.saveShirt(R.drawable.item_torso2);
        session.saveShoes(R.drawable.item_lower_leg2);
        session.savePants(R.drawable.item_upper_leg2);
        session.saveHand(R.drawable.item_lower_arm2);
        session.savePants(R.drawable.item_upper_arm2);
    }

    private void registerUser(){
        APIService apiInterface = RetrofitClient.getClient().create(APIService.class);

        UiUtils.showProgressDialog(ChooseCharacterActivity.this, "Pleas wait...");
        //showProgress(true);
        // prepare call in Retrofit 2.0
        try {
   /*         String[] location = DummyDataProvider.userDetail.get("latLong").split(",");
            LogUtils.log(TAG,location[0]+" "+location[1]);
            JSONObject paramObject = new JSONObject();



            paramObject.put("first_name",DummyDataProvider.userDetail.get("firstName"));
            paramObject.put("last_name", DummyDataProvider.userDetail.get("last_name"));
            paramObject.put("email", DummyDataProvider.userDetail.get("email"));
            paramObject.put("phone_number", DummyDataProvider.userDetail.get("phone"));
            paramObject.put("password", DummyDataProvider.userDetail.get("password"));
            paramObject.put("character_id", DummyDataProvider.userDetail.get("character"));
            paramObject.put("timezone", DummyDataProvider.userDetail.get("timezone"));
            paramObject.put("location", location);
            paramObject.put("purpose", DummyDataProvider.userDetail.get("purpose"));
*/

            List<Double> locationList = new ArrayList<>();
            locationList.add(60.32);
            locationList.add(24.97);

            final RegisterQueryModel registerQueryModel = new RegisterQueryModel();
            registerQueryModel.setFirstName(DummyDataProvider.userDetail.get("firstName"));
            registerQueryModel.setLastName(DummyDataProvider.userDetail.get("lastName"));
            registerQueryModel.setEmail(DummyDataProvider.userDetail.get("email"));
            registerQueryModel.setPassword(DummyDataProvider.userDetail.get("password"));
            registerQueryModel.setPhoneNumber(DummyDataProvider.userDetail.get("phone"));
            registerQueryModel.setCharacterId(DummyDataProvider.userDetail.get("character"));
            registerQueryModel.setTimezone(DummyDataProvider.userDetail.get("timezone"));
            registerQueryModel.setLocation(locationList);
            registerQueryModel.setPurpose(DummyDataProvider.userDetail.get("purpose"));

            Call<RegisterResponseModel> userCall = apiInterface.register(registerQueryModel);
            userCall.enqueue(new Callback<RegisterResponseModel>() {
                @Override
                public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                    UiUtils.dismissAllProgressDialogs();
                    final RegisterQueryModel regQueryModel = registerQueryModel;

                    LogUtils.log(TAG, "OnResponse: " + response.body().toString());

                    if (response.isSuccessful()) {
                        if (response.body().getState() == 200 && response.body().getId()!=null) {
                            session.createLoginSession(
                                    regQueryModel.getFirstName(),
                                    regQueryModel.getLastName(),
                                    regQueryModel.getEmail(),
                                    regQueryModel.getPhoneNumber(),
                                    "",
                                    "",
                                    regQueryModel.getPassword(),
                                    regQueryModel.getCharacterId(),
                                    "",
                                    "",
                                    regQueryModel.getPurpose(),
                                    regQueryModel.getLocation().get(0),
                                    regQueryModel.getLocation().get(1),
                                    response.body().getId()
                            );
                            startActivity(new Intent(ChooseCharacterActivity.this, CharacterCustomizationActivity.class));
                        } else if (response.body().getState() != 200) {
                            UiUtils.dismissAllProgressDialogs();
                            //UiUtils.showSafeToast("Oops! Something went wrong!");
                            Toast.makeText(ChooseCharacterActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                    UiUtils.dismissAllProgressDialogs();
                    Toast.makeText(ChooseCharacterActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"NETWORK ERROR", t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


}
}

