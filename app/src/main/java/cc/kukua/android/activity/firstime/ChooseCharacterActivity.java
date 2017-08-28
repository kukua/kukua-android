package cc.kukua.android.activity.firstime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.HomeActivity;
import cc.kukua.android.activity.auth.LoginActivity;
import cc.kukua.android.adapters.ChooseCharacterPagerAdapter;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.model.LoginResponseModel;
import cc.kukua.android.model.RegisterResponseModel;
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

    private String TAG = ChooseCharacterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        ButterKnife.bind(this);
        
        tvToolbarTitle.setText(getString(R.string.choose_your_character));

        pager.setAdapter(new ChooseCharacterPagerAdapter(getSupportFragmentManager()));

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                if (tab > 0) {
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
                int tab = pager.getCurrentItem();
                tab++;
                pager.setCurrentItem(tab);
            }
        });
        btnNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DummyDataProvider.userDetail.put("timezone", TimeZone.getDefault().getID());
                LogUtils.log(TAG,"TimeZone: "+TimeZone.getDefault().getID());
                LogUtils.log(TAG,DummyDataProvider.userDetail.toString());
            }
        });
    }

    private void registerUser{
        APIService apiInterface = RetrofitClient.getClient().create(APIService.class);

        UiUtils.showProgressDialog(ChooseCharacterActivity.this, "Pleas wait...");
        //showProgress(true);
        // prepare call in Retrofit 2.0
        try {
            String[] location = DummyDataProvider.userDetail.get("latLong").split(",");
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", DummyDataProvider.userDetail.get("email"));
            paramObject.put("password", DummyDataProvider.userDetail.get("password"));
            paramObject.put("first_name",DummyDataProvider.userDetail.get("firstName"));
            paramObject.put("last_name", DummyDataProvider.userDetail.get("last_name"));
            paramObject.put("phone_number", DummyDataProvider.userDetail.get("phone"));
            paramObject.put("password", DummyDataProvider.userDetail.get("password"));
            paramObject.put("character_id", DummyDataProvider.userDetail.get("character"));
            paramObject.put("timezone", DummyDataProvider.userDetail.get("timezone"));
            paramObject.put("location", location);
            paramObject.put("purpose", DummyDataProvider.userDetail.get("purpose"));

            Call<RegisterResponseModel> userCall = apiInterface.userSignUp(paramObject.toString());
            userCall.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    UiUtils.dismissAllProgressDialogs();
                    LogUtils.log(TAG, "OnResponse: " + response.body().toString());

                    if (response.isSuccessful()) {
                        if (response.body().getLogin() == true) {
                            session.createLoginSession("", "","","","","","","","","","");
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else if (response.body().getLogin() == false) {
                            UiUtils.dismissAllProgressDialogs();
                            //UiUtils.showSafeToast("Oops! Something went wrong!");
                            Toast.makeText(LoginActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                    UiUtils.showSafeToast("Oops! Something went wrong.");

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
}

