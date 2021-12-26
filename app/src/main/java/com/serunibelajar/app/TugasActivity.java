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

public class TugasActivity extends AppCompatActivity {

    private JSONArray resultjurusan, resultkelas;
    private ArrayList<String> jurusan, kelas;
    Spinner spinnerjurusan, spinnerkelas;
    private List<Tugas> tugasList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas);

        Tools.setSystemBarColor(this, R.color.colortop);

        LinearLayout tambah = findViewById(R.id.btntambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(TugasActivity.this, Tambahtugas_Activity.class);
                in.putExtra("sekolah", getIntent().getStringExtra("sekolah"));
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

        mList = findViewById(R.id.recyclerviewtugas);
        tugasList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TugasAdapter(this, tugasList);

        jurusan = new ArrayList<String>();
        kelas = new ArrayList<String>();
        spinnerjurusan = findViewById(R.id.spinnerjurusan);
        spinnerjurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getKelas(i);
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
                    Toast.makeText(TugasActivity.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas"))
                {
                    Toast.makeText(TugasActivity.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                getSearch(spinnerjurusan.getSelectedItem().toString(), spinnerkelas.getSelectedItem().toString());
            }
        });

        getJurusan();
        getAll();
    }

    private void getSearch(String jurusan, String kelas) {
        tugasList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/tugascari.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Tugas tugas = new Tugas();
                        tugas.setId_tugas(ob.getString("id_tugas"));
                        tugas.setSekolah_tugas(ob.getString("sekolah_tugas"));
                        tugas.setJurusan_tugas(ob.getString("jurusan_tugas"));
                        tugas.setKelas_tugas(ob.getString("kelas_tugas"));
                        tugas.setMapel_tugas(ob.getString("mapel_tugas"));
                        tugas.setJudul_tugas(ob.getString("judul_tugas"));
                        tugas.setTanggal_tugas(ob.getString("tanggal_tugas"));
                        tugas.setFile_tugas(ob.getString("file_tugas"));
                        tugas.setYoutube_tugas(ob.getString("youtube_tugas"));
                        tugas.setGuru_tugas(ob.getString("guru_tugas"));

                        tugasList.add(tugas);
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
        tugasList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/tugas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Tugas tugas = new Tugas();
                        tugas.setId_tugas(ob.getString("id_tugas"));
                        tugas.setSekolah_tugas(ob.getString("sekolah_tugas"));
                        tugas.setJurusan_tugas(ob.getString("jurusan_tugas"));
                        tugas.setKelas_tugas(ob.getString("kelas_tugas"));
                        tugas.setMapel_tugas(ob.getString("mapel_tugas"));
                        tugas.setJudul_tugas(ob.getString("judul_tugas"));
                        tugas.setTanggal_tugas(ob.getString("tanggal_tugas"));
                        tugas.setFile_tugas(ob.getString("file_tugas"));
                        tugas.setYoutube_tugas(ob.getString("youtube_tugas"));
                        tugas.setGuru_tugas(ob.getString("guru_tugas"));

                        tugasList.add(tugas);
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

    private void getKelas(int itemAtPosition) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/kelas.php",
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(TugasActivity.this);
                params.put("sekolah",  getIntent().getStringExtra("sekolah"));
                params.put("jurusan", String.valueOf(itemAtPosition));
                return params;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getkelas(JSONArray j) {
        kelas.clear();
        kelas.add("Pilih Kelas");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                kelas.add(json.getString("nama_kelas"));;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerkelas.setAdapter(new ArrayAdapter<String>(TugasActivity.this, R.layout.login_spinner, R.id.textView, kelas));
    }

    private void getJurusan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/jurusan.php",
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(TugasActivity.this);
                params.put("sekolah",  getIntent().getStringExtra("sekolah"));
                return params;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getjurusan(JSONArray j) {
        jurusan.clear();
        jurusan.add("Pilih Jurusan");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                jurusan.add(json.getString("nama_jurusan"));;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerjurusan.setAdapter(new ArrayAdapter<String>(TugasActivity.this, R.layout.login_spinner, R.id.textView, jurusan));
    }
}