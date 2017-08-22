package cc.kukua.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cc.kukua.android.R;
import cc.kukua.android.activity.auth.SessionManager;

public class HomeActivity extends AppCompatActivity {

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
    }
}
