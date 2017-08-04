package cc.kukua.android;

import android.app.Application;
import com.microsoft.windowsazure.mobileservices.*;

/**
 * Created by mistaguy on 7/28/2017.
 */

public class KukuaApp extends Application {
    private MobileServiceClient mClient;
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize logging first to log all operations
        try {
            mClient = new MobileServiceClient(
                    Constant.azureUrl,
                    this
            );
        }catch (Exception ex){
        }
    }
}