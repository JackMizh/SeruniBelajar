package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Tools.setSystemBarColor(this, R.color.colorPrimary);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);

        TextView btn_daftar = findViewById(R.id.daftar_button);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
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

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if (!object.getBoolean("sucsses")){
                        if(object.getString("status_daftar").equals("Registration")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Pemberitahuan Akun");
                            alertDialog.setMessage("Akun anda belum disetujui oleh admin, silahkan tunggu beberapa saat lagi.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        else {
                            SessionManager sessionManager = new SessionManager(LoginActivity.this);
                            sessionManager.createSession(object.getString("nip"), object.getString("password"), object.getString("npsn"), object.getString("nuptk"), object.getString("nama"), object.getString("id_jk"), object.getString("tempat_lahir"), object.getString("tanggal_lahir"), object.getString("kode_kelas"), object.getString("status_kepegawaian"), object.getString("id_jenis_ptk"), object.getString("id_agama"), object.getString("alamat"), object.getString("no_hp"), object.getString("email"), object.getString("tugas_tambahan"), object.getString("golongan"), object.getString("nomor_sertifikasi"), object.getString("status"), object.getString("status_daftar"));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"User Login UnSucssesFull", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("email",emaill);
                parms.put("password",pswd);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}