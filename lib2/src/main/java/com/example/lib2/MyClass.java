package com.example.lib2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyClass {
    public static void main(String[] args) {
        String calender = "2020-11-2";
        String time = "23:29";
        String db_name = "lq";
        String sql ="CREATE TABLE"+db_name+"(ID INTERGER PRIMARY KEY AUTOINCREMENT, TEXT varchar(20),TIME varchar(30));";
//        String date = (new SimpleDateFormat("yyyy-MM-dd HH:mm").format("2018-06-19 23:10"));
        System.out.print(sql);
    }
}