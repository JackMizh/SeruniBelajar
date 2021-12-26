package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class RegisteradminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinnerstatus, spinneragama, spinnerjk;
    String[] status = {"Status","Aktif","Tidak Aktif"};
    String[] agama = {"Agama","Islam", "Kristen Protestan", "Kristen Katolik", "Hindu", "Buddha", "Konghucu"};
    String[] jk = {"Jenis Kelamin", "Pria", "Wanita"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeradmin);

        Tools.setSystemBarColor(this, R.color.colortop);

        spinnerjk = (Spinner) findViewById(R.id.spinnerjk);
        spinnerjk.setOnItemSelectedListener(this);
        SpinnertextAdapter spinnerjkAdapter = new SpinnertextAdapter(getApplicationContext(), jk);
        spinnerjk.setAdapter(spinnerjkAdapter);

        spinneragama = (Spinner) findViewById(R.id.spinneragama);
        spinneragama.setOnItemSelectedListener(this);
        SpinnertextAdapter spinneragamaAdapter = new SpinnertextAdapter(getApplicationContext(), agama);
        spinneragama.setAdapter(spinneragamaAdapter);

        spinnerstatus = (Spinner) findViewById(R.id.spinnerstatus);
        spinnerstatus.setOnItemSelectedListener(this);
        SpinnertextAdapter spinnertextAdapter = new SpinnertextAdapter(getApplicationContext(), status);
        spinnerstatus.setAdapter(spinnertextAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), previllage[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}