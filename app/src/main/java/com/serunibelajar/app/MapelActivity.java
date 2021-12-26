package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapelActivity extends AppCompatActivity {

    private JSONArray resultjurusan, resultkelas, resulthari;
    private ArrayList<String> jurusan, kelas, hari;
    Spinner spinnerjurusan, spinnerkelas, spinnerhari ;
    private List<Mapel> mapelList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapel);

        Tools.setSystemBarColor(this, R.color.colortop);

        mList = findViewById(R.id.recyclerviewmapel);
        mapelList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MapelAdapter(this, mapelList);

        TextView tahun = findViewById(R.id.tahun);
        final int[] choosenYear = {2022};
        tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MapelActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        tahun.setText(Integer.toString(selectedYear));
                        choosenYear[0] = selectedYear;
                    }
                }, choosenYear[0], 0);
                builder.showYearOnly()
                        .setYearRange(1990, 2030)
                        .build()
                        .show();
            }
        });

        Button btncari = findViewById(R.id.btncari);
        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerjurusan.getSelectedItem().toString().equals("Pilih Jurusan"))
                {
                    Toast.makeText(MapelActivity.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas"))
                {
                    Toast.makeText(MapelActivity.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerhari.getSelectedItem().toString().equals("Pilih Hari"))
                {
                    Toast.makeText(MapelActivity.this, "Pilih Hari Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(tahun.getText().toString().equals("Pilih Tahun A."))
                {
                    Toast.makeText(MapelActivity.this, "Pilih Tahun Akademik Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                getMapel(spinnerjurusan.getSelectedItem().toString(), spinnerkelas.getSelectedItem().toString(), spinnerhari.getSelectedItem().toString(), tahun.getText().toString());
            }
        });

        jurusan = new ArrayList<String>();
        kelas = new ArrayList<String>();
        hari = new ArrayList<String>();
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

        spinnerhari = findViewById(R.id.spinnerhari);
        spinnerhari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getJurusan();
        getHari();
    }

    private void getMapel(String jurusan, String kelas, String hari, String tahun) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/mapel.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Mapel mapel = new Mapel();
                        mapel.setId_mapel(ob.getString("id_mapel"));
                        mapel.setNama_mapel(ob.getString("nama_mapel"));
                        mapel.setKkm_mapel(ob.getString("kkm_mapel"));
                        mapel.setTahunakademik_mapel(ob.getString("tahunakademik_mapel"));
                        mapel.setHari_mapel(ob.getString("hari_mapel"));
                        mapel.setJammulai_mapel(ob.getString("jammulai_mapel"));
                        mapel.setJamselesai_mapel(ob.getString("jamselesai_mapel"));
                        mapel.setSekolah_mapel(ob.getString("sekolah_mapel"));
                        mapel.setJurusan_mapel(ob.getString("jurusan_mapel"));
                        mapel.setKelas_mapel(ob.getString("kelas_mapel"));

                        mapelList.add(mapel);
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
                parms.put("jurusan", jurusan);
                parms.put("kelas", kelas);
                parms.put("hari", hari);
                parms.put("tahun", tahun);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getHari() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://plazatanaman.com/sipren/hari.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resulthari = j.getJSONArray("result");
                            gethari(resulthari);

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
                return params;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void gethari(JSONArray j) {
        hari.clear();
        hari.add("Pilih Hari");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                hari.add(json.getString("nama_hari"));;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerhari.setAdapter(new ArrayAdapter<String>(MapelActivity.this, R.layout.login_spinner, R.id.textView, hari));
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
                SessionManager sessionManager = new SessionManager(MapelActivity.this);
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
        spinnerkelas.setAdapter(new ArrayAdapter<String>(MapelActivity.this, R.layout.login_spinner, R.id.textView, kelas));
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
                SessionManager sessionManager = new SessionManager(MapelActivity.this);
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
        spinnerjurusan.setAdapter(new ArrayAdapter<String>(MapelActivity.this, R.layout.login_spinner, R.id.textView, jurusan));
    }
}