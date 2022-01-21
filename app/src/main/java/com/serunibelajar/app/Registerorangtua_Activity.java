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

public class Registerorangtua_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    LinearLayout tanggallahirlayout;
    Spinner spinnerjk;
    TextView tanggallahir;
    private JSONArray result;
    private ArrayList<String> namajk, idjk;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    EditText nik, nama, tempatlahir, alamat,nohp, pekerjaan, status;
    SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerorangtua_);

        Tools.setSystemBarColor(this, R.color.colortop);

        namajk = new ArrayList<String>();
        idjk = new ArrayList<String>();
        calendar = Calendar.getInstance();
        spinnerjk = (Spinner) findViewById(R.id.spinnerjk);
        status = (EditText) findViewById(R.id.status);
        tanggallahirlayout = (LinearLayout) findViewById(R.id.tanggallahirlayout);
        tanggallahir = (TextView) findViewById(R.id.tanggallahir);
        nik = (EditText) findViewById(R.id.nik);
        nama = (EditText) findViewById(R.id.namalengkap);
        tempatlahir = (EditText) findViewById(R.id.tempatlahir);
        alamat = (EditText) findViewById(R.id.alamat);
        nohp = (EditText) findViewById(R.id.nohp);
        pekerjaan = (EditText) findViewById(R.id.pekerjaan);

        tanggallahirlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Registerorangtua_Activity.this, Registerorangtua_Activity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggallahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Registerorangtua_Activity.this, Registerorangtua_Activity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        getjk();

        ImageView btnnext = findViewById(R.id.buttonnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nik.getText().toString().isEmpty()) {
                    nik.setError("NIK is required");
                    nik.requestFocus();
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
                if (spinnerjk.getSelectedItem().toString().equals("Belum Terisi")) {
                    Toast.makeText(Registerorangtua_Activity.this, "Jenis Kelamin is required", Toast.LENGTH_LONG).show();
                    spinnerjk.requestFocus();
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
                if (status.getText().toString().equals("")) {
                    Toast.makeText(Registerorangtua_Activity.this, "Status Keluarga is required", Toast.LENGTH_LONG).show();
                    status.requestFocus();
                    return;
                }
                if (pekerjaan.getText().toString().isEmpty()) {
                    pekerjaan.setError("Pekerjaan is required");
                    pekerjaan.requestFocus();
                    return;
                }


                Intent i = new Intent(Registerorangtua_Activity.this, LastregisterActivity.class);
                i.putExtra("nik_wali", nik.getText().toString());
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("alamat", alamat.getText().toString());
                i.putExtra("tempat_lahir", tempatlahir.getText().toString());
                i.putExtra("tanggal_lahir", tanggallahir.getText().toString());
                i.putExtra("id_jk", String.valueOf(spinnerjk.getSelectedItem().toString()));
                i.putExtra("no_hp", nohp.getText().toString());
                i.putExtra("status_keluarga", status.getText().toString());
                i.putExtra("pekerjaan", pekerjaan.getText().toString());
                i.putExtra("status", "Aktif");
                i.putExtra("previllage", "Orang Tua");
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
        spinnerAdapter = new SpinnerAdapter(Registerorangtua_Activity.this, namajk, idjk);
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