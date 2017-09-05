package cc.kukua.android.activity.auth;

/**
 * Created by calistus on 18/08/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "KukuaPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_CHARACTER_ID= "character_id";
    public static final String KEY_LOCATION= "location";
    public static final String KEY_TIMEZONE = "timezone";
    public static final String KEY_PHONE_NUMBER = "phone";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CHARACTER_URL = "user_photo_url";
    public static final String KEY_USER_ID= "user_id";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE= "longitude";
    public static final String KEY_PURPOSE = "purpose";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String phone,
                                   String token,
                                   String characterURL,
                                   String password,
                                   String characterID,
                                   String location,
                                   String timezone,
                                   String purpose,
                                   float latitude,
                                   float longitude,
                                   String userID) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing user data in pref
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_CHARACTER_ID, characterID);
        editor.putString(KEY_PHONE_NUMBER, phone);
        editor.putString(KEY_TIMEZONE, timezone);
        editor.putString(KEY_TOKEN,token);
        editor.putString(KEY_LOCATION, location);
        editor.putString(KEY_CHARACTER_URL, characterURL);
        editor.putString(KEY_PASSWORD, password + "".trim());
        editor.putString(KEY_PURPOSE, purpose);
        editor.putString(KEY_LATITUDE, String.valueOf(latitude));
        editor.putString(KEY_LONGITUDE, String.valueOf(longitude));
        editor.putString(KEY_USER_ID, userID);

        // commit changes
        editor.commit();

    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, null));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, null));
        user.put(KEY_CHARACTER_ID, pref.getString(KEY_CHARACTER_ID, null));
        user.put(KEY_CHARACTER_URL, pref.getString(KEY_CHARACTER_URL, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_PHONE_NUMBER, pref.getString(KEY_PHONE_NUMBER, null));
        user.put(KEY_LATITUDE, pref.getString(KEY_LATITUDE,null));
        user.put(KEY_LONGITUDE, pref.getString(KEY_LONGITUDE,null));
        user.put(KEY_TIMEZONE, pref.getString(KEY_TIMEZONE, null));

        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin(Context context) {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void updateUserDetails(String firstName, String lastName, String phone, String email, String characterID, String KEY_CHARACTER_URL, String timezone, String location, String password) {
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_CHARACTER_ID, characterID);
        editor.putString(KEY_PHONE_NUMBER, phone);
        editor.putString(KEY_TIMEZONE, timezone);
        editor.putString(KEY_LOCATION, location);
        editor.putString(KEY_PASSWORD, password + "".trim());


        editor.commit();
    }

    public void refreshAuthenticatedUserToken(String refreshedToken) {
        editor.putString(SessionManager.KEY_TOKEN, refreshedToken);
        editor.commit();
    }

    public String getCharacterID() {
        return pref.getString(KEY_CHARACTER_ID,"");
    }
    public Integer getUserID() {
        return pref.getInt(KEY_USER_ID,0);
    }
    public String getFirstName() {
        return pref.getString(KEY_FIRST_NAME,"");
    }
    public String getLastName() {
        return pref.getString(KEY_LAST_NAME,"");
    }
    public String getEmail() {
        return pref.getString(KEY_EMAIL,"");
    }
    public String getKeyPhoneNumber() {
        return pref.getString(KEY_PHONE_NUMBER,"");
    }
    public String getPassword() {
        return pref.getString(KEY_PASSWORD,"");
    }
    public String getTimezone(){return pref.getString(KEY_TIMEZONE,"");}

    public String getLatitude() {
        return pref.getString(KEY_LATITUDE,null);
    }
    public String  getLongitude() {
        return pref.getString(KEY_LONGITUDE,null);
    }

    public String getPurpose() {
        return pref.getString(KEY_PURPOSE,"");
    }

}