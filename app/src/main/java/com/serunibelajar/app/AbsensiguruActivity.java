package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AbsensiguruActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{


    private JSONArray resultjurusan, resultkelas, resultmapel, resultsiswa;
    private ArrayList<String> namajurusan, kodejurusan, namakelas, kodekelas, namamapel ,kodemapel, namasiswa, nisnsiswa;
    Spinner spinnerjurusan, spinnerkelas, spinnermapel ;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    TextView tanggal;
    LinearLayout tanggallayout;
    private List<Absensi> absensiList;
    private RecyclerView.Adapter adapter;
    private RecyclerView mList;
    SpinnerwhiteAdapter spinnerwhiteAdapter;
    SpinnerAdapter spinnerAdapter;

    private Dialog customDialog;
    private Spinner spinnersiswa, spinnerstatus;
    private Button btntambah;
    private String[] status = {
            "Pilih Status",
            "Hadir",
            "Izin",
            "Sakit",
            "Alpa"
    };

    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensiguru);

        Tools.setSystemBarColor(this, R.color.colorPrimary);

        mList = findViewById(R.id.recyclerviewabsensi);
        absensiList = new ArrayList<>();

        mList.setHasFixedSize(true);
        mList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AbsensiguruAdapter(this, absensiList);

        customDialog = new Dialog(AbsensiguruActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.tambahabsensi_dialog);
        customDialog.setCancelable(true);

        spinnersiswa = customDialog.findViewById(R.id.spinnersiswa);
        spinnerstatus = customDialog.findViewById(R.id.spinnerstatus);
        spinnerstatus.setAdapter(new ArrayAdapter<String>(AbsensiguruActivity.this, R.layout.text_spinner, R.id.textView, status));
        spinnerstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btntambah = customDialog.findViewById(R.id.btntambah);
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnersiswa.getSelectedItem().toString().equals("Pilih Siswa"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Siswa Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerstatus.getSelectedItem().toString().equals("Pilih Status"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Status Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/tambahabsensi.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                rQueue.getCache().clear();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.optString("success").equals("1")) {
                                        Toast.makeText(AbsensiguruActivity.this, "Berhasil Menambahkan Absensi Siswa", Toast.LENGTH_SHORT).show();
                                        customDialog.dismiss();
                                    } else {
                                        Toast.makeText(AbsensiguruActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        customDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    customDialog.dismiss();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AbsensiguruActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                customDialog.dismiss();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("nisn",  spinnersiswa.getSelectedItem().toString());
                        params.put("kode_mapel", spinnermapel.getSelectedItem().toString());
                        params.put("kehadiran", spinnerstatus.getSelectedItem().toString());
                        params.put("tanggal", tanggal.getText().toString());
                        return params;
                    }
                };
                rQueue = Volley.newRequestQueue(AbsensiguruActivity.this);
                rQueue.add(stringRequest);
            }
        });

        calendar = Calendar.getInstance();
        namajurusan = new ArrayList<String>();
        kodejurusan = new ArrayList<String>();
        namakelas = new ArrayList<String>();
        kodekelas = new ArrayList<String>();
        namamapel = new ArrayList<String>();
        kodemapel = new ArrayList<String>();
        namasiswa = new ArrayList<String>();
        nisnsiswa = new ArrayList<String>();

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

        spinnerkelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMapel(adapterView.getItemAtPosition(i).toString(), spinnerjurusan.getSelectedItem().toString() ,getIntent().getStringExtra("sekolah"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnermapel = findViewById(R.id.spinnermapel);

        tanggallayout = (LinearLayout) findViewById(R.id.tanggallayout);
        tanggal = (TextView) findViewById(R.id.tanggal);
        tanggallayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AbsensiguruActivity.this, AbsensiguruActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AbsensiguruActivity.this, AbsensiguruActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        Button btncari = findViewById(R.id.btncari);
        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerjurusan.getSelectedItem().toString().equals("Pilih Jurusan"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnermapel.getSelectedItem().toString().equals("Pilih Mapel"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Mapel Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(tanggal.getText().toString().equals("Tanggal"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Isi Tanggal Tugas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }


                getAbsensi(tanggal.getText().toString(), spinnermapel.getSelectedItem().toString());
            }
        });

        Button btntambah = findViewById(R.id.btntambah);
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerjurusan.getSelectedItem().toString().equals("Pilih Jurusan"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnermapel.getSelectedItem().toString().equals("Pilih Mapel"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Pilih Mapel Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(tanggal.getText().toString().equals("Tanggal"))
                {
                    Toast.makeText(AbsensiguruActivity.this, "Isi Tanggal Tugas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                getSiswakelas(getIntent().getStringExtra("sekolah"), spinnerjurusan.getSelectedItem().toString(), spinnerkelas.getSelectedItem().toString());
            }
        });


        getJurusan(getIntent().getStringExtra("sekolah"));
    }

    private void getSiswakelas(String sekolah, String jurusan, String kelas) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://serunibelajar.co.id/absensi/getsiswakelas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultsiswa = j.getJSONArray("result");
                            getsiswakelas(resultsiswa);

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
                SessionManager sessionManager = new SessionManager(AbsensiguruActivity.this);
                params.put("sekolah",  sekolah);
                params.put("jurusan", jurusan);
                params.put("kelas", kelas);
                return params;
            }
        };;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getsiswakelas(JSONArray j) {
        namasiswa.clear();
        nisnsiswa.clear();
        namasiswa.add("Pilih Siswa");
        nisnsiswa.add("0000");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namasiswa.add(json.getString("nama"));
                nisnsiswa.add(json.getString("nisn"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerAdapter = new SpinnerAdapter(AbsensiguruActivity.this, namasiswa, nisnsiswa);
        spinnersiswa.setAdapter(spinnerAdapter);
        customDialog.show();
    }

    private void getAbsensi(String tanggal, String mapel) {
        absensiList.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://serunibelajar.co.id/absensi/absensiviewguru.php", new Response.Listener<String>() {
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
                        absensi.setNamasiswa(ob.getString("nama_siswa"));

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
                parms.put("mapel", mapel);
                parms.put("tanggal", tanggal);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getMapel(String kelas, String jurusan, String sekolah) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://serunibelajar.co.id/absensi/getmapel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultmapel = j.getJSONArray("result");
                            getmapel(resultmapel);

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
                params.put("sekolah", sekolah);
                params.put("jurusan", jurusan);
                params.put("kelas", firstTwo(kelas));
                return params;
            }
        };
        ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }


    private void getmapel(JSONArray j) {
        namamapel.clear();
        kodemapel.clear();
        namamapel.add("Pilih Mapel");
        kodemapel.add("0000");
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namamapel.add(json.getString("nama"));
                kodemapel.add(json.getString("kode_mapel"));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(AbsensiguruActivity.this, namamapel, kodemapel);
        spinnermapel.setAdapter(spinnerwhiteAdapter);
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
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(AbsensiguruActivity.this, namakelas, kodekelas);
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
        spinnerwhiteAdapter = new SpinnerwhiteAdapter(AbsensiguruActivity.this, namajurusan, kodejurusan);
        spinnerjurusan.setAdapter(spinnerwhiteAdapter);
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
    }
}