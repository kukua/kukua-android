package cc.kukua.android.utils;

import android.util.Log;

import cc.kukua.android.BuildConfig;

/**
 * @author Ilo Calistus
 */

public class LogUtils {

    public static void log(String tag, String message) {

        //Prevent logging when app goes to production for security reasons
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }

    }
     public static void logError(String tag, String message, Throwable error){
         if(BuildConfig.DEBUG){
             Log.e(tag,message,error);
         }
     }

}
