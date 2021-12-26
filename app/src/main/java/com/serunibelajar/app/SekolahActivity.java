package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SekolahActivity extends AppCompatActivity {

    private JSONArray resultsekolah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sekolah);

        Tools.setSystemBarColor(this, R.color.colorPrimary);

            StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/sekolah.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                resultsekolah = j.getJSONArray("result");
                                getSekolah(resultsekolah);

                            } catch (JSONException e) {
                                Toast.makeText(SekolahActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SekolahActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams()  {
                    Map<String,String>parms=new HashMap<String, String>();
                    parms.put("sekolah", getIntent().getStringExtra("sekolah"));
                    return parms;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getSekolah(JSONArray j) {
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                TextView txtnama = findViewById(R.id.nama);
                TextView txtregion = findViewById(R.id.region);
                TextView txtnss = findViewById(R.id.nss);
                TextView txtnpsn = findViewById(R.id.npsn);
                TextView txtnpwp = findViewById(R.id.npwp);
                TextView txtkodesekolah = findViewById(R.id.kode);
                TextView txtkepsek = findViewById(R.id.kepsek);
                TextView txttahun = findViewById(R.id.tahun);
                TextView txtstatus = findViewById(R.id.status);
                TextView txtalamat = findViewById(R.id.alamat);
                TextView txtnohp = findViewById(R.id.nohp);
                TextView txtemail = findViewById(R.id.email);
                TextView txtwebsite = findViewById(R.id.website);

                txtnama.setText(json.getString("nama_sekolah") + " (" + json.getString("status_sekolah") + ")");
                txtregion.setText(json.getString("kelurahan_sekolah") + ", " + json.getString("kecamatan_sekolah") + ", " + json.getString("kabkota_sekolah") + ", " + json.getString("provinsi_sekolah") + ". " + json.getString("kodepos_sekolah"));
                txtnss.setText(":  "+json.getString("nss_sekolah"));
                txtnpsn.setText(":  "+json.getString("npsn_sekolah"));
                txtnpwp.setText(":  "+json.getString("npwp_sekolah"));
                txtkodesekolah.setText(":  "+json.getString("kode_sekolah"));
                txtkepsek.setText(":  "+json.getString("namakepsek_sekolah"));
                txttahun.setText(":  "+json.getString("tahunberdiri_sekolah"));
                txtstatus.setText(":  "+json.getString("status"));
                txtalamat.setText(json.getString("alamat_sekolah"));
                txtnohp.setText(json.getString("nohp_sekolah"));
                txtemail.setText(json.getString("email_sekolah"));
                txtwebsite.setText(json.getString("website_sekolah"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}