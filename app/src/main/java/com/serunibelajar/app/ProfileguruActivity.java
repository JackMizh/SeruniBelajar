package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileguruActivity extends AppCompatActivity {

    private JSONArray resultprofileguru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileguru);

        Tools.setSystemBarColor(this, R.color.colortop);

        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/profileguru.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultprofileguru = j.getJSONArray("result");
                            getProfileguru(resultprofileguru);

                        } catch (JSONException e) {
                            Toast.makeText(ProfileguruActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileguruActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(ProfileguruActivity.this);
                parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getProfileguru(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                TextView txtnip = findViewById(R.id.txtnip);
                TextView txtnpsn = findViewById(R.id.txtnpsn);
                TextView txtnuptk = findViewById(R.id.txtnuptk);
                TextView txtnamalengkap = findViewById(R.id.txtnamalengkap);
                TextView txtttl = findViewById(R.id.txtttl);
                TextView txtjk = findViewById(R.id.txtjk);
                TextView txtagama = findViewById(R.id.txtagama);
                TextView txtalamat = findViewById(R.id.txtalamat);
                TextView txtstatuskepegawaian = findViewById(R.id.txtstatuskepegawaian);
                TextView txtjabatan = findViewById(R.id.txtjabatan);
                TextView txttugastambahan = findViewById(R.id.txttugastambahan);
                TextView txtnohp = findViewById(R.id.txtnohp);
                TextView txtnosertifikasi = findViewById(R.id.txtnosertifikasi);
                TextView txtgolongan = findViewById(R.id.txtgolongan);
                TextView txtstatus = findViewById(R.id.txtstatus);
                CircleImageView profileimage = findViewById(R.id.profile_image);
                TextView txtsekolahabdian = findViewById(R.id.txtsekolahabdian);
                TextView txtemail = findViewById(R.id.txtemail);

                txtnip.setText(json.getString("nip_guru"));
                txtnpsn.setText(json.getString("npsn_guru"));
                txtnuptk.setText(json.getString("nuptk_guru"));
                txtnamalengkap.setText(json.getString("namalengkap_guru"));
                txtttl.setText(json.getString("tempatlahir_guru") + ", " + json.getString("tanggallahir_guru"));
                txtjk.setText(json.getString("jeniskelamin_guru"));
                txtagama.setText(json.getString("agama_guru"));
                txtalamat.setText(json.getString("alamat_guru"));
                txtstatuskepegawaian.setText(json.getString("statuspegawai_guru"));
                txtjabatan.setText(json.getString("jabatan_guru"));
                txttugastambahan.setText(json.getString("tugastambahan_guru"));
                txtnohp.setText(json.getString("nohp_guru"));
                txtnosertifikasi.setText(json.getString("nosertifikasi_guru"));
                txtgolongan.setText(json.getString("golongan_guru"));
                txtstatus.setText(json.getString("status_guru"));
                if(json.getString("foto_guru").equals(""))
                {
                    profileimage.setImageResource(R.drawable.logoputih);
                }else {
                    Glide.with(ProfileguruActivity.this).load(json.getString("foto_guru")).into(profileimage);
                }
                txtsekolahabdian.setText(json.getString("sekolahabdian_guru"));
                txtemail.setText(json.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}