package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElearningActivity extends AppCompatActivity {

    private JSONArray resultjurusan, resultkelas;
    private ArrayList<String> namajurusan, kodejurusan, namakelas, kodekelas;
    Spinner spinnerjurusan, spinnerkelas;
    private List<Elearning> elearningList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;
    SpinnerwhiteAdapter spinnerwhiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elearning);

        Tools.setSystemBarColor(this, R.color.colortop);

        LinearLayout tambah = findViewById(R.id.btntambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ElearningActivity.this, TambahElearning.class);
                in.putExtra("sekolah", getIntent().getStringExtra("sekolah"));
                in.putExtra("nip", getIntent().getStringExtra("nip"));
                startActivity(in);
            }
        });

        SessionManager sessionManager = new SessionManager(this);
        if(!sessionManager.getUserDetail().get("PREVILLAGE").equals("Guru"))
        {
            tambah.setVisibility(View.GONE);
        }
        else {
            tambah.setVisibility(View.VISIBLE);
        }

        mList = findViewById(R.id.recyclerviewelearning);
        elearningList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ElearningAdapter(this, elearningList);

        namajurusan = new ArrayList<String>();
        kodejurusan = new ArrayList<String>();
        namakelas = new ArrayList<String>();
        kodekelas = new ArrayList<String>();
        spinnerjurusan = findViewById(R.id.spinnerjurusan);
        spinnerjurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getKelas(adapterView.getItemAtPosition(i).toString(), getIntent().getStringExtra("sekolah"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerkelas = findViewById(R.id.spinnerkelas);

        Button btnall = findViewById(R.id.btnall);
        btnall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAll();
            }
        });

        Button btncari = findViewById(R.id.btncari);
        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerjurusan.getSelectedItem().toString().equals("Pilih Jurusan"))
                {
                    Toast.makeText(ElearningActivity.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas"))
                {
                    Toast.makeText(ElearningActivity.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                getSearch(spinnerjurusan.getSelectedItem().toString(), spinnerkelas.getSelectedItem().toString());
            }
        });

        getJurusan(getIntent().getStringExtra("sekolah"));
        getAll();
    }

    private void getSearch(String jurusan, String kelas) {
        elearningList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/elearningcari.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Elearning elearning = new Elearning();
                        elearning.setId_elearning(ob.getString("kode_mapel"));
                        elearning.setSekolah_elearning(ob.getString("npsn"));
                        elearning.setJurusan_elearning(ob.getString("kode_jurusan"));
                        elearning.setKelas_elearning(ob.getString("kode_kelas"));
                        elearning.setMapel_elearning(ob.getString("nama_mapel"));
                        elearning.setJudul_elearning(ob.getString("judul"));
                        elearning.setTanggal_elearning(ob.getString("tgl_upload"));
                        elearning.setFile_elearning(ob.getString("bahan"));
                        elearning.setYoutube_elearning(ob.getString("video"));
                        elearning.setGuru_elearning(ob.getString("nama"));
                        elearning.setPertemuan_elearning(ob.getString("pertemuan"));

                        elearningList.add(elearning);
                    }

                    mList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parms = new HashMap<String, String>();
                parms.put("sekolah", getIntent().getStringExtra("sekolah"));
                parms.put("jurusan", jurusan);
                parms.put("kelas", kelas);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getAll() {
        elearningList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/elearning.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Elearning elearning = new Elearning();
                        elearning.setId_elearning(ob.getString("kode_mapel"));
                        elearning.setSekolah_elearning(ob.getString("npsn"));
                        elearning.setJurusan_elearning(ob.getString("kode_jurusan"));
                        elearning.setKelas_elearning(ob.getString("kode_kelas"));
                        elearning.setMapel_elearning(ob.getString("nama_mapel"));
                        elearning.setJudul_elearning(ob.getString("judul"));
                        elearning.setTanggal_elearning(ob.getString("tgl_upload"));
                        elearning.setFile_elearning(ob.getString("bahan"));
                        elearning.setYoutube_elearning(ob.getString("video"));
                        elearning.setGuru_elearning(ob.getString("nama"));
                        elearning.setPertemuan_elearning(ob.getString("pertemuan"));

                        elearningList.add(elearning);
                    }

                    mList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parms = new HashMap<String, String>();
                parms.put("sekolah", getIntent().getStringExtra("sekolah"));
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getKelas(String kode_jurusan, String npsn) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://serunibelajar.co.id/absensi/kelas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultkelas = j.getJSONArray("result");
                            getkelas(resultkelas);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("kode_jurusan", kode_jurusan);
                parms.put("npsn",npsn);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getkelas(JSONArray j) {
        kodekelas.clear();
        namakelas.clear();
        namakelas.add("Pilih Kelas");
        kodekelas.add("0000");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namakelas.add(json.getString("nama_kelas"));
                kodekelas.add(json.getString("kode_kelas"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(ElearningActivity.this, namakelas, kodekelas);
        spinnerkelas.setAdapter(spinnerwhiteAdapter);
    }

    private void getJurusan(String npsn) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://serunibelajar.co.id/absensi/jurusan.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultjurusan = j.getJSONArray("result");
                            getjurusan(resultjurusan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("npsn",npsn);
                return parms;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getjurusan(JSONArray j) {
        kodejurusan.clear();
        namajurusan.clear();
        namajurusan.add("Pilih Jurusan");
        kodejurusan.add("0000");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namajurusan.add(json.getString("nama_jurusan"));
                kodejurusan.add(json.getString("kode_jurusan"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(ElearningActivity.this, namajurusan, kodejurusan);
        spinnerjurusan.setAdapter(spinnerwhiteAdapter);
    }
}