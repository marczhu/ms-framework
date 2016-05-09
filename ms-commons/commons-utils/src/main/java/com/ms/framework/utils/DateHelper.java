package com.ms.framework.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mark.zhu on 2016/3/1.
 */
public class DateHelper {

    public static Date setTime(Date date, int hour, int minute, int second,int millisecond) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }else{
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, second);
            c.set(Calendar.MILLISECOND, millisecond);
            return c.getTime();
        }
    }
    public static Date toMaxTimeOfDay(Date date){
        return setTime(date,23,59,59,999);
    }
    public static Date toMinTimeOfDay(Date date){
        return setTime(date,0,0,0,0);
    }
}
