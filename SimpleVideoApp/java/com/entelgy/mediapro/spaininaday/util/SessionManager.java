package com.entelgy.mediapro.spaininaday.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.entelgy.mediapro.spaininaday.rest.UserRequest;
import com.entelgy.mediapro.spaininaday.rest.UserResponse;

public class SessionManager {
    private static final String PREFERENCES_NAME = "MediaproPreferences";
    private static final String USERID = "userId";
    private static final String ENCRYPTED_PASSWORD = "encryptedPassword";
    private static final String LOGGED = "isLogged";
    private static final String FULLNAME = "fullname";

    private static SessionManager ourInstance;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    private SessionManager(Context context) {
        super();
        this.preferences = context.getSharedPreferences(PREFERENCES_NAME, 0); // 0 - for private mode
        this.context = context;
        this.editor = this.preferences.edit();
        this.editor.clear();
    }

    private SessionManager() {}

    public static synchronized SessionManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SessionManager(context);
        }
        return ourInstance;
    }

    public void saveUserSession(UserResponse user) {
        if (user != null) {
            editor.putBoolean(LOGGED, true);
            editor.putString(USERID, user.get_id());
            editor.commit();
        }
    }

    public void saveFullName(UserRequest user) {
        if (user != null) {
            editor.putString(FULLNAME, user.getFirstName() + " " + user.getLastName());
            editor.commit();
        }
    }

    public void saveEncryptedPassword(String value) {
        editor.putString(ENCRYPTED_PASSWORD, value);
        editor.commit();
    }

    public boolean isUserLogged() {
        return preferences.getBoolean(LOGGED, false);
    }

    public String getLoggedUserId() {
        return preferences.getString(USERID, "0");
    }

    public String getEncryptedPassword() {
        return preferences.getString(ENCRYPTED_PASSWORD, "");
    }

    public void deleteUserSession() {
        editor.clear();
        editor.commit();
    }

}
