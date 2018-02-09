package br.com.iandev.midiaindoor.util;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Lucas on 17/12/2016.
 */

public class IntervalUtil {

    public static long parse(String value) {
        String[] values;
        long time = 0L;
        try {
            values = value.trim().split(":");
            try {
                time += Long.valueOf(values[0]) * 60L * 60L * 1000L;
            } catch (Exception e) {
//                e.printStackTrace();
            }

            try {
                time += Long.valueOf(values[1]) * 60L * 1000L;
            } catch (Exception e) {
//                e.printStackTrace();
            }

            try {
                time += Long.valueOf(values[2]) * 1000L;
            } catch (Exception e) {
//                e.printStackTrace();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return time;
    }

    public static String format(long time) {
        char signal = '+';
        if (time < 0L) {
            signal = '-';
            time *= -1L;
        }
        // milliseconds to seconds
        time /= 1000L;

        long hours = time / (60L * 60L);
        time -= hours * 60L * 60L;

        long minutes = time / 60L;
        time -= minutes * 60L;

        long seconds = time % 60L;

        return String.format(Locale.getDefault(), "%c%02d:%02d:%02d", signal, hours, minutes, seconds);
    }

    public static long trunc(long time, TimeZone timeZone) {
        return time - DateUtil.trunc(new Date(time), timeZone).getTime();
    }
}
