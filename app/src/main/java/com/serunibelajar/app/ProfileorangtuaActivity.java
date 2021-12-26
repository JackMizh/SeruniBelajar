package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileorangtuaActivity extends AppCompatActivity {

    private JSONArray resultprofileorangtua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileorangtua);

        Tools.setSystemBarColor(this, R.color.colortop);

        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/profileorangtua.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultprofileorangtua = j.getJSONArray("result");
                            getProfileorangtua(resultprofileorangtua);

                        } catch (JSONException e) {
                            Toast.makeText(ProfileorangtuaActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileorangtuaActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String>parms=new HashMap<String, String>();
                        SessionManager sessionManager = new SessionManager(ProfileorangtuaActivity.this);
                        parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                        return parms;
                    }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    private void getProfileorangtua(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                TextView txtnik = findViewById(R.id.txtnik);
                TextView txtnamalengkap = findViewById(R.id.txtnamalengkap);
                TextView txtttl = findViewById(R.id.txtttl);
                TextView txtjk = findViewById(R.id.txtjk);
                TextView txtagama = findViewById(R.id.txtagama);
                TextView txtalamat = findViewById(R.id.txtalamat);
                TextView txtnohp = findViewById(R.id.txtnohp);
                TextView txtstatus = findViewById(R.id.txtstatus);
                CircleImageView profileimage = findViewById(R.id.profile_image);
                TextView txtpekerjaan = findViewById(R.id.txtpekerjaan);
                TextView txtemail = findViewById(R.id.txtemail);

                txtnik.setText(json.getString("nik_orangtua"));
                txtnamalengkap.setText(json.getString("namalengkap_orangtua"));
                txtttl.setText(json.getString("tempatlahir_orangtua") + ", " + json.getString("tanggallahir_orangtua"));
                txtjk.setText(json.getString("jeniskelamin_orangtua"));
                txtagama.setText(json.getString("agama_orangtua"));
                txtalamat.setText(json.getString("alamat_orangtua"));
                txtnohp.setText(json.getString("nohp_orangtua"));
                txtstatus.setText(json.getString("status_orangtua"));
                if(json.getString("foto_orangtua").equals(""))
                {
                    profileimage.setImageResource(R.drawable.logoputih);
                }else {
                    Glide.with(ProfileorangtuaActivity.this).load(json.getString("foto_orangtua")).into(profileimage);
                }
                txtpekerjaan.setText(json.getString("pekerjaan_orangtua"));
                txtemail.setText(json.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}