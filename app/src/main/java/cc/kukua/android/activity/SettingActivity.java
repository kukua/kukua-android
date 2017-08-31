package cc.kukua.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        ButterKnife.bind(this);
        setContentView(R.layout.activity_setting);

        //TODO: Fix bug with setToolbarTitle
        //setToolBarTitle(getString(R.string.settings));
    }


    public void setToolBarTitle(String title) {
        toolbarTitle.setText(title);
    }
}
