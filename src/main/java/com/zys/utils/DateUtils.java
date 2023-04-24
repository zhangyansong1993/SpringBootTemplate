package com.zys.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    private final static String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowDateTime() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat(yyyyMMddHHmmss).format(calendar.getTime());
    }

    /**
     * 获取几天前/几天后
     * @param day
     * @return
     */
    public static String getDateTimeByDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat(yyyyMMddHHmmss).format(calendar.getTime());
    }
    /**
     * 获取几小时前/几小时后
     * @param hour
     * @return
     */
    public static String getDateTimeByMinute(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, hour);
        return new SimpleDateFormat(yyyyMMddHHmmss).format(calendar.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getDateTimeByMinute(-7));
    }

}
