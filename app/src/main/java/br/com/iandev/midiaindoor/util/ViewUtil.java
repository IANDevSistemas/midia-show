package br.com.iandev.midiaindoor.util;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Lucas on 09/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 09/04/2017  Lucas
 */

public class ViewUtil {

    public static String getString(Object value) {
        return value != null ? value.toString() : "";
    }

//    public static String getString(TimeZone value) {
//        return value != null ? value.getDisplayName() : "";
//    }

    public static String getString(Date value) {
        return getString(value, null);
    }

    public static String getString(Date value, TimeZone timeZone) {
        return value != null ? DateUtil.fullFormat(value, timeZone != null ? timeZone : TimeZone.getDefault()) : "";
    }

    public static String getInterval(Long value) {
        return value != null ? IntervalUtil.format(value) : "";
    }
}
