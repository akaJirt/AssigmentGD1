package com.example.assigmentgd1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assigmentgd1.R;
import com.example.assigmentgd1.adapter.DangKyMonHocAdapter;
import com.example.assigmentgd1.model.MonHoc;
import com.example.assigmentgd1.service.DangKyMonHocService;

import java.util.ArrayList;

public class DangKyMonHocActivity extends AppCompatActivity {
    RecyclerView rcvDangKyMonHoc;

    ArrayList<MonHoc> list;

    int id;

    IntentFilter intentFilter;

    boolean isAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_mon_hoc);
        rcvDangKyMonHoc = findViewById(R.id.rcvDanhSachMonHoc);
        TextView textView = findViewById(R.id.txtTitle);


        intentFilter = new IntentFilter();
        intentFilter.addAction("DangKyMonHocService");
        intentFilter.addAction("DKMonHocService");
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        isAll = bundle.getBoolean("isAll", true);
        if (isAll) {
            textView.setText("Danh sách môn học");
        } else {
            textView.setText("Danh sách môn học đã đăng ký");
        }
        intent = new Intent(DangKyMonHocActivity.this, DangKyMonHocService.class);
        bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putBoolean("isAll", isAll);
        intent.putExtras(bundle);
        startService(intent);

    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDangKyMonHoc.setLayoutManager(linearLayoutManager);
        DangKyMonHocAdapter dangKyMonHocAdapter = new DangKyMonHocAdapter(this, list, id, isAll);
        rcvDangKyMonHoc.setAdapter(dangKyMonHocAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            switch (intent.getAction()) {
                case "DangKyMonHocService":
                case "DKMonHocService":
                    Bundle bundle = intent.getExtras();
                    boolean check = bundle.getBoolean("check", true);
                    if (check) {
                        list = (ArrayList<MonHoc>) bundle.getSerializable("list");
                        loadData();
                    } else {
                        Toast.makeText(DangKyMonHocActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}