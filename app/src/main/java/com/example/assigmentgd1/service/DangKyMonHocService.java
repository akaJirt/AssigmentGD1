package com.example.assigmentgd1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.assigmentgd1.DAO.DKMonHocDAO;
import com.example.assigmentgd1.model.MonHoc;

import java.util.ArrayList;

public class DangKyMonHocService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        boolean isAll = bundle.getBoolean("isAll");

        DKMonHocDAO dkMonHocDAO = new DKMonHocDAO(this);
        ArrayList< MonHoc > list = dkMonHocDAO.getAllMonHoc(id, isAll);
        Intent intentBR = new Intent();
        Bundle bundleBR = new Bundle();
        bundleBR.putBoolean("check", true);
        bundleBR.putSerializable("list", list);
        intentBR.putExtras(bundleBR);
        intentBR.setAction("DangKyMonHocService");
        sendBroadcast(intentBR);


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
