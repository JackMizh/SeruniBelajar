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

public class RegisterguruActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    LinearLayout tanggallahirlayout;
    Spinner spinnerstatus, spinneragama, spinnerjk, spinnerstatuspegawai, spinnerjabatan, spinnersekolahabdian;
    TextView tanggallahir;
    private JSONArray result, resultagama, resultstatus, resultstatuspegawai, resultjabatan, resultsekolahabdian;
    private ArrayList<String> jk, agama, status, statuspegawai, jabatan, sekolahabdian;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    EditText nip, npsn, nuptk, nama, tempatlahir, alamat, tugastambahan, nohp, nosertifikasi, golongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerguru);

        Tools.setSystemBarColor(this, R.color.colortop);

        jk = new ArrayList<String>();
        agama = new ArrayList<String>();
        status = new ArrayList<String>();
        statuspegawai = new ArrayList<String>();
        jabatan = new ArrayList<String>();
        sekolahabdian = new ArrayList<String>();
        calendar = Calendar.getInstance();
        spinnerjk = (Spinner) findViewById(R.id.spinnerjk);
        spinneragama = (Spinner) findViewById(R.id.spinneragama);
        spinnerstatus = (Spinner) findViewById(R.id.spinnerstatus);
        spinnerstatuspegawai = (Spinner) findViewById(R.id.spinnerstatuspegawai);
        spinnerjabatan = (Spinner) findViewById(R.id.spinnerjabatan);
        spinnersekolahabdian = (Spinner) findViewById(R.id.spinnersekolahabdian);
        tanggallahirlayout = (LinearLayout) findViewById(R.id.tanggallahirlayout);
        tanggallahir = (TextView) findViewById(R.id.tanggallahir);
        nip = (EditText) findViewById(R.id.nip);
        npsn = (EditText) findViewById(R.id.npsn);
        nuptk = (EditText) findViewById(R.id.nuptk);
        nama = (EditText) findViewById(R.id.namalengkap);
        tempatlahir = (EditText) findViewById(R.id.tempatlahir);
        alamat = (EditText) findViewById(R.id.alamat);
        tugastambahan = (EditText) findViewById(R.id.tugastambahan);
        nohp = (EditText) findViewById(R.id.nohp);
        nosertifikasi = (EditText) findViewById(R.id.nosertifikasi);
        golongan = (EditText) findViewById(R.id.golongan);

        tanggallahirlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RegisterguruActivity.this, RegisterguruActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggallahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RegisterguruActivity.this, RegisterguruActivity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        getjk();
        getagama();
        getstatus();
        getstatuspegawai();
        getjabatan();
        getsekolahabdian();

        ImageView btnnext = findViewById(R.id.buttonnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerstatus = findViewById(R.id.spinnerstatus);
                if (nip.getText().toString().isEmpty()) {
                    nip.setError("NIP is required");
                    nip.requestFocus();
                    return;
                }
                if (npsn.getText().toString().isEmpty()) {
                    npsn.setError("NPSN is required");
                    npsn.requestFocus();
                    return;
                }
                if (nuptk.getText().toString().isEmpty()) {
                    nuptk.setError("NUPTK is required");
                    nuptk.requestFocus();
                    return;
                }
                if (nama.getText().toString().isEmpty()) {
                    nama.setError("Nama Lengkap is required");
                    nama.requestFocus();
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
                if (spinnerjk.getSelectedItem().toString().equals("Jenis Kelamin")) {
                    Toast.makeText(RegisterguruActivity.this, "Jenis Kelamin is required", Toast.LENGTH_LONG).show();
                    spinnerjk.requestFocus();
                    return;
                }
                if (alamat.getText().toString().isEmpty()) {
                    alamat.setError("Alamat is required");
                    alamat.requestFocus();
                    return;
                }
                if (spinneragama.getSelectedItem().toString().equals("Agama")) {
                    Toast.makeText(RegisterguruActivity.this, "Agama is required", Toast.LENGTH_LONG).show();
                    spinneragama.requestFocus();
                    return;
                }
                if (spinnerstatuspegawai.getSelectedItem().toString().equals("Status Pegawai")) {
                    Toast.makeText(RegisterguruActivity.this, "Status Pegawai is required", Toast.LENGTH_LONG).show();
                    spinnerstatuspegawai.requestFocus();
                    return;
                }
                if (spinnerjabatan.getSelectedItem().toString().equals("Jabatan")) {
                    Toast.makeText(RegisterguruActivity.this, "Status Jabatan is required", Toast.LENGTH_LONG).show();
                    spinnerjabatan.requestFocus();
                    return;
                }
                if (tugastambahan.getText().toString().isEmpty()) {
                    tugastambahan.setError("Tugas Tambahan is required");
                    tugastambahan.requestFocus();
                    return;
                }
                if (nohp.getText().toString().isEmpty()) {
                    nohp.setError("No. Handphone is required");
                    nohp.requestFocus();
                    return;
                }
                if (nosertifikasi.getText().toString().isEmpty()) {
                    nosertifikasi.setError("No. Sertifikasi is required");
                    nosertifikasi.requestFocus();
                    return;
                }
                if (golongan.getText().toString().isEmpty()) {
                    golongan.setError("Golongan is required");
                    golongan.requestFocus();
                    return;
                }
                if (spinnerstatus.getSelectedItem().toString().equals("Status")) {
                    Toast.makeText(RegisterguruActivity.this, "Status is required", Toast.LENGTH_LONG).show();
                    spinnerstatus.requestFocus();
                    return;
                }
                if (spinnersekolahabdian.getSelectedItem().toString().equals("Sekolah Abdian")) {
                    Toast.makeText(RegisterguruActivity.this, "Sekolah Abdian is required", Toast.LENGTH_LONG).show();
                    spinnersekolahabdian.requestFocus();
                    return;
                }


                Intent i = new Intent(RegisterguruActivity.this, LastregisterActivity.class);
                i.putExtra("nip", nip.getText().toString());
                i.putExtra("npsn", nip.getText().toString());
                i.putExtra("nuptk", nip.getText().toString());
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("tempatlahir", tempatlahir.getText().toString());
                i.putExtra("tanggallahir", tanggallahir.getText().toString());
                i.putExtra("jeniskelamin", String.valueOf(spinnerjk.getSelectedItemPosition()));
                i.putExtra("alamat", alamat.getText().toString());
                i.putExtra("agama", String.valueOf(spinneragama.getSelectedItemPosition()));
                i.putExtra("statuspegawai", String.valueOf(spinnerstatuspegawai.getSelectedItemPosition()));
                i.putExtra("jabatan", String.valueOf(spinnerjabatan.getSelectedItemPosition()));
                i.putExtra("tugastambahan", tugastambahan.getText().toString());
                i.putExtra("nohp", nohp.getText().toString());
                i.putExtra("nosertifikasi", nosertifikasi.getText().toString());
                i.putExtra("golongan", golongan.getText().toString());
                i.putExtra("status", String.valueOf(spinnerstatus.getSelectedItemPosition()));
                i.putExtra("sekolahabdian", String.valueOf(spinnersekolahabdian.getSelectedItemPosition()));
                i.putExtra("previllage", "Guru");
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

    private void getsekolahabdian() {
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/sekolahabdian.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultsekolahabdian = j.getJSONArray("result");
                            getSekolahabdian(resultsekolahabdian);

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

    private void getSekolahabdian(JSONArray j) {
        sekolahabdian.clear();
        sekolahabdian.add("Sekolah Abdian");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                sekolahabdian.add(json.getString("nama_sekolah"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnersekolahabdian.setAdapter(new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, sekolahabdian));
    }

    private void getjabatan() {
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/jabatan.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultjabatan = j.getJSONArray("result");
                            getJabatan(resultjabatan);

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

    private void getJabatan(JSONArray j) {
        jabatan.clear();
        jabatan.add("Jabatan");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                jabatan.add(json.getString("nama_jabatan"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerjabatan.setAdapter(new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, jabatan));
    }

    private void getstatuspegawai() {
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/statuspegawai.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultstatuspegawai = j.getJSONArray("result");
                            getStatuspegawai(resultstatuspegawai);

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

    private void getStatuspegawai(JSONArray j) {
        statuspegawai.clear();
        statuspegawai.add("Status Pegawai");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                statuspegawai.add(json.getString("nama_statuspegawai"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerstatuspegawai.setAdapter(new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, statuspegawai));
    }

    private void getstatus() {
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resultstatus = j.getJSONArray("result");
                            getStatus(resultstatus);

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

    private void getStatus(JSONArray j) {
        status.clear();
        status.add("Status");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                status.add(json.getString("nama_status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerstatus.setAdapter(new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, status));
    }

    private void getagama() {
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/agama.php",
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

    private void getjk() {
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/jk.php",
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

    private void getAgama(JSONArray j) {
        agama.clear();
        agama.add("Agama");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                agama.add(json.getString("nama_agama"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinneragama.setAdapter(new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, agama));

    }

    private void getJenisKelamin(JSONArray j) {
        jk.clear();
        jk.add("Jenis Kelamin");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                jk.add(json.getString("nama_jk"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerjk.setAdapter(new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, jk));

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