package cc.kukua.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;

public class SettingActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.btn_personal_information)
    Button btnPersonalInformation;
    @BindView(R.id.btn_account_information)
    Button btnAccountInformation;
    @BindView(R.id.btn_gps_information)
    Button btnGpsInformation;
    @BindView(R.id.btn_character_information)
    Button btnCharacterInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        
        setToolBarTitle(getString(R.string.settings));

    }


    public void setToolBarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void openCharacterCustomization(View view) {
        startActivity(new Intent(SettingActivity.this, CharacterCustomizationActivity.class));
    }
    public void openUpdatePersonalInformation(View view) {
        startActivity(new Intent(SettingActivity.this, UpdatePersonalInformationActivity.class));
    }
    public void openUpdateAccount(View view) {
        startActivity(new Intent(SettingActivity.this, UpdateAccountActivity.class));
    }
    public void openUpdateGps(View view) {
        startActivity(new Intent(SettingActivity.this, UpdateLocationActivity.class));
    }
}
