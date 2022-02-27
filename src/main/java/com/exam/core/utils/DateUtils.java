package com.exam.core.utils;

import com.exam.core.constant.OtherConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author
 */
public class DateUtils {

    private DateUtils() {}

    /**
     * 默认日期格式
     * @return
     */
    public static String newDateTime() {
        SimpleDateFormat sdf = OtherConstant.DEFAULT_DATETIME_FORMATTER;
        return sdf.format(new Date());
    }

    /**
     * 年月日
     * @return
     */
    public static String newDate() {
        SimpleDateFormat sdf = OtherConstant.DATE_FORMAT;
        return sdf.format(new Date());
    }

    /**
     * 指定日期格式
     * @return
     */
    public static String newDateByFormat(SimpleDateFormat format) {
        SimpleDateFormat sdf = format;
        return sdf.format(new Date());
    }

    /**
     * 计算日期相差天数
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Long diffTime(String startDate, String endDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long a = sdf.parse(endDate).getTime()-sdf.parse(startDate).getTime();
        return a/1000/60/60/24;
    }


    /**
     * 指定日期字符串加上 天数
     *
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static String addDate(String date, int day) {
        long time = 0;
        try {
            time = OtherConstant.DEFAULT_DATETIME_FORMATTER.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long second = day * 24 * 60 * 60 * 1000;
        time += second;
        return OtherConstant.DEFAULT_DATETIME_FORMATTER.format(new Date(time));
    }

    /**
     * 判断是否比当前时间大
     *
     * @return
     */
    public static boolean bigThanNow(String date) {

        DateFormat df = OtherConstant.DEFAULT_DATETIME_FORMATTER;
        try {
            Date dt1 = df.parse(date);
            Date dt2 = df.parse(newDateTime());
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

}
