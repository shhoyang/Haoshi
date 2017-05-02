package com.haoshi.hao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Haoshi
 */
public class DateUtils {
    
    /**
     * 时分秒转换成秒
     */
    public static int getSecondTime(String time) {
        String[] arr = time.split(":");
        if (arr.length <= 3) {
            int second = 0;
            if (arr.length > 0) {
                second += 3600 * Integer.valueOf(arr[0]);
            }
            if (arr.length > 1) {
                second += 60 * Integer.valueOf(arr[1]);
            }
            if (arr.length > 2) {
                second += Integer.valueOf(arr[2]);
            }
            return second;
        }
        return -1;
    }

    /**
     * 当天的秒转换成时分秒
     */
    public static String second2Time(int second) {
        Date date = new Date();
        date.setTime(1000 * 60 * 60 * 16 + 1000 * second);
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * 获取当天的秒
     */
    public static int getNowSecondTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return hour * 3600 + minute * 60 + second;
    }

    /**
     * 获取时间
     */
    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取指定格式的时间
     */
    public static String getTime(String model) {
        return new SimpleDateFormat(model).format(new Date());
    }

    /**
     * 获取second后的那个时间
     */
    public static String getDelayTime(int second) {
        Date date = new Date();
        date.setTime(System.currentTimeMillis() + 1000 * second);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 日期转换成毫秒
     */
    public static long time2Long(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millisecond = 0;
        try {
            millisecond = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millisecond;
    }

    /**
     * 日期转换成毫秒
     */
    public static long date2Long(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long millisecond = 0;
        try {
            millisecond = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millisecond;
    }

    /**
     * 在时间段之间(天)
     */
    public static boolean isDateBetween(String beginDate, String endDate) {
        long l = System.currentTimeMillis();
        return l >= date2Long(beginDate) && l <= date2Long(endDate);
    }

    /**
     * 在时间段之间(时)
     */
    public static boolean isTimeBetween(String beginTime, String endTime) {
        int nowSecond = getNowSecondTime();
        return nowSecond >= getSecondTime(beginTime) && nowSecond <= getSecondTime(endTime);
    }

    /**
     * 在时间段之间(时)
     */
    public static boolean isTimeBetween(int beginTime, int endTime) {
        int nowSecond = getNowSecondTime();
        return nowSecond >= beginTime && nowSecond <= endTime;
    }
}
