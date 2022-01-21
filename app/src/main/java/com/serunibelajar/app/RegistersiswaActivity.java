package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistersiswaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    LinearLayout tanggallahirlayout;
    Spinner spinneragama, spinnerjk, spinnersekolah, spinnerjurusan, spinnerkelas;
    TextView tanggallahir;
    private JSONArray result, resultagama, resultsekolah, resultjurusan, resultkelas;
    private ArrayList<String> namajk, idjk, namaagama, idagama, namasekolah, npsnsekolah, namajurusan, kodejurusan, namakelas, kodekelas;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    EditText nisn, nipd, nama, tempatlahir, alamat, nohp, nikwali;
    SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersiswa);

        Tools.setSystemBarColor(this, R.color.colortop);

        namajk = new ArrayList<String>();
        idjk = new ArrayList<String>();
        namaagama = new ArrayList<String>();
        idagama = new ArrayList<String>();
        namasekolah = new ArrayList<String>();
        npsnsekolah = new ArrayList<String>();
        namajurusan = new ArrayList<String>();
        kodejurusan = new ArrayList<String>();
        namakelas = new ArrayList<String>();
        kodekelas = new ArrayList<String>();

        calendar = Calendar.getInstance();

        spinnerjk = (Spinner) findViewById(R.id.spinnerjk);
        spinneragama = (Spinner) findViewById(R.id.spinneragama);
        spinnersekolah = (Spinner) findViewById(R.id.spinnersekolah);
        spinnerjurusan = (Spinner) findViewById(R.id.spinnerjurusan);
        spinnerkelas = (Spinner) findViewById(R.id.spinnerkelas);

        tanggallahirlayout = (LinearLayout) findViewById(R.id.tanggallahirlayout);
        tanggallahir = (TextView) findViewById(R.id.tanggallahir);
        nisn = (EditText) findViewById(R.id.nisn);
        nipd = (EditText) findViewById(R.id.nipd);
        nama = (EditText) findViewById(R.id.namalengkap);
        tempatlahir = (EditText) findViewById(R.id.tempatlahir);
        alamat = (EditText) findViewById(R.id.alamat);
        nohp = (EditText) findViewById(R.id.nohp);
        nikwali = (EditText) findViewById(R.id.nikwali);

        spinnersekolah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getJurusan(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerjurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getKelas(adapterView.getItemAtPosition(i).toString(), spinnersekolah.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tanggallahirlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RegistersiswaActivity.this, RegistersiswaActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggallahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RegistersiswaActivity.this, RegistersiswaActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        getjk();
        getagama();
        getsekolah();


        ImageView btnnext = findViewById(R.id.buttonnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nisn.getText().toString().isEmpty()) {
                    nisn.setError("NISN is required");
                    nisn.requestFocus();
                    return;
                }
                if (nipd.getText().toString().isEmpty()) {
                    nipd.setError("NIPD is required");
                    nipd.requestFocus();
                    return;
                }
                if (nama.getText().toString().isEmpty()) {
                    nama.setError("Nama Lengkap is required");
                    nama.requestFocus();
                    return;
                }
                if (spinnersekolah.getSelectedItem().toString().equals("Asal Sekolah")) {
                    Toast.makeText(RegistersiswaActivity.this, "Asal Sekolah is required", Toast.LENGTH_LONG).show();
                    spinnersekolah.requestFocus();
                    return;
                }
                if (spinnerjurusan.getSelectedItem().toString().equals("Jurusan")) {
                    Toast.makeText(RegistersiswaActivity.this, "Jurusan is required", Toast.LENGTH_LONG).show();
                    spinnerjurusan.requestFocus();
                    return;
                }
                if (spinnerkelas.getSelectedItem().toString().equals("Kelas")) {
                    Toast.makeText(RegistersiswaActivity.this, "Kelas is required", Toast.LENGTH_LONG).show();
                    spinnerkelas.requestFocus();
                    return;
                }
                if (tempatlahir.getText().toString().isEmpty()) {
                    tempatlahir.setError("Tempat Lahir is required");
                    tempatlahir.requestFocus();
                    return;
                }
                if (tanggallahir.getText().toString().equals("Tanggal Lahir")) {
                    tanggallahir.setError("Tanggal Lahir is required");
                    tanggallahir.requestFocus();
                    return;
                }
                if (spinnerjk.getSelectedItem().toString().equals("Belum Terisi")) {
                    Toast.makeText(RegistersiswaActivity.this, "Jenis Kelamin is required", Toast.LENGTH_LONG).show();
                    spinnerjk.requestFocus();
                    return;
                }
                if (spinneragama.getSelectedItem().toString().equals("Belum Terisi")) {
                    Toast.makeText(RegistersiswaActivity.this, "Agama is required", Toast.LENGTH_LONG).show();
                    spinneragama.requestFocus();
                    return;
                }
                if (alamat.getText().toString().isEmpty()) {
                    alamat.setError("Alamat is required");
                    alamat.requestFocus();
                    return;
                }
                if (nohp.getText().toString().isEmpty()) {
                    nohp.setError("No. Handphone is required");
                    nohp.requestFocus();
                    return;
                }
                if (nikwali.getText().toString().isEmpty()) {
                    nikwali.setError("NIK Wali is required");
                    nikwali.requestFocus();
                    return;
                }

                Intent i = new Intent(RegistersiswaActivity.this, LastregisterActivity.class);
                i.putExtra("nisn", nisn.getText().toString());
                i.putExtra("nipd", nipd.getText().toString());
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("npsn", spinnersekolah.getSelectedItem().toString());
                i.putExtra("jurusan", spinnerjurusan.getSelectedItem().toString());
                i.putExtra("kelas", spinnerkelas.getSelectedItem().toString());
                i.putExtra("tempatlahir", tempatlahir.getText().toString());
                i.putExtra("tanggallahir", tanggallahir.getText().toString());
                i.putExtra("jeniskelamin", spinnerjk.getSelectedItem().toString());
                i.putExtra("agama", spinneragama.getSelectedItem().toString());
                i.putExtra("alamat", alamat.getText().toString());
                i.putExtra("nohp", nohp.getText().toString());
                i.putExtra("status", "Aktif");
                i.putExtra("nikwali", nikwali.getText().toString());
                i.putExtra("previllage", "Siswa");
                startActivity(i);
            }
        });

        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getsekolah() {
        StringRequest stringRequest = new StringRequest("https://serunibelajar.co.id/absensi/sekolahabdian.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultsekolah = j.getJSONArray("result");
                            getSekolah(resultsekolah);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getSekolah(JSONArray j) {
        npsnsekolah.clear();
        namasekolah.clear();
        namasekolah.add("Asal Sekolah");
        npsnsekolah.add("0000");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namasekolah.add(json.getString("nama"));
                npsnsekolah.add(json.getString("npsn"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerAdapter = new SpinnerAdapter(RegistersiswaActivity.this, namasekolah, npsnsekolah);
        spinnersekolah.setAdapter(spinnerAdapter);
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
        namajurusan.add("Jurusan");
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
        spinnerAdapter = new SpinnerAdapter(RegistersiswaActivity.this, namajurusan, kodejurusan);
        spinnerjurusan.setAdapter(spinnerAdapter);
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
        namakelas.add("Kelas");
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
        spinnerAdapter = new SpinnerAdapter(RegistersiswaActivity.this, namakelas, kodekelas);
        spinnerkelas.setAdapter(spinnerAdapter);
    }

    private void getagama() {
        StringRequest stringRequest = new StringRequest("https://serunibelajar.co.id/absensi/agama.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultagama = j.getJSONArray("result");
                            getAgama(resultagama);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getAgama(JSONArray j) {
        namaagama.clear();
        idagama.clear();
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namaagama.add(json.getString("nama"));
                idagama.add(json.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerAdapter = new SpinnerAdapter(RegistersiswaActivity.this, namaagama, idagama);
        spinneragama.setAdapter(spinnerAdapter);

    }

    private void getjk() {
        StringRequest stringRequest = new StringRequest("https://serunibelajar.co.id/absensi/jk.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("result");
                            getJenisKelamin(result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getJenisKelamin(JSONArray j) {
        namajk.clear();
        idjk.clear();
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namajk.add(json.getString("nama"));
                idjk.add(json.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerAdapter = new SpinnerAdapter(RegistersiswaActivity.this, namajk, idjk);
        spinnerjk.setAdapter(spinnerAdapter);

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
        tanggallahir.setText(i + "-" + (i1+1) + "-" + i2);
    }
}