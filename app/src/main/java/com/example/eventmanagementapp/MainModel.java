package com.example.eventmanagementapp;

public class MainModel{
    String b_name, c_time,  d_date, e_loc, f_type,  g_people;

    MainModel(){

    }
    public MainModel(String b_name, String c_time, String d_date, String e_loc, String f_type, String g_people) {
        this.b_name = b_name;
        this.c_time = c_time;
        this.d_date = d_date;
        this.e_loc = e_loc;
        this.f_type = f_type;
        this.g_people = g_people;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getD_date() {
        return d_date;
    }

    public void setD_date(String d_date) {
        this.d_date = d_date;
    }

    public String getE_loc() {
        return e_loc;
    }

    public void setE_loc(String e_loc) {
        this.e_loc = e_loc;
    }

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public String getG_people() {
        return g_people;
    }

    public void setG_people(String g_people) {
        this.g_people = g_people;
    }
}

