package com.limh.agile.utils;

/**
 * Created by Administrator on 2017/6/7.
 *
 */

public class DateUtils {
    private static int daysOfMonth = 0; // 某月的天数

    // 判断是否为闰年
    private static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }

    // 得到某月有多少天数
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapYear(year)) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth = 28;
                }

        }
        return daysOfMonth;
    }
}
