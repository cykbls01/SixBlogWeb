package com.example.demo.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());



    }

    public static boolean compare(String time1,String time2) throws ParseException {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);

        int compareTo = date1.compareTo(date2);

        if(compareTo==-1)
            return  false;
        else return  true;


    }



}
