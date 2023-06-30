package com.example.assigmentgd1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.assigmentgd1.DAO.DKMonHocDAO;
import com.example.assigmentgd1.R;
import com.example.assigmentgd1.SocialNetworkActivity;
import com.example.assigmentgd1.TinTucActivity;
import com.example.assigmentgd1.database.DPHelper;
import com.example.assigmentgd1.model.MonHoc;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DKMonHocDAO DKMonHocDAO;

    ImageView imgLogo1, imgLogo2, imgLogo3, imgLogo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgLogo1 = findViewById(R.id.imgLogo);
        imgLogo2 = findViewById(R.id.imgLogo2);
        imgLogo3 = findViewById(R.id.imgLogo3);
        imgLogo4 = findViewById(R.id.imgLogo4);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imgLogo1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CourseActivity.class);
            startActivity(intent);
        });

        imgLogo2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        imgLogo3.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TinTucActivity.class);
            startActivity(intent);
        });

        imgLogo4.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SocialNetworkActivity.class);
            startActivity(intent);
        });
    }
}