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

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://serunibelajar.co.id/absensi/profileorangtua.php",
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
                        parms.put("nik", sessionManager.getUserDetail().get("EMAIL"));
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
                TextView txtalamat = findViewById(R.id.txtalamat);
                TextView txtnohp = findViewById(R.id.txtnohp);
                TextView txtstatus = findViewById(R.id.txtstatus);
                CircleImageView profileimage = findViewById(R.id.profile_image);
                TextView txtpekerjaan = findViewById(R.id.txtpekerjaan);

                txtnik.setText(json.getString("nik_wali"));
                txtnamalengkap.setText(json.getString("nama"));
                txtttl.setText(json.getString("tempat_lahir") + ", " + json.getString("tanggal_lahir"));
                txtjk.setText(json.getString("nama_jk"));
                txtalamat.setText(json.getString("alamat"));
                txtnohp.setText(json.getString("no_hp"));
                txtstatus.setText(json.getString("status_keluarga"));
                if(json.getString("foto").equals(""))
                {
                    profileimage.setImageResource(R.drawable.logoputih);
                }else {
                    Glide.with(ProfileorangtuaActivity.this).load(json.getString("foto")).into(profileimage);
                }
                txtpekerjaan.setText(json.getString("pekerjaan"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}