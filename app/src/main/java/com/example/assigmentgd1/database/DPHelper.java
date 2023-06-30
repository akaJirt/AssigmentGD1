package com.example.assigmentgd1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DPHelper extends SQLiteOpenHelper {
    public DPHelper(@Nullable Context context) {
        super(context, "dbStudent", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE NGUOIDUNG (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, FULLNAME TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE MONHOC (CODE TEXT PRIMARY KEY, NAME TEXT, TEACHER TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE THONGTIN(ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, DATE TEXT, ADDRESS, FOREIGN KEY(CODE) REFERENCES MONHOC(CODE))");
        sqLiteDatabase.execSQL("CREATE TABLE DANGKY(ID INTEGER, CODE TEXT)");
        sqLiteDatabase.execSQL("INSERT INTO NGUOIDUNG VALUES(1,'tridinh','123456','Trí Định'),(2,'minhtri','123abc123','Minh Trí')");
        sqLiteDatabase.execSQL("INSERT INTO MONHOC VALUES('MOB201','Android Nâng Cao','Nguyễn Trí Định'),('MOB306','React Native','Trần Anh Hùng'),('MOB2041','Dự Án Mẫu','Nguyễn Trí Định'),('MOB2042','Dự Án Mẫu','Trần Anh Hùng'),('MOB2043','Dự Án Mẫu','Nguyễn Trí Định'),('MOB2044','Dự Án Mẫu','Trần Anh Hùng'),('MOB2045','Dự Án Mẫu','Nguyễn Trí Định'),('MOB2046','Dự Án Mẫu','Trần Anh Hùng')");
        sqLiteDatabase.execSQL("INSERT INTO THONGTIN VALUES(1, 'MOB201', 'Ca 2 - 19/09/2022', 'T1011'),(2, 'MOB201', 'Ca 2 - 21/09/2022', 'T1011'),(3, 'MOB201', 'Ca 2 - 23/09/2022', 'T1011'),(4, 'MOB306', 'Ca 5 - 20/09/2022', 'F204'),(5, 'MOB306', 'Ca 5 - 22/09/2022', 'F204'),(6, 'MOB2041', 'Ca 1 - 20/09/2022', 'Online - https://meet.google.com/rku-beuk-wqu')");
        sqLiteDatabase.execSQL("INSERT INTO DANGKY VALUES(1, 'MOB201'),(1, 'MOB306'),(2, 'MOB2041')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 != i) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MONHOC");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THONGTIN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DANGKY");
            onCreate(sqLiteDatabase);
        }
    }
}
