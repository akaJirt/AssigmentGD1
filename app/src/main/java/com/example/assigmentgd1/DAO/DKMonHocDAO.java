package com.example.assigmentgd1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assigmentgd1.database.DPHelper;
import com.example.assigmentgd1.model.MonHoc;
import com.example.assigmentgd1.model.ThongTin;

import java.util.ArrayList;

public class DKMonHocDAO {

    DPHelper dpHelper;

    public DKMonHocDAO(Context context) {
        dpHelper = new DPHelper(context);
    }

    public ArrayList<MonHoc> getAllMonHoc(int id, boolean isAll) {

        ArrayList<MonHoc> list = new ArrayList<>();
        Cursor cursor;
        if (isAll) {
            cursor = dpHelper.getReadableDatabase().rawQuery
                    ("SELECT mh.code, mh.name, mh.teacher, dk.id FROM MONHOC mh LEFT JOIN DANGKY dk ON mh.code = dk.code AND dk.id = ?", new String[]{String.valueOf(id)});
        } else {
            cursor = dpHelper.getReadableDatabase().rawQuery
                    ("SELECT mh.code, mh.name, mh.teacher, dk.id FROM MONHOC mh INNER JOIN DANGKY dk ON mh.code = dk.code WHERE dk.id = ?", new String[]{String.valueOf(id)});
        }
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new MonHoc(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), getThongTinMonHoc(cursor.getString(0))));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean dangKyMonHoc(int id, String code) {
        SQLiteDatabase sqLiteDatabase = dpHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("code", code);
        long check = sqLiteDatabase.insert("DANGKY", null, contentValues);
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean huyDangKyMonHoc(int id, String code) {
        SQLiteDatabase sqLiteDatabase = dpHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("DANGKY", "id = ? AND code = ?", new String[]{String.valueOf(id), code});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public ArrayList<ThongTin> getThongTinMonHoc(String code) {
        ArrayList<ThongTin> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dpHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT DATE, ADDRESS FROM THONGTIN WHERE code = ?", new String[]{code});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                String date = cursor.getString(0);
                String address = cursor.getString(1);
                list.add(new ThongTin(date, address));
            } while (cursor.moveToNext());
        }
        return list;
    }

}
