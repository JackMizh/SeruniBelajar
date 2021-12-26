package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class RegistersiswaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    LinearLayout tanggallahirlayout;
    Spinner spinnerstatus, spinneragama, spinnerjk, spinnersekolah;
    TextView tanggallahir;
    private JSONArray result, resultagama, resultstatus, resultsekolah;
    private ArrayList<String> jk, agama, status, sekolah;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    EditText nisn, npsn, nipd, nama, tempatlahir, alamat, nohp, nikayah, nikibu, nikwali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersiswa);

        Tools.setSystemBarColor(this, R.color.colortop);

        jk = new ArrayList<String>();
        agama = new ArrayList<String>();
        status = new ArrayList<String>();
        sekolah = new ArrayList<String>();
        calendar = Calendar.getInstance();
        spinnerjk = (Spinner) findViewById(R.id.spinnerjk);
        spinneragama = (Spinner) findViewById(R.id.spinneragama);
        spinnerstatus = (Spinner) findViewById(R.id.spinnerstatus);
        spinnersekolah = (Spinner) findViewById(R.id.spinnersekolah);
        tanggallahirlayout = (LinearLayout) findViewById(R.id.tanggallahirlayout);
        tanggallahir = (TextView) findViewById(R.id.tanggallahir);
        nisn = (EditText) findViewById(R.id.nisn);
        npsn = (EditText) findViewById(R.id.npsn);
        nipd = (EditText) findViewById(R.id.nipd);
        nama = (EditText) findViewById(R.id.namalengkap);
        tempatlahir = (EditText) findViewById(R.id.tempatlahir);
        alamat = (EditText) findViewById(R.id.alamat);
        nohp = (EditText) findViewById(R.id.nohp);
        nikayah = (EditText) findViewById(R.id.nikayah);
        nikibu = (EditText) findViewById(R.id.nikibu);
        nikwali = (EditText) findViewById(R.id.nikwali);

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
        getstatus();
        getsekolah();


        ImageView btnnext = findViewById(R.id.buttonnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerstatus = findViewById(R.id.spinnerstatus);
                if (nisn.getText().toString().isEmpty()) {
                    nisn.setError("NISN is required");
                    nisn.requestFocus();
                    return;
                }
                if (npsn.getText().toString().isEmpty()) {
                    npsn.setError("NPSN is required");
                    npsn.requestFocus();
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
                    Toast.makeText(RegistersiswaActivity.this, "Jenis Kelamin is required", Toast.LENGTH_LONG).show();
                    spinnerjk.requestFocus();
                    return;
                }
                if (spinneragama.getSelectedItem().toString().equals("Agama")) {
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
                if (spinnerstatus.getSelectedItem().toString().equals("Status")) {
                    Toast.makeText(RegistersiswaActivity.this, "Status is required", Toast.LENGTH_LONG).show();
                    spinnerstatus.requestFocus();
                    return;
                }
                if (nikayah.getText().toString().isEmpty()) {
                    nikayah.setError("NIK Ayah is required");
                    nikayah.requestFocus();
                    return;
                }
                if (nikibu.getText().toString().isEmpty()) {
                    nikibu.setError("NIK Ibu is required");
                    nikibu.requestFocus();
                    return;
                }

                Intent i = new Intent(RegistersiswaActivity.this, LastregisterActivity.class);
                i.putExtra("nisn", nisn.getText().toString());
                i.putExtra("npsn", npsn.getText().toString());
                i.putExtra("nipd", nipd.getText().toString());
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("sekolah", String.valueOf(spinnersekolah.getSelectedItemPosition()));
                i.putExtra("tempatlahir", tempatlahir.getText().toString());
                i.putExtra("tanggallahir", tanggallahir.getText().toString());
                i.putExtra("jeniskelamin", String.valueOf(spinnerjk.getSelectedItemPosition()));
                i.putExtra("alamat", alamat.getText().toString());
                i.putExtra("agama", String.valueOf(spinneragama.getSelectedItemPosition()));
                i.putExtra("nohp", nohp.getText().toString());
                i.putExtra("status", String.valueOf(spinnerstatus.getSelectedItemPosition()));
                i.putExtra("nikayah", nikayah.getText().toString());
                i.putExtra("nikibu", nikibu.getText().toString());
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
        StringRequest stringRequest = new StringRequest("https://plazatanaman.com/sipren/sekolahabdian.php",
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
        sekolah.clear();
        sekolah.add("Asal Sekolah");
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                sekolah.add(json.getString("nama_sekolah"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnersekolah.setAdapter(new ArrayAdapter<String>(RegistersiswaActivity.this, R.layout.text_spinner, R.id.textView, sekolah));
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
        spinnerstatus.setAdapter(new ArrayAdapter<String>(RegistersiswaActivity.this, R.layout.text_spinner, R.id.textView, status));
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
        spinneragama.setAdapter(new ArrayAdapter<String>(RegistersiswaActivity.this, R.layout.text_spinner, R.id.textView, agama));

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
        spinnerjk.setAdapter(new ArrayAdapter<String>(RegistersiswaActivity.this, R.layout.text_spinner, R.id.textView, jk));

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