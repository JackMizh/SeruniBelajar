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

public class RegisterguruActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    LinearLayout tanggallahirlayout;
    Spinner spinneragama, spinnerjk, spinnerstatuspegawai, spinnertugastambahan, spinnersekolah;
    TextView tanggallahir;
    private JSONArray result, resultagama, resultsekolah, resulttugastambahan;
    private ArrayList<String> idtugastambahan, namatugastambahan, namajk, idjk, namaagama, idagama, namasekolah, npsnsekolah;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    EditText nip, npsn, nuptk, nama, tempatlahir, alamat, nohp, nosertifikasi, golongan;
    SpinnerAdapter spinnerAdapter;
    String[] statuspegawai = {
            "Status Pegawai",
            "PNS",
            "Non PNS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerguru);

        Tools.setSystemBarColor(this, R.color.colortop);

        idtugastambahan = new ArrayList<String>();
        namatugastambahan = new ArrayList<String>();
        namajk = new ArrayList<String>();
        idjk = new ArrayList<String>();
        namaagama = new ArrayList<String>();
        idagama = new ArrayList<String>();
        namasekolah = new ArrayList<String>();
        npsnsekolah = new ArrayList<String>();
        calendar = Calendar.getInstance();
        spinnerjk = (Spinner) findViewById(R.id.spinnerjk);
        spinneragama = (Spinner) findViewById(R.id.spinneragama);
        spinnersekolah = (Spinner) findViewById(R.id.spinnersekolah);
        spinnerstatuspegawai = (Spinner) findViewById(R.id.spinnerstatuspegawai);
        spinnertugastambahan = (Spinner) findViewById(R.id.spinnertugastambahan);
        tanggallahirlayout = (LinearLayout) findViewById(R.id.tanggallahirlayout);
        tanggallahir = (TextView) findViewById(R.id.tanggallahir);
        nip = (EditText) findViewById(R.id.nip);
        npsn = (EditText) findViewById(R.id.npsn);
        nuptk = (EditText) findViewById(R.id.nuptk);
        nama = (EditText) findViewById(R.id.namalengkap);
        tempatlahir = (EditText) findViewById(R.id.tempatlahir);
        alamat = (EditText) findViewById(R.id.alamat);
        nohp = (EditText) findViewById(R.id.nohp);
        nosertifikasi = (EditText) findViewById(R.id.nosertifikasi);
        golongan = (EditText) findViewById(R.id.golongan);

        final ArrayAdapter adapter = new ArrayAdapter<String>(RegisterguruActivity.this, R.layout.text_spinner, R.id.textView, statuspegawai);
        spinnerstatuspegawai.setAdapter(adapter);

        spinnersekolah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
        getsekolah();
        gettugastambahan();

        ImageView btnnext = findViewById(R.id.buttonnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nip.getText().toString().isEmpty()) {
                    nip.setError("NIP is required");
                    nip.requestFocus();
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
                if (spinnersekolah.getSelectedItem().toString().equals("Asal Sekolah")) {
                    Toast.makeText(RegisterguruActivity.this, "Asal Sekolah is required", Toast.LENGTH_LONG).show();
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
                if (spinnerjk.getSelectedItem().toString().equals("Belum Terisi")) {
                    Toast.makeText(RegisterguruActivity.this, "Jenis Kelamin is required", Toast.LENGTH_LONG).show();
                    spinnerjk.requestFocus();
                    return;
                }
                if (alamat.getText().toString().isEmpty()) {
                    alamat.setError("Alamat is required");
                    alamat.requestFocus();
                    return;
                }
                if (spinneragama.getSelectedItem().toString().equals("Belum Terisi")) {
                    Toast.makeText(RegisterguruActivity.this, "Agama is required", Toast.LENGTH_LONG).show();
                    spinneragama.requestFocus();
                    return;
                }
                if (spinnerstatuspegawai.getSelectedItem().toString().equals("Status Pegawai")) {
                    Toast.makeText(RegisterguruActivity.this, "Status Pegawai is required", Toast.LENGTH_LONG).show();
                    spinnerstatuspegawai.requestFocus();
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


                Intent i = new Intent(RegisterguruActivity.this, LastregisterActivity.class);
                i.putExtra("nip", nip.getText().toString());
                i.putExtra("npsn", spinnersekolah.getSelectedItem().toString());
                i.putExtra("nuptk", nuptk.getText().toString());
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("id_jk", spinnerjk.getSelectedItem().toString());
                i.putExtra("tempat_lahir", tempatlahir.getText().toString());
                i.putExtra("tanggal_lahir", tanggallahir.getText().toString());
                i.putExtra("status_kepegawaian", spinnerstatuspegawai.getSelectedItem().toString());
                i.putExtra("id_agama", spinneragama.getSelectedItem().toString());
                i.putExtra("alamat", alamat.getText().toString());
                i.putExtra("no_hp", nohp.getText().toString());
                i.putExtra("id_tgs_tambahan", spinnertugastambahan.getSelectedItem().toString());
                i.putExtra("nomor_sertifikasi", nosertifikasi.getText().toString());
                i.putExtra("golongan", golongan.getText().toString());
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
        spinnerAdapter = new SpinnerAdapter(RegisterguruActivity.this, namasekolah, npsnsekolah);
        spinnersekolah.setAdapter(spinnerAdapter);
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
        spinnerAdapter = new SpinnerAdapter(RegisterguruActivity.this, namaagama, idagama);
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
        spinnerAdapter = new SpinnerAdapter(RegisterguruActivity.this, namajk, idjk);
        spinnerjk.setAdapter(spinnerAdapter);

    }

    private void gettugastambahan() {
        StringRequest stringRequest = new StringRequest("https://serunibelajar.co.id/absensi/tugastambahan.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            resulttugastambahan = j.getJSONArray("result");
                            getTugastambahan(resulttugastambahan);

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

    private void getTugastambahan(JSONArray j) {
        namatugastambahan.clear();
        idtugastambahan.clear();
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                namatugastambahan.add(json.getString("nama"));
                idtugastambahan.add(json.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerAdapter = new SpinnerAdapter(RegisterguruActivity.this, namatugastambahan, idtugastambahan);
        spinnertugastambahan.setAdapter(spinnerAdapter);

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