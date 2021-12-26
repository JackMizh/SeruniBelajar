package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastregister);

        Tools.setSystemBarColor(this, R.color.colortop);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

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
            email.setError("Username or Email is required");
            email.requestFocus();
            return;
        }
        if (pswd.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(getIntent().getStringExtra("previllage").equals("Guru"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/registerguru.php",
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
                    params.put("nip_guru",  getIntent().getStringExtra("nip"));
                    params.put("npsn_guru", getIntent().getStringExtra("npsn"));
                    params.put("nuptk_guru", getIntent().getStringExtra("nuptk"));
                    params.put("namalengkap_guru", getIntent().getStringExtra("nama"));
                    params.put("tempatlahir_guru", getIntent().getStringExtra("tempatlahir"));
                    params.put("tanggallahir_guru", getIntent().getStringExtra("tanggallahir"));
                    params.put("jeniskelamin_guru", getIntent().getStringExtra("jeniskelamin"));
                    params.put("alamat_guru", getIntent().getStringExtra("alamat"));
                    params.put("agama_guru", getIntent().getStringExtra("agama"));
                    params.put("statuspegawai_guru", getIntent().getStringExtra("statuspegawai"));
                    params.put("jabatan_guru", getIntent().getStringExtra("jabatan"));
                    params.put("tugastambahan_guru", getIntent().getStringExtra("tugastambahan"));
                    params.put("nohp_guru", getIntent().getStringExtra("nohp"));
                    params.put("nosertifikasi_guru", getIntent().getStringExtra("nosertifikasi"));
                    params.put("golongan_guru", getIntent().getStringExtra("golongan"));
                    params.put("foto_guru", "");
                    params.put("status_guru", getIntent().getStringExtra("status"));
                    params.put("sekolahabdian_guru", getIntent().getStringExtra("sekolahabdian"));
                    params.put("email",emaill);
                    params.put("password",pswd);
                    params.put("status_registrasi", "no");
                    return params;
                }
            };
            rQueue = Volley.newRequestQueue(LastregisterActivity.this);
            rQueue.add(stringRequest);
        }
        else if(getIntent().getStringExtra("previllage").equals("Orang Tua"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/registerorangtua.php",
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
                    params.put("nik_orangtua",  getIntent().getStringExtra("nik"));
                    params.put("namalengkap_orangtua", getIntent().getStringExtra("nama"));
                    params.put("tempatlahir_orangtua", getIntent().getStringExtra("tempatlahir"));
                    params.put("tanggallahir_orangtua", getIntent().getStringExtra("tanggallahir"));
                    params.put("jeniskelamin_orangtua", getIntent().getStringExtra("jeniskelamin"));
                    params.put("alamat_orangtua", getIntent().getStringExtra("alamat"));
                    params.put("agama_orangtua", getIntent().getStringExtra("agama"));
                    params.put("nohp_orangtua", getIntent().getStringExtra("nohp"));
                    params.put("status_orangtua", getIntent().getStringExtra("status"));
                    params.put("foto_orangtua", "");
                    params.put("pekerjaan_orangtua", getIntent().getStringExtra("pekerjaan"));
                    params.put("email",emaill);
                    params.put("password",pswd);
                    params.put("status_registrasi", "no");
                    return params;
                }
            };
            rQueue = Volley.newRequestQueue(LastregisterActivity.this);
            rQueue.add(stringRequest);
        }
        else if(getIntent().getStringExtra("previllage").equals("Siswa"))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/registersiswa.php",
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
                    params.put("nisn_siswa",  getIntent().getStringExtra("nisn"));
                    params.put("npsn_siswa", getIntent().getStringExtra("npsn"));
                    params.put("nipd_siswa", getIntent().getStringExtra("nipd"));
                    params.put("nik_ayah", getIntent().getStringExtra("nikayah"));
                    params.put("nik_ibu", getIntent().getStringExtra("nikibu"));
                    params.put("nik_wali", getIntent().getStringExtra("nikwali"));
                    params.put("namalengkap_siswa", getIntent().getStringExtra("nama"));
                    params.put("jeniskelamin_siswa", getIntent().getStringExtra("jeniskelamin"));
                    params.put("alamat_siswa", getIntent().getStringExtra("alamat"));
                    params.put("agama_siswa", getIntent().getStringExtra("agama"));
                    params.put("nohp_siswa", getIntent().getStringExtra("nohp"));
                    params.put("foto_siswa", "");
                    params.put("status_siswa", getIntent().getStringExtra("status"));
                    params.put("tempatlahir_siswa", getIntent().getStringExtra("tempatlahir"));
                    params.put("tanggallahir_siswa", getIntent().getStringExtra("tanggallahir"));
                    params.put("asalsekolah_siswa", getIntent().getStringExtra("sekolah"));
                    params.put("email",emaill);
                    params.put("password",pswd);
                    params.put("status_registrasi", "no");
                    return params;
                }
            };
            rQueue = Volley.newRequestQueue(LastregisterActivity.this);
            rQueue.add(stringRequest);
        }
    }
}