package de.j4velin.pedometer.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_PASS = "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.apply();
    }

    public static void setUserPass(Context ctx, String passWord){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASS, passWord);
        editor.apply();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getUserPass(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_PASS, "");
    }

    public static void logOutUser(Context ctx) {
        getSharedPreferences(ctx).edit().remove(PREF_USER_NAME).apply();
        getSharedPreferences(ctx).edit().remove(PREF_USER_PASS).apply();
    }
}
