package com.clb.commonlibrary.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xiaowang on 2018/3/20.
 * Time processing
 *
 */

public class DateUtil {

    private static SimpleDateFormat sf = null;

    /**
     * Get current time
     * df is the parameter of the time format to be passed in
     * @param df
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate(String df){
        Date d = new Date();
        sf = new SimpleDateFormat(df);
        return sf.format(d);
    }

    /**
     * longtime to string
     * @param time
     * @param df
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getLongToString(Long time, String df){
        Date d = new Date(time);
        sf = new SimpleDateFormat(df);
        return sf.format(d);
    }


    /**
     * datetime to String
     * @param date
     * @param df
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateToString(Date date, String df){
        sf = new SimpleDateFormat(df);
        return sf.format(date);
    }


    /**
     * Date to Long
     * @param date
     * @return
     */
    public static Long getDateToLong(Date date){
        return date.getTime();
    }


    /**
     * String to Long
     * @param time
     * @param df
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Long getStringToLong(String time, String df){
        sf = new SimpleDateFormat(df);
        Date d = new Date();
        try{
            d = sf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * String to Date
     * @param time
     * @param df
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getStringToDate(String time, String df){
        sf = new SimpleDateFormat(df);
        Date d = new Date();
        try {
            d = sf.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        return d;
    }


    /**
     * 返回当前月份日期位于周几
     * @param year
     *
     * @param month
     *
     * @return
     * 	日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongConstant")
    public static int getDayWeek(int year, int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 通过年份和月份 得到当月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
                    return 29;
                }else{
                    return 28;
                }
            default:
                return  -1;
        }
    }

    /**
     * 第一种方式
     * 将未指定格式的字符串转换成日期类型
     * @param date - 20151123083450 转为
     * @return Mon Nov 23 00:00:00 GMT+08:00 2015
     */
    public static Date parseStringToDate(String date) throws Exception {
        Date result;
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("()[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        SimpleDateFormat format = new SimpleDateFormat(parse, Locale.CHINA);
        result = format.parse(date);
        return result;
    }

    /**
     * 第二种方式
     * 将未指定格式的字符串转换成日期类型
     * @param date - 20151123 083450 或者 2018/10/13 12:30:40
     * @return Mon Nov 23 00:00:00 GMT+08:00 2015
     */
    public static Date parseStringToDate1(String date) throws Exception {
        Date result;
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        SimpleDateFormat format = new SimpleDateFormat(parse, Locale.CHINA);
        result = format.parse(date);
        return result;
    }






}
