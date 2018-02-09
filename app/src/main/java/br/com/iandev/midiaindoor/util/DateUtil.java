package br.com.iandev.midiaindoor.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Lucas on 15/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 15/12/2016  Lucas
 */

public class DateUtil {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, TimeZone timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(date);
    }

    public static String fullFormat(Date date, TimeZone timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(FULL_FORMAT);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(date);
    }

    public static Date parse(String value, TimeZone timeZone) {
        return parse(value, DEFAULT_FORMAT, timeZone);
    }

    public static Date parse(String dateString, String format, TimeZone timeZone) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setTimeZone(timeZone);
            date = dateFormat.parse(dateString);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return date;
    }

    public static int getDayOfWeek(long date, TimeZone timeZone) {
        return getDayOfWeek(new Date(date), timeZone);
    }

    public static int getDayOfWeek(Date date, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(timeZone);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static Date trunc(Date date, TimeZone timeZone) {
        return parse(format(date, timeZone), timeZone);
    }

//    public static long timeZoneDiff(TimeZone t1, TimeZone t2) {
//        Date date = new Date(IntervalUtil.parse("24:00:00"));
//        long x = t1.getRawOffset();
//        long diff = IntervalUtil.trunc(date.getTime(), t1) - IntervalUtil.trunc(date.getTime(), t2);
//        diff = -(date.getTime() + diff) % date.getTime();
//
//        long max = IntervalUtil.parse("12:00:00");
//        if(diff  > max) {
//            diff -= max;
//        }
//
//        return diff;
//    }
}
