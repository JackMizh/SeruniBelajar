package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AbsensiActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    TextView tanggal;
    LinearLayout tanggallayout;
    String sekolah_absen, jurusan_absen, kelas_absen, nama_siswa;
    private List<Absensi> absensiList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);

        Tools.setSystemBarColor(this, R.color.colorPrimary);

        mList = findViewById(R.id.recyclerviewabsensi);
        absensiList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AbsensiAdapter(this, absensiList);

        sekolah_absen = "";
        jurusan_absen = "";
        kelas_absen = "";
        nama_siswa = "";

        calendar = Calendar.getInstance();
        tanggallayout = (LinearLayout) findViewById(R.id.layouttanggal);
        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggallayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AbsensiActivity.this, AbsensiActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AbsensiActivity.this, AbsensiActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        getAbsensi(getIntent().getStringExtra("nisn"), formattedDate);
    }

    private void getAbsensi(String nisn, String tanggal) {
        absensiList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/absensi.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("result");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        Absensi absensi = new Absensi();
                        absensi.setNisn(ob.getString("nisn"));
                        absensi.setKode_mapel(ob.getString("kode_mapel"));
                        absensi.setNama(ob.getString("nama"));
                        absensi.setJam_mulai(ob.getString("jam_mulai"));
                        absensi.setJam_selesai(ob.getString("jam_selesai"));
                        absensi.setKehadiran(ob.getString("kehadiran"));
                        absensi.setTanggal(ob.getString("tanggal"));
                        absensi.setNamasiswa("");

                        absensiList.add(absensi);
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
                parms.put("tanggal", tanggal);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), previllage[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        tanggal.setText(i + "-" + (i1 + 1) + "-" + i2);
        getAbsensi(getIntent().getStringExtra("nisn"), tanggal.getText().toString());
    }
}