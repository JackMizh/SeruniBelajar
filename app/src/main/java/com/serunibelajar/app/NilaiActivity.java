package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
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

public class NilaiActivity extends AppCompatActivity{

    private List<Nilai> nilaiList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;
    private RadioGroup list_opsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        Tools.setSystemBarColor(this, R.color.colortop);

        list_opsi = findViewById(R.id.choose);
        list_opsi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.uts:
                        getNilaiuts(getIntent().getStringExtra("nisn"));
                        break;
                    case R.id.uas:
                        getNilaiuas(getIntent().getStringExtra("nisn"));
                        break;
                }
            }
        });

        mList = findViewById(R.id.recyclerviewnilai);
        nilaiList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NilaiAdapter(this, nilaiList);

        getNilaiuts(getIntent().getStringExtra("nisn"));

    }

    private void getNilaiuts(String nisn) {
        nilaiList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/nilaiuts.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Nilai nilai = new Nilai();
                        nilai.setNisn(ob.getString("nisn"));
                        nilai.setKode_mapel(ob.getString("kode_mapel"));
                        nilai.setNama(ob.getString("nama"));
                        nilai.setNilai(ob.getString("nilai"));
                        nilai.setKkm(ob.getString("kkm"));

                        nilaiList.add(nilai);
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
                parms.put("nisn", nisn);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void getNilaiuas(String nisn) {
        nilaiList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/nilaiuas.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Nilai nilai = new Nilai();
                        nilai.setNisn(ob.getString("nisn"));
                        nilai.setKode_mapel(ob.getString("kode_mapel"));
                        nilai.setNama(ob.getString("nama"));
                        nilai.setNilai(ob.getString("nilai"));
                        nilai.setKkm(ob.getString("kkm"));

                        nilaiList.add(nilai);
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
                parms.put("nisn", nisn);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}