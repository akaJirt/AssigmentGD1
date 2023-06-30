package com.example.assigmentgd1.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MonHoc implements Serializable {
    private String code;
    private String name;
    private String teacher;

    private int isRegister;

    private ArrayList<ThongTin> listThongTin;

    public ArrayList<ThongTin> getListThongTin() {
        return listThongTin;
    }

    public void setListThongTin(ArrayList<ThongTin> listThongTin) {
        this.listThongTin = listThongTin;
    }

    public MonHoc() {
    }

    public MonHoc(String code, String name, String teacher, int isRegister, ArrayList<ThongTin> listThongTin) {
        this.code = code;
        this.name = name;
        this.teacher = teacher;
        this.isRegister = isRegister;
        this.listThongTin = listThongTin;
    }

    public MonHoc(String code, String name, String teacher) {
        this.code = code;
        this.name = name;
        this.teacher = teacher;
    }

    public MonHoc(String code, String name, String teacher, int isRegister) {
        this.code = code;
        this.name = name;
        this.teacher = teacher;
        this.isRegister = isRegister;
    }

    public int getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(int isRegister) {
        this.isRegister = isRegister;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
