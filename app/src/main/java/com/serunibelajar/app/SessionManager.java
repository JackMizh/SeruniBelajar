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
    static final String EMAIL = "EMAIL";
    static final String NAMALENGKAP = "NAMALENGKAP";
    static final String FOTO = "FOTO";
    static final String PREVILLAGE = "PREVILLAGE";


    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String EMAIL, String NAMALENGKAP, String FOTO, String PREVILLAGE){
        editor.putBoolean(LOGIN, true);
        editor.putString("EMAIL", EMAIL);
        editor.putString("NAMALENGKAP", NAMALENGKAP);
        editor.putString("FOTO", FOTO);
        editor.putString("PREVILLAGE", PREVILLAGE);

        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(final Context context){
        if (this.isLoggin()) {
        } else {
            Intent i = new Intent (context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(NAMALENGKAP, sharedPreferences.getString(NAMALENGKAP, null));
        user.put(FOTO, sharedPreferences.getString(FOTO, null));
        user.put(PREVILLAGE, sharedPreferences.getString(PREVILLAGE, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
