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

        Tools.setSystemBarColor(this, R.color.colorPrimary);

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


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/register.php",
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
                params.put("nama", getIntent().getStringExtra("nama"));
                params.put("nip", getIntent().getStringExtra("nip"));
                params.put("nuptk", getIntent().getStringExtra("nuptk"));
                params.put("alamat", getIntent().getStringExtra("alamat"));
                params.put("no_hp", getIntent().getStringExtra("no_hp"));
                params.put("email",emaill);
                params.put("password",pswd);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(LastregisterActivity.this);
        rQueue.add(stringRequest);
    }
}