package com.example.demo.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());



    }



}
