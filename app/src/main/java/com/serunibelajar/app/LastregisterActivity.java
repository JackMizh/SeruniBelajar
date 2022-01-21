package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LastregisterActivity extends AppCompatActivity {

    EditText email,password;
    private RequestQueue rQueue;
    LinearLayout usernamelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastregister);

        Tools.setSystemBarColor(this, R.color.colortop);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        usernamelayout = findViewById(R.id.usernamelayout);

        if(getIntent().getStringExtra("previllage").equals("Orang Tua"))
        {
            email.setEnabled(false);
            usernamelayout.setVisibility(View.INVISIBLE);
        }
        else {
            email.setEnabled(true);
            usernamelayout.setVisibility(View.VISIBLE);
        }

        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button daftar = findViewById(R.id.btn_daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }
        });
    }

    private void daftar() {
        final String emaill = email.getText().toString();
        final String pswd = password.getText().toString();
        if (emaill.isEmpty()) {
            if(getIntent().getStringExtra("previllage").equals("Orang Tua")) {

            }
            else {
                email.setError("Username or Email is required");
                email.requestFocus();
                return;
            }
        }
        if (pswd.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(getIntent().getStringExtra("previllage").equals("Guru"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/registerguru.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optString("success").equals("1")) {
                                    Toast.makeText(LastregisterActivity.this, "Registered Successfully! Now Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LastregisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LastregisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nip",  getIntent().getStringExtra("nip"));
                    params.put("password",pswd);
                    params.put("npsn", getIntent().getStringExtra("npsn"));
                    params.put("nuptk", getIntent().getStringExtra("nuptk"));
                    params.put("nama", getIntent().getStringExtra("nama"));
                    params.put("id_jk", getIntent().getStringExtra("id_jk"));
                    params.put("tempat_lahir", getIntent().getStringExtra("tempat_lahir"));
                    params.put("tanggal_lahir", getIntent().getStringExtra("tanggal_lahir"));
                    params.put("status_kepegawaian", getIntent().getStringExtra("status_kepegawaian"));
                    params.put("id_agama", getIntent().getStringExtra("id_agama"));
                    params.put("alamat", getIntent().getStringExtra("alamat"));
                    params.put("no_hp", getIntent().getStringExtra("no_hp"));
                    params.put("email",emaill);
                    params.put("id_tgs_tambahan", getIntent().getStringExtra("id_tgs_tambahan"));
                    params.put("golongan", getIntent().getStringExtra("golongan"));
                    params.put("foto", "");
                    params.put("nomor_sertifikasi", getIntent().getStringExtra("nomor_sertifikasi"));
                    params.put("status", "Aktif");
                    return params;
                }
            };
            rQueue = Volley.newRequestQueue(LastregisterActivity.this);
            rQueue.add(stringRequest);
        }
        else if(getIntent().getStringExtra("previllage").equals("Orang Tua"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/registerorangtua.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optString("success").equals("1")) {
                                    Toast.makeText(LastregisterActivity.this, "Registered Successfully! Now Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LastregisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LastregisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nik_wali",  getIntent().getStringExtra("nik_wali"));
                    params.put("password",pswd);
                    params.put("nama", getIntent().getStringExtra("nama"));
                    params.put("alamat", getIntent().getStringExtra("alamat"));
                    params.put("tempat_lahir", getIntent().getStringExtra("tempat_lahir"));
                    params.put("tanggal_lahir", getIntent().getStringExtra("tanggal_lahir"));
                    params.put("id_jk", getIntent().getStringExtra("id_jk"));
                    params.put("no_hp", getIntent().getStringExtra("no_hp"));
                    params.put("foto", "");
                    params.put("status_keluarga", getIntent().getStringExtra("status_keluarga"));
                    params.put("pekerjaan", getIntent().getStringExtra("pekerjaan"));
                    params.put("status", getIntent().getStringExtra("status"));
                    return params;
                }
            };
            rQueue = Volley.newRequestQueue(LastregisterActivity.this);
            rQueue.add(stringRequest);
        }
        else if(getIntent().getStringExtra("previllage").equals("Siswa"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/registersiswa.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optString("success").equals("1")) {
                                    Toast.makeText(LastregisterActivity.this, "Registered Successfully! Now Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LastregisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LastregisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("nisn",  getIntent().getStringExtra("nisn"));
                    params.put("nipd", getIntent().getStringExtra("nipd"));
                    params.put("nama", getIntent().getStringExtra("nama"));
                    params.put("npsn", getIntent().getStringExtra("npsn"));
                    params.put("kode_jurusan", getIntent().getStringExtra("jurusan"));
                    params.put("kode_kelas", getIntent().getStringExtra("kelas"));
                    params.put("tempat_lahir", getIntent().getStringExtra("tempatlahir"));
                    params.put("tanggal_lahir", getIntent().getStringExtra("tanggallahir"));
                    params.put("id_jk", getIntent().getStringExtra("jeniskelamin"));
                    params.put("id_agama", getIntent().getStringExtra("agama"));
                    params.put("alamat", getIntent().getStringExtra("alamat"));
                    params.put("no_hp", getIntent().getStringExtra("nohp"));
                    params.put("foto", "");
                    params.put("status", getIntent().getStringExtra("status"));
                    params.put("nik_wali", getIntent().getStringExtra("nikwali"));
                    params.put("email",emaill);
                    params.put("password",pswd);
                    return params;
                }
            };
            rQueue = Volley.newRequestQueue(LastregisterActivity.this);
            rQueue.add(stringRequest);
        }
    }
}