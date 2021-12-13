package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText nip, nama, nuptk, jenis_kelamin, alamat, no_hp, status_pegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Tools.setSystemBarColor(this, R.color.colorPrimary);
        nama = findViewById(R.id.namalengkap);
        nuptk = findViewById(R.id.nuptk);
        jenis_kelamin = findViewById(R.id.jeniskelamin);
        alamat = findViewById(R.id.alamat);
        no_hp = findViewById(R.id.nohp);
        status_pegawai = findViewById(R.id.statuspegawai);
        nip = findViewById(R.id.nip);

        ImageView back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView next = findViewById(R.id.buttonnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nip.getText().toString().isEmpty()) {
                    nip.setError("NIP is required");
                    nip.requestFocus();
                    return;
                }
                if (nama.getText().toString().isEmpty()) {
                    nama.setError("Nama Lengkap is required");
                    nama.requestFocus();
                    return;
                }
                if (nuptk.getText().toString().isEmpty()) {
                    nuptk.setError("NUPTK is required");
                    nuptk.requestFocus();
                    return;
                }
                if (jenis_kelamin.getText().toString().isEmpty()) {
                    jenis_kelamin.setError("Jenis Kelamin is required");
                    jenis_kelamin.requestFocus();
                    return;
                }
                if (alamat.getText().toString().isEmpty()) {
                    alamat.setError("Alamat is required");
                    alamat.requestFocus();
                    return;
                }
                if (no_hp.getText().toString().isEmpty()) {
                    no_hp.setError("No HP is required");
                    no_hp.requestFocus();
                    return;
                }
                if (status_pegawai.getText().toString().isEmpty()) {
                    status_pegawai.setError("Status Pegawai is required");
                    status_pegawai.requestFocus();
                    return;
                }
                Intent i = new Intent(RegisterActivity.this, LastregisterActivity.class);
                i.putExtra("nip", nip.getText().toString());
                i.putExtra("nama", nama.getText().toString());
                i.putExtra("nuptk", nuptk.getText().toString());
                i.putExtra("jenis_kelamin", jenis_kelamin.getText().toString());
                i.putExtra("alamat", alamat.getText().toString());
                i.putExtra("no_hp", no_hp.getText().toString());
                i.putExtra("status_pegawai", status_pegawai.getText().toString());
                startActivity(i);
            }
        });
    }
}