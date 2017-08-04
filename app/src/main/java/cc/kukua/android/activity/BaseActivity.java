package cc.kukua.android.activity;

import android.support.v7.app.AppCompatActivity;

import net.hockeyapp.android.CrashManager;

/**
 * Created by mistaguy on 8/4/2017.
 */

public class BaseActivity  extends AppCompatActivity {
    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

}