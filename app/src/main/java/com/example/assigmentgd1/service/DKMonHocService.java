package com.example.assigmentgd1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.assigmentgd1.DAO.DKMonHocDAO;
import com.example.assigmentgd1.model.MonHoc;

import java.util.ArrayList;

public class DKMonHocService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id", -1);
        String code = bundle.getString("code", "");
        int isRegister = bundle.getInt("isRegister", -1);
        boolean isAll = bundle.getBoolean("isAll");
        boolean check ;
        DKMonHocDAO dkMonHocDAO = new DKMonHocDAO(this);
        if(isRegister == id ){
            check = dkMonHocDAO.huyDangKyMonHoc(id, code);
        }else{
            check = dkMonHocDAO.dangKyMonHoc(id, code);
        }
        ArrayList<MonHoc> list = new ArrayList<>();
        if(check){
            list = dkMonHocDAO.getAllMonHoc(id, isAll);
        }
        Intent intentBR = new Intent();
        Bundle bundleBR = new Bundle();
        bundleBR.putBoolean("check", check);
        bundleBR.putSerializable("list", list);
        intentBR.putExtras(bundleBR);
        intentBR.setAction("DKMonHocService");
        sendBroadcast(intentBR);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
