package cc.kukua.android.activity.firstime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.BaseActivity;
import cc.kukua.android.activity.HomeActivity;
import cc.kukua.android.activity.auth.LoginActivity;
import cc.kukua.android.activity.auth.RegisterActivity;

public class FirstTimeActivity extends AppCompatActivity {
    @BindView(R.id.btn_submit)
    Button signInButton;
    @BindView(R.id.txt_signup)
    TextView signUpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        ButterKnife.bind(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstTimeActivity.this, HomeActivity.class));
                finish();
            }
        });
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstTimeActivity.this, PersonalInfoActivity.class));
            }
        });

    }
}
