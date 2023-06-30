package com.example.assigmentgd1.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assigmentgd1.database.DPHelper;

public class UserDAO {
    private DPHelper dpHelper;

    SharedPreferences sharedPreferences;

    public UserDAO(Context context) {
        dpHelper = new DPHelper(context);
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NGUOIDUNG WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("ID", cursor.getInt(0));
            editor.apply();
            return true;
        } else {
            return false;
        }
    }
}
