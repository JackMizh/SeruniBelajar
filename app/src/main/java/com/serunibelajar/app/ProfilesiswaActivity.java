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

public class ProfilesiswaActivity extends AppCompatActivity {

    private JSONArray resultprofilesiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilesiswa);

        Tools.setSystemBarColor(this, R.color.colortop);

        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://serunibelajar.co.id/absensi/profilesiswa.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultprofilesiswa = j.getJSONArray("result");
                            getProfilesiswa(resultprofilesiswa);

                        } catch (JSONException e) {
                            Toast.makeText(ProfilesiswaActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfilesiswaActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(ProfilesiswaActivity.this);
                parms.put("email", sessionManager.getUserDetail().get("EMAIL"));
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getProfilesiswa(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                TextView txtnisn = findViewById(R.id.txtnisn);
                TextView txtnipd = findViewById(R.id.txtnipd);
                TextView txtnikwali = findViewById(R.id.txtnikwali);
                TextView txtnamalengkap = findViewById(R.id.txtnamalengkap);
                TextView txtjk = findViewById(R.id.txtjk);
                TextView txtagama = findViewById(R.id.txtagama);
                TextView txtnohp = findViewById(R.id.txtnohp);
                TextView txtttl = findViewById(R.id.txtttl);
                TextView txtasalsekolah = findViewById(R.id.txtasalsekolah);
                TextView txtjurusan = findViewById(R.id.txtjurusan);
                TextView txtkelas = findViewById(R.id.txtkelas);
                CircleImageView profileimage = findViewById(R.id.profile_image);
                TextView txtemail = findViewById(R.id.txtemail);
                TextView txtalamat = findViewById(R.id.txtalamat);

                txtnisn.setText(json.getString("nisn"));
                txtnipd.setText(json.getString("nipd"));
                txtnikwali.setText(json.getString("nik_wali"));
                txtnamalengkap.setText(json.getString("nama"));
                txtjk.setText(json.getString("nama_jk"));
                txtagama.setText(json.getString("nama_agama"));
                txtalamat.setText(json.getString("alamat"));
                txtnohp.setText(json.getString("no_hp"));
                txtttl.setText(json.getString("tempat_lahir") + ", " + json.getString("tanggal_lahir"));
                txtasalsekolah.setText(json.getString("nama_sekolah"));
                txtjurusan.setText(json.getString("nama_jurusan"));
                txtkelas.setText(json.getString("nama_kelas"));
                if(json.getString("foto").equals(""))
                {
                    profileimage.setImageResource(R.drawable.logoputih);
                }else {
                    Glide.with(ProfilesiswaActivity.this).load(json.getString("foto")).into(profileimage);
                }
                txtemail.setText(json.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}