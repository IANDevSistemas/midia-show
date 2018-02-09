package br.com.iandev.midiaindoor.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Lucas on 24/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 24/03/2017  Lucas
 */
public class DateUtilTest {
    @Test
    public void toStringFormat() throws Exception {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT);

        timeZone = TimeZone.getDefault();
        dateFormat.setTimeZone(timeZone);
        assertEquals(dateFormat.format(date), DateUtil.format(date, timeZone));
    }

    @Test
    public void parseValid() throws Exception {

        TimeZone timeZone1 = TimeZone.getTimeZone("GMT");
        TimeZone timeZone2 = TimeZone.getTimeZone("Etc/GMT-7");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(timeZone1);
        String value = "2017-01-01";

        assertEquals(dateFormat.parse(value).getTime(), DateUtil.parse(value, timeZone1).getTime());
        assertNotEquals(dateFormat.parse(value).getTime(), DateUtil.parse(value, timeZone2).getTime());

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone1);
        value = "2017-01-01 00:00:00";

        assertEquals(dateFormat.parse(value).getTime(), DateUtil.parse(value, timeZone1).getTime());
        assertNotEquals(dateFormat.parse(value).getTime(), DateUtil.parse(value, timeZone2).getTime());
    }

    @Test
    public void notValid() throws Exception {
        String value = "20170101";
        assertEquals(null, DateUtil.parse(value, null));
    }

    @Test
    public void getDayOfWeek() throws Exception {
        TimeZone timeZone1 = TimeZone.getTimeZone("Etc/GMT-7");
        TimeZone timeZone2 = TimeZone.getTimeZone("GMT");

        Date sunday = DateUtil.parse("2017-01-01", timeZone1);
        assertEquals(1, DateUtil.getDayOfWeek(sunday, timeZone1));
        assertNotEquals(1, DateUtil.getDayOfWeek(sunday, timeZone2));

        Date monday = DateUtil.parse("2017-01-02", timeZone1);
        assertEquals(2, DateUtil.getDayOfWeek(monday, timeZone1));
        assertNotEquals(2, DateUtil.getDayOfWeek(monday, timeZone2));

        Date tuesday = DateUtil.parse("2017-01-03", timeZone1);
        assertEquals(3, DateUtil.getDayOfWeek(tuesday, timeZone1));
        assertNotEquals(3, DateUtil.getDayOfWeek(tuesday, timeZone2));

        Date wednesday = DateUtil.parse("2017-01-04", timeZone1);
        assertEquals(4, DateUtil.getDayOfWeek(wednesday, timeZone1));
        assertNotEquals(4, DateUtil.getDayOfWeek(wednesday, timeZone2));

        Date thursday = DateUtil.parse("2017-01-05", timeZone1);
        assertEquals(5, DateUtil.getDayOfWeek(thursday, timeZone1));
        assertNotEquals(5, DateUtil.getDayOfWeek(thursday, timeZone2));

        Date friday = DateUtil.parse("2017-01-06", timeZone1);
        assertEquals(6, DateUtil.getDayOfWeek(friday, timeZone1));
        assertNotEquals(6, DateUtil.getDayOfWeek(thursday, timeZone2));

        Date saturday = DateUtil.parse("2017-01-07", timeZone1);
        assertEquals(7, DateUtil.getDayOfWeek(saturday, timeZone1));
        assertNotEquals(7, DateUtil.getDayOfWeek(saturday, timeZone2));
    }

    @Test
    public void truncDateWithHour() throws Exception {
        TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+7");
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat1.setTimeZone(timeZone);

        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat2.setTimeZone(timeZone);

        Date date1 = dateFormat1.parse("2017-01-01");
        Date date2 = dateFormat2.parse("2017-01-01 18:41:32");

        assertNotEquals(date1.getTime(), date2.getTime());
        assertEquals(date1.getTime(), DateUtil.trunc(date2, timeZone).getTime());
    }

    @Test
    public void truncDateWithoutHour() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getDefault());

        Date date = dateFormat.parse("2017-01-01");
        assertEquals(date.getTime(), DateUtil.trunc(date, TimeZone.getDefault()).getTime());
    }

    @Test
    public void truncDateUtilAndIntervalUtil() throws Exception {
        TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+7");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);

        Date date = dateFormat.parse("2017-01-01 06:41:32");

        assertEquals(DateUtil.trunc(date, timeZone).getTime() + IntervalUtil.trunc(date.getTime(), timeZone), date.getTime());

        assertNotEquals(DateUtil.trunc(date, timeZone).getTime() + IntervalUtil.trunc(date.getTime(), TimeZone.getTimeZone("GMT")), date.getTime());
        assertNotEquals(DateUtil.trunc(date, TimeZone.getTimeZone("GMT")).getTime() + IntervalUtil.trunc(date.getTime(), timeZone), date.getTime());

        assertNotEquals(DateUtil.trunc(date, TimeZone.getTimeZone("GMT")).getTime(), DateUtil.trunc(date, timeZone).getTime());
        assertEquals(DateUtil.trunc(date, TimeZone.getTimeZone("GMT")).getTime() + IntervalUtil.parse("07:00:00"), DateUtil.trunc(date, timeZone).getTime());
        assertEquals(IntervalUtil.parse("00:00:00"), IntervalUtil.trunc(DateUtil.trunc(date, TimeZone.getTimeZone("GMT")).getTime(), TimeZone.getTimeZone("GMT")));

        assertEquals(IntervalUtil.parse("07:00:00"), IntervalUtil.trunc(DateUtil.trunc(date, timeZone).getTime(), TimeZone.getTimeZone("GMT")));
        assertEquals(IntervalUtil.parse("17:00:00"), IntervalUtil.trunc(DateUtil.trunc(date, TimeZone.getTimeZone("GMT")).getTime(), timeZone));
    }

    @Test
    public void truncDateUtil() throws Exception {
        TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+7");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);

        Date date = dateFormat.parse("2017-01-01 06:41:32");

        long time = DateUtil.trunc(date, TimeZone.getTimeZone("GMT")).getTime();
        time = DateUtil.trunc(new Date(time), timeZone).getTime();
        assertEquals(IntervalUtil.parse("24:00:00"), DateUtil.trunc(date, timeZone).getTime() - time);
    }

//    @Test
//    public void timeZoneDiff() throws Exception {
//        assertEquals("+00:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-12:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+12"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-11:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+11"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-10:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+10"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-09:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+9"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-08:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+8"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-07:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+7"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-06:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+6"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-05:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+5"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-04:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+4"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-03:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+3"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-02:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+2"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("-01:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+1"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+00:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT+0"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+00:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT0"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+00:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-0"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+01:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-1"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+02:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-2"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+03:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-3"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+04:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-4"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+05:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-5"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+06:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-6"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+07:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-7"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+08:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-8"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+09:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-9"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+10:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-10"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+11:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-11"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+12:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-12"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+13:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-13"), TimeZone.getString("Etc/GMT0"))));
//        assertEquals("+14:00:00", IntervalUtil.format(DateUtil.timeZoneDiff(TimeZone.getString("Etc/GMT-14"), TimeZone.getString("Etc/GMT0"))));
//    }

}