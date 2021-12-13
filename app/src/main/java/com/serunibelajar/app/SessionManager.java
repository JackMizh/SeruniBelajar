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
    static final String NIP = "NIP";
    static final String PASSWORD = "PASSWORD";
    static final String NPSN = "NPSN";
    static final String NUPTK = "NUPTK";
    static final String NAMA = "NAMA";
    static final String ID_JK = "ID_JK";
    static final String TEMPAT_LAHIR = "TEMPAT_LAHIR";
    static final String TANGGAL_LAHIR = "TANGGAL_LAHIR";
    static final String KODE_KELAS = "KODE_KELAS";
    static final String STATUS_KEPEGAWAIAN = "STATUS_KEPEGAWAIAN";
    static final String ID_JENIS_PTK = "ID_JENIS_PTK";
    static final String ID_AGAMA = "ID_AGAMA";
    static final String ALAMAT = "ALAMAT";
    static final String NO_HP = "NO_HP";
    static final String EMAIL = "EMAIL";
    static final String TUGAS_TAMBAHAN = "TUGAS_TAMBAHAN";
    static final String GOLONGAN = "GOLONGAN";
    static final String NOMOR_SERTIFIKASI = "NOMOR_SERTIFIKASI";
    static final String STATUS = "STATUS";
    static final String STATUS_DAFTAR = "STATUS_DAFTAR";


    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String NIP, String PASSWORD, String NPSN, String NUPTK, String NAMA, String ID_JK, String TEMPAT_LAHIR, String TANGGAL_LAHIR, String KODE_KELAS, String STATUS_KEPEGAWAIAN, String ID_JENIS_PTK, String ID_AGAMA, String ALAMAT, String NO_HP, String EMAIL, String TUGAS_TAMBAHAN, String GOLONGAN, String NOMOR_SERTIFIKASI, String STATUS, String STATUS_DAFTAR){
        editor.putBoolean(LOGIN, true);
        editor.putString("NIP", NIP);
        editor.putString("PASSWORD", PASSWORD);
        editor.putString("NPSN", NPSN);
        editor.putString("NUPTK", NUPTK);
        editor.putString("NAMA", NAMA);
        editor.putString("ID_JK", ID_JK);
        editor.putString("TEMPAT_LAHIR", TEMPAT_LAHIR);
        editor.putString("TANGGAL_LAHIR", TANGGAL_LAHIR);
        editor.putString("KODE_KELAS", KODE_KELAS);
        editor.putString("STATUS_KEPEGAWAIAN", STATUS_KEPEGAWAIAN);
        editor.putString("ID_JENIS_PTK", ID_JENIS_PTK);
        editor.putString("ID_AGAMA", ID_AGAMA);
        editor.putString("ALAMAT", ALAMAT);
        editor.putString("NO_HP", NO_HP);
        editor.putString("EMAIL", EMAIL);
        editor.putString("TUGAS_TAMBAHAN", TUGAS_TAMBAHAN);
        editor.putString("GOLONGAN", GOLONGAN);
        editor.putString("NOMOR_SERTIFIKASI", NOMOR_SERTIFIKASI);
        editor.putString("STATUS", STATUS);
        editor.putString("STATUS_DAFTAR", STATUS_DAFTAR);

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
        user.put(NIP, sharedPreferences.getString(NIP, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(NPSN, sharedPreferences.getString(NPSN, null));
        user.put(NUPTK, sharedPreferences.getString(NUPTK, null));
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(ID_JK, sharedPreferences.getString(ID_JK, null));
        user.put(TEMPAT_LAHIR, sharedPreferences.getString(TEMPAT_LAHIR, null));
        user.put(TANGGAL_LAHIR, sharedPreferences.getString(TANGGAL_LAHIR, null));
        user.put(KODE_KELAS, sharedPreferences.getString(KODE_KELAS, null));
        user.put(STATUS_KEPEGAWAIAN, sharedPreferences.getString(STATUS_KEPEGAWAIAN, null));
        user.put(ID_JENIS_PTK, sharedPreferences.getString(ID_JENIS_PTK, null));
        user.put(ID_AGAMA, sharedPreferences.getString(ID_AGAMA, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(NO_HP, sharedPreferences.getString(NO_HP, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(TUGAS_TAMBAHAN, sharedPreferences.getString(TUGAS_TAMBAHAN, null));
        user.put(GOLONGAN, sharedPreferences.getString(GOLONGAN, null));
        user.put(NOMOR_SERTIFIKASI, sharedPreferences.getString(NOMOR_SERTIFIKASI, null));
        user.put(STATUS, sharedPreferences.getString(STATUS, null));
        user.put(STATUS_DAFTAR, sharedPreferences.getString(STATUS_DAFTAR, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
