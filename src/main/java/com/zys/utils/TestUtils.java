package com.zys.utils;


import java.text.SimpleDateFormat;

import java.util.*;

public class TestUtils {


    public static void main(String[] args) {
        String a="";
        String b="";
        for (int i = 0; i < 24; i++) {
            if(i<10){
                a = "0"+i;
                b = "0"+(i+1);
                if("010".equals(b)){
                    b = "10";
                }
            }else {
                a = i+"";
                b = i+1+"";
            }
            String startTime = "2023-03-13 "+a+":00:00";
            String endTime ="";
            if("24".equals(b)){
                endTime = "2023-03-14 00:00:00";
            }else {
                endTime = "2023-03-13 "+b+":00:00";
            }
            System.out.println("select count(1) cou from rpt_event_secure where event_time >= '"+startTime+"' and " +
                    "event_time<'"+endTime+"' union all");


        }
    }
}
