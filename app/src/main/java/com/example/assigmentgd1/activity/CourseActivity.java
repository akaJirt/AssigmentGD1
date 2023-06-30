package com.example.assigmentgd1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.assigmentgd1.R;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView btnDangKy = findViewById(R.id.btnDangKyKhoaHoc);
        TextView btnDangKy2 = findViewById(R.id.btnDangKyKhoaHoc2);


        btnDangKy.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DangKyMonHocActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAll", true);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnDangKy2.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DangKyMonHocActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAll", false);
            intent.putExtras(bundle);
            startActivity(intent);
        });


    }
}