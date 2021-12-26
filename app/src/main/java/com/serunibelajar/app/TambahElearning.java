package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

public class TambahElearning extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private JSONArray resultjurusan, resultkelas, resultmapel;
    private ArrayList<String> jurusan, kelas, mapel;
    Spinner spinnerjurusan, spinnerkelas, spinnermapel;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    TextView tanggalelearning;
    LinearLayout tanggalelearninglayout;
    EditText judul, file, youtube;
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_elearning);
        Tools.setSystemBarColor(this, R.color.colortop);

        judul = findViewById(R.id.judulelearning);
        file = findViewById(R.id.fileelearning);
        youtube = findViewById(R.id.youtubeelearning);

        ImageView next = findViewById(R.id.buttonnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinnerjurusan.getSelectedItem().toString().equals("Pilih Jurusan")) {
                    Toast.makeText(TambahElearning.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas")) {
                    Toast.makeText(TambahElearning.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spinnermapel.getSelectedItem().toString().equals("Pilih Mapel")) {
                    Toast.makeText(TambahElearning.this, "Pilih Mapel Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (judul.getText().toString().equals("")) {
                    Toast.makeText(TambahElearning.this, "Isi Judul Elearning Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tanggalelearning.getText().toString().equals("Tanggal Elearning")) {
                    Toast.makeText(TambahElearning.this, "Isi Tanggal Elearning Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (file.getText().toString().equals("")) {
                    Toast.makeText(TambahElearning.this, "Isi Link File Elearning Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (youtube.getText().toString().equals("")) {
                    Toast.makeText(TambahElearning.this, "Isi Link Youtube Elearning Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                SessionManager sessionManager = new SessionManager(TambahElearning.this);
                insertelearning(getIntent().getStringExtra("sekolah"), String.valueOf(spinnerjurusan.getSelectedItemPosition()), String.valueOf(spinnerkelas.getSelectedItemPosition()), String.valueOf(spinnermapel.getSelectedItemPosition()), judul.getText().toString(), tanggalelearning.getText().toString(), file.getText().toString(), youtube.getText().toString(), sessionManager.getUserDetail().get("NAMALENGKAP"));
            }
        });

        calendar = Calendar.getInstance();
        tanggalelearninglayout = (LinearLayout) findViewById(R.id.tanggalelearningayout);
        tanggalelearning = (TextView) findViewById(R.id.tanggalelearning);
        tanggalelearninglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(TambahElearning.this, TambahElearning.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggalelearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(TambahElearning.this, TambahElearning.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        jurusan = new ArrayList<String>();
        kelas = new ArrayList<String>();
        mapel = new ArrayList<String>();
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

        spinnerkelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMapel(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnermapel = findViewById(R.id.spinnermapel);

        getJurusan();
    }

    private void insertelearning(String sekolah, String jurusan, String kelas, String mapel, String judul, String tanggal, String file, String youtube, String namalengkap) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/tambahelearning.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("success").equals("1")) {
                                Toast.makeText(TambahElearning.this, "Berhasil Menambahkan Elearning", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(TambahElearning.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TambahElearning.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sekolah", sekolah);
                params.put("jurusan", jurusan);
                params.put("kelas", kelas);
                params.put("mapel", mapel);
                params.put("judul", judul);
                params.put("tanggal", tanggal);
                params.put("file", file);
                params.put("youtube", youtube);
                params.put("namalengkap", namalengkap);
                return params;
            }
        };
        rQueue = Volley.newRequestQueue(TambahElearning.this);
        rQueue.add(stringRequest);
    }

    private void getMapel(int i) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/getmapel.php",
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(TambahElearning.this);
                params.put("sekolah", getIntent().getStringExtra("sekolah"));
                params.put("jurusan", String.valueOf(spinnerjurusan.getSelectedItemPosition()));
                params.put("kelas", String.valueOf(i));
                return params;
            }
        };
        ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getmapel(JSONArray j) {
        mapel.clear();
        mapel.add("Pilih Mapel");
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                mapel.add(json.getString("nama_mapel"));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnermapel.setAdapter(new ArrayAdapter<String>(TambahElearning.this, R.layout.text_spinner, R.id.textView, mapel));
    }

    private void getKelas(int itemAtPosition) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/kelas.php",
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(TambahElearning.this);
                params.put("sekolah", getIntent().getStringExtra("sekolah"));
                params.put("jurusan", String.valueOf(itemAtPosition));
                return params;
            }
        };
        ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getkelas(JSONArray j) {
        kelas.clear();
        kelas.add("Pilih Kelas");
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                kelas.add(json.getString("nama_kelas"));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerkelas.setAdapter(new ArrayAdapter<String>(TambahElearning.this, R.layout.text_spinner, R.id.textView, kelas));
    }

    private void getJurusan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/jurusan.php",
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SessionManager sessionManager = new SessionManager(TambahElearning.this);
                params.put("sekolah", getIntent().getStringExtra("sekolah"));
                return params;
            }
        };
        ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getjurusan(JSONArray j) {
        jurusan.clear();
        jurusan.add("Pilih Jurusan");
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                jurusan.add(json.getString("nama_jurusan"));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerjurusan.setAdapter(new ArrayAdapter<String>(TambahElearning.this, R.layout.text_spinner, R.id.textView, jurusan));
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
        tanggalelearning.setText(i + "-" + (i1 + 1) + "-" + i2);
    }
}