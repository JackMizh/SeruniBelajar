package com.serunibelajar.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private final Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    static final String NAMALENGKAP = "NAMALENGKAP";
    static final String USERNAME = "USERNAME";
    static final String EMAIL = "EMAIL";
    static final String NOHP = "NOHP";
    static final String JENISKELAMIN = "JENISKELAMIN";
    static final String TEMPAT_LAHIR = "TEMPAT_LAHIR";
    static final String TANGGAL_LAHIR = "TANGGAL_LAHIR";
    static final String ALAMAT = "ALAMAT";
    static final String AGAMA = "AGAMA";
    static final String PASSWORD = "PASSWORD";
    static final String PROFILE_PICTURE = "PROFILE_PICTURE";


    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String NAMALENGKAP, String USERNAME, String EMAIL, String NOHP, String JENISKELAMIN, String TEMPAT_LAHIR, String TANGGAL_LAHIR, String ALAMAT, String AGAMA, String PASSWORD, String PROFILE_PICTURE){
        editor.putBoolean(LOGIN, true);
        editor.putString("NAMALENGKAP", NAMALENGKAP);
        editor.putString("USERNAME", USERNAME);
        editor.putString("EMAIL", EMAIL);
        editor.putString("NOHP", NOHP);
        editor.putString("JENISKELAMIN", JENISKELAMIN);
        editor.putString("TEMPAT_LAHIR", TEMPAT_LAHIR);
        editor.putString("TANGGAL_LAHIR", TANGGAL_LAHIR);
        editor.putString("ALAMAT", ALAMAT);
        editor.putString("AGAMA", AGAMA);
        editor.putString("PASSWORD", PASSWORD);
        editor.putString("PROFILE_PICTURE", PROFILE_PICTURE);

        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(final Context context){
        if (this.isLoggin()) {
            Intent i = new Intent (context, MainActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        } else {
            Intent i = new Intent (context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAMALENGKAP, sharedPreferences.getString(NAMALENGKAP, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(NOHP, sharedPreferences.getString(NOHP, null));
        user.put(JENISKELAMIN, sharedPreferences.getString(JENISKELAMIN, null));
        user.put(TEMPAT_LAHIR, sharedPreferences.getString(TEMPAT_LAHIR, null));
        user.put(TANGGAL_LAHIR, sharedPreferences.getString(TANGGAL_LAHIR, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(AGAMA, sharedPreferences.getString(AGAMA, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(PROFILE_PICTURE, sharedPreferences.getString(PROFILE_PICTURE, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
