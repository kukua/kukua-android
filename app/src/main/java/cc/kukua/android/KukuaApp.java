package cc.kukua.android;

import android.app.Application;

import com.microsoft.windowsazure.mobileservices.*;

import net.hockeyapp.android.metrics.MetricsManager;

/**
 * Created by mistaguy on 7/28/2017.
 */

public class KukuaApp extends Application {
    private MobileServiceClient mClient;

    private static KukuaApp _INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        _INSTANCE = this;
        // Initialize logging first to log all operations
        try {
            mClient = new MobileServiceClient(
                    Constant.azureUrl,
                    this
            );
        } catch (Exception ex) {
        }

        // add this to your main activity's onCreate()-callback
        MetricsManager.register(this);
    }

    public static KukuaApp getInstance() {
        return _INSTANCE;
    }
}