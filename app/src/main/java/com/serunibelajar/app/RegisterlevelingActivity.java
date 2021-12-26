package com.serunibelajar.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterlevelingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerleveling);

        Tools.setSystemBarColor(this, R.color.colortop);

        Button btn_siswa = findViewById(R.id.btn_siswa);
        btn_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterlevelingActivity.this, RegistersiswaActivity.class));
            }
        });

        Button btn_guru = findViewById(R.id.btn_guru);
        btn_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterlevelingActivity.this, RegisterguruActivity.class));
            }
        });

        Button btn_orantua = findViewById(R.id.btn_orangtua);
        btn_orantua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterlevelingActivity.this, Registerorangtua_Activity.class));
            }
        });
    }
}