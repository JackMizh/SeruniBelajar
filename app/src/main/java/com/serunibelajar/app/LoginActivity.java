package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class LoginActivity extends AppCompatActivity{

    EditText username,password;
    private RequestQueue rQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Tools.setSystemBarColor(this, R.color.colortop);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        TextView btn_daftar = findViewById(R.id.daftar_button);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterlevelingActivity.class));
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
        final String usernamee = username.getText().toString();
        final String pswd = password.getText().toString();
        if (usernamee.isEmpty()) {
            username.setError("Silahkan masukkan NISN/NIP/NIK");
            username.requestFocus();
            return;
        }
        if (pswd.isEmpty()) {
            password.setError("Silahkan masukkan Password");
            password.requestFocus();
            return;
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if (object.getBoolean("sucsses")){
                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        if (object.getString("previllage").equals("Guru"))
                        {
                            sessionManager.createSession(object.getString("email"), object.getString("nama"), object.getString("foto"), object.getString("previllage"));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(object.getString("previllage").equals("Orang Tua"))
                        {
                            sessionManager.createSession(object.getString("nik_wali"), object.getString("nama"), object.getString("foto"), object.getString("previllage"));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(object.getString("previllage").equals("Siswa"))
                        {
                            sessionManager.createSession(object.getString("email"), object.getString("nama"), object.getString("foto"), object.getString("previllage"));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        if (object.getString("error").equals("wrongemail")) {
                            Toast.makeText(getApplicationContext(), "NISN/NIP/NIK yang anda masukkan salah atau tidak terdaftar.", Toast.LENGTH_LONG).show();
                        } else if (object.getString("error").equals("wrongpassword")) {
                            Toast.makeText(getApplicationContext(), "Password yang anda masukkan salah, silahkan coba lagi.", Toast.LENGTH_LONG).show();
                        }
                        else{
                                Toast.makeText(getApplicationContext(),"Terjadi kesalahan saat login, silahkan coba lagi nanti.", Toast.LENGTH_LONG).show();
                        }
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
                parms.put("username",usernamee);
                parms.put("password",pswd);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}