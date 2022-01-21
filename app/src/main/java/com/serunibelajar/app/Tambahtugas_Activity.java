    package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tambahtugas_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    private JSONArray resultjurusan, resultkelas, resultmapel;
    private ArrayList<String> namajurusan, kodejurusan, namakelas, kodekelas, namamapel ,kodemapel;
    Spinner spinnerjurusan, spinnerkelas, spinnermapel;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    TextView tanggaltugas;
    LinearLayout tanggaltugaslayout;
    EditText judul, youtube;
    private RequestQueue rQueue;
    SpinnerAdapter spinnerAdapter;
    private String nameoffile;
    private Uri urioffile;

    private Button btnchoose;
    private TextView txtfile;
    private String upload_URL = "https://serunibelajar.co.id/absensi/tambahtugas.php";
    private ArrayList<HashMap<String, String>> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahtugas_);

        Tools.setSystemBarColor(this, R.color.colortop);

        judul = findViewById(R.id.judultugas);
        youtube = findViewById(R.id.youtubetugas);

        btnchoose = findViewById(R.id.btn_choose);
        txtfile = findViewById(R.id.file);
        nameoffile = "";
        urioffile = null;

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
            }
        });

        ImageView next = findViewById(R.id.buttonnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerjurusan.getSelectedItem().toString().equals("Pilih Jurusan"))
                {
                    Toast.makeText(Tambahtugas_Activity.this, "Pilih Jurusan Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnerkelas.getSelectedItem().toString().equals("Pilih Kelas"))
                {
                    Toast.makeText(Tambahtugas_Activity.this, "Pilih Kelas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinnermapel.getSelectedItem().toString().equals("Pilih Mapel"))
                {
                    Toast.makeText(Tambahtugas_Activity.this, "Pilih Mapel Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(judul.getText().toString().equals(""))
                {
                    Toast.makeText(Tambahtugas_Activity.this, "Isi Judul Tugas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(tanggaltugas.getText().toString().equals("Tanggal Deadline Tugas"))
                {
                    Toast.makeText(Tambahtugas_Activity.this, "Isi Tanggal Deadline Tugas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtfile.getText().toString().equals("")) {
                    Toast.makeText(Tambahtugas_Activity.this, "Silahkan Pilih File Bahan Elearning Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }
                if(youtube.getText().toString().equals(""))
                {
                    Toast.makeText(Tambahtugas_Activity.this, "Isi Link Youtube Tugas Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }

                SessionManager sessionManager = new SessionManager(Tambahtugas_Activity.this);
//                inserttugas(getIntent().getStringExtra("sekolah"), String.valueOf(spinnerjurusan.getSelectedItemPosition()), String.valueOf(spinnerkelas.getSelectedItemPosition()), String.valueOf(spinnermapel.getSelectedItemPosition()), judul.getText().toString(), tanggaltugas.getText().toString(), file.getText().toString(), youtube.getText().toString(), sessionManager.getUserDetail().get("NAMALENGKAP"));
                uploadPDF(spinnermapel.getSelectedItem().toString(), spinnerkelas.getSelectedItem().toString(), spinnerjurusan.getSelectedItem().toString(), getIntent().getStringExtra("sekolah"),getIntent().getStringExtra("nip"), judul.getText().toString(), tanggaltugas.getText().toString(),nameoffile,urioffile, youtube.getText().toString());
            }
        });

        calendar = Calendar.getInstance();
        tanggaltugaslayout = (LinearLayout) findViewById(R.id.tanggaltugasayout) ;
        tanggaltugas = (TextView) findViewById(R.id.tanggaltugas);
        tanggaltugaslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Tambahtugas_Activity.this, Tambahtugas_Activity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        tanggaltugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Tambahtugas_Activity.this, Tambahtugas_Activity.this, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        namajurusan = new ArrayList<String>();
        kodejurusan = new ArrayList<String>();
        namakelas = new ArrayList<String>();
        kodekelas = new ArrayList<String>();
        namamapel = new ArrayList<String>();
        kodemapel = new ArrayList<String>();

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

        getJurusan(getIntent().getStringExtra("sekolah"));
    }

    private void inserttugas(String sekolah, String jurusan, String kelas, String mapel, String judul, String tanggal, String file, String youtube, String namalengkap) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://plazatanaman.com/sipren/tambahtugas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("success").equals("1")) {
                                Toast.makeText(Tambahtugas_Activity.this, "Berhasil Menambahkan Tugas", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Tambahtugas_Activity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Tambahtugas_Activity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sekolah",  sekolah);
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
        rQueue = Volley.newRequestQueue(Tambahtugas_Activity.this);
        rQueue.add(stringRequest);
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
        spinnerAdapter = new SpinnerAdapter(Tambahtugas_Activity.this, namamapel, kodemapel);
        spinnermapel.setAdapter(spinnerAdapter);
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
        spinnerAdapter = new SpinnerAdapter(Tambahtugas_Activity.this, namakelas, kodekelas);
        spinnerkelas.setAdapter(spinnerAdapter);
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
        spinnerAdapter = new SpinnerAdapter(Tambahtugas_Activity.this, namajurusan, kodejurusan);
        spinnerjurusan.setAdapter(spinnerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        txtfile.setText(displayName);
                        nameoffile = displayName;
                        urioffile = uri;
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(String kode_mapel, String kode_kelas, String kode_jurusan, String npsn, String nip, String judul, String deadline,final String pdfname, Uri pdffile, String video){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString("status").equals("true")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date myDate = new Date();
                    Map<String, String> params = new HashMap<>();
                    params.put("kode_mapel", kode_mapel);
                    params.put("kode_kelas", kode_kelas);
                    params.put("kode_jurusan", kode_jurusan);
                    params.put("npsn", npsn);
                    params.put("nip", nip);
                    params.put("judul", judul);
                    params.put("tgl_selesai", deadline);
                    params.put("video", video);
                    params.put("tgl_upload", timeStampFormat.format(myDate) + " 00:00:00");
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(Tambahtugas_Activity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
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
        tanggaltugas.setText(i + "-" + (i1+1) + "-" + i2);
    }
}