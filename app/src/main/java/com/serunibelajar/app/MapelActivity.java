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

    private JSONArray resultjurusan, resultkelas;
    String[] hari = {
            "Pilih Hari",
            "Senin",
            "Selasa",
            "Rabu",
            "Kamis",
            "Jumat",
            "Sabtu",
            "Minggu"
    };

    String[] semester = {
            "Pilih Semest.",
            "Ganjil",
            "Genap"
    };
    private ArrayList<String> namajurusan, kodejurusan, namakelas, kodekelas;
    Spinner spinnerjurusan, spinnerkelas, spinnerhari, spinnersemester;
    private List<Mapel> mapelList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;
    SpinnerwhiteAdapter spinnerwhiteAdapter;


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
                if(spinnersemester.getSelectedItem().toString().equals("Pilih Semest."))
                {
                    Toast.makeText(MapelActivity.this, "Pilih Semester Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                getMapel(getIntent().getStringExtra("sekolah") ,spinnerjurusan.getSelectedItem().toString(), spinnerkelas.getSelectedItem().toString(), spinnerhari.getSelectedItem().toString(), tahun.getText().toString(), spinnersemester.getSelectedItem().toString());
            }
        });

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

        spinnersemester = findViewById(R.id.spinnersemester);
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(MapelActivity.this, R.layout.login_spinner, R.id.textView, semester);
        spinnersemester.setAdapter(adapter2);

        spinnerhari = findViewById(R.id.spinnerhari);
        final ArrayAdapter adapter = new ArrayAdapter<String>(MapelActivity.this, R.layout.login_spinner, R.id.textView, hari);
        spinnerhari.setAdapter(adapter);
        spinnerhari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getJurusan(getIntent().getStringExtra("sekolah"));
    }

    private void getMapel(String sekolah ,String jurusan, String kelas, String hari, String tahun, String semester) {
        mapelList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/mapel.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Mapel mapel = new Mapel();
                        mapel.setId_mapel(ob.getString("kode_mapel"));
                        mapel.setNama_mapel(ob.getString("nama"));
                        mapel.setKkm_mapel(ob.getString("kkm"));
                        mapel.setTahunakademik_mapel(ob.getString("tahun_akademik"));
                        mapel.setHari_mapel(ob.getString("hari"));
                        mapel.setJammulai_mapel(ob.getString("jam_mulai"));
                        mapel.setJamselesai_mapel(ob.getString("jam_selesai"));
                        mapel.setSekolah_mapel(ob.getString("npsn"));
                        mapel.setJurusan_mapel(ob.getString("kode_jurusan"));
                        mapel.setKelas_mapel(ob.getString("kode_kelas"));

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
                parms.put("sekolah", sekolah);
                parms.put("jurusan", jurusan);
                parms.put("kelas", firstTwo(kelas));
                parms.put("hari", hari);
                parms.put("tahun", tahun);
                parms.put("semester", semester);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
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
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(MapelActivity.this, namakelas, kodekelas);
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
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(MapelActivity.this, namajurusan, kodejurusan);
        spinnerjurusan.setAdapter(spinnerwhiteAdapter);
    }
}