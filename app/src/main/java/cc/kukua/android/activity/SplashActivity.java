package cc.kukua.android.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import cc.kukua.android.R;
import cc.kukua.android.activity.auth.LoginActivity;
import cc.kukua.android.activity.auth.SessionManager;

/**
 * Created by mistaguy on 7/28/2017.
 */

public class SplashActivity extends BaseActivity {
    /**
     * Initialize the splashscreen and show circular progress
     * @param savedInstanceState is called in that instance then a new instance of the activity will
     *                           be created, with whatever savedInstanceState the previous instance
     *                           had generated from onSaveInstanceState
     */

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        session = new SessionManager(getApplicationContext());
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SplashActivity.this,R.color.colorPrimary));
        }

        // TODO check for updates every week
        // checkForUpdates();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(session.isLoggedIn()){
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                finish();
            }
        }, 3000);
    }

    //
//    @Override
//    public void onResume() {
//        super.onResume();
//        // ... your own onResume implementation
//        checkForCrashes();
//    }
//
//    private void checkForCrashes() {
//        CrashManager.register(this);
//    }


//    private void checkForUpdates() {
//        // Remove this for store builds!
//        // UpdateManager.register(this);
//    }
//
//    private void unregisterManagers() {
//        UpdateManager.unregister();
//    }

//
//    @Override
//    public void onPause() {
//        super.onPause();
//        unregisterManagers();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unregisterManagers();
//    }
}