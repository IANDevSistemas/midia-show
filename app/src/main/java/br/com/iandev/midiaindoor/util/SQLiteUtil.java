package br.com.iandev.midiaindoor.util;

import android.database.Cursor;

import java.util.Date;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.model.Model;

/**
 * Created by Lucas on 21/03/2017.
 */

public class SQLiteUtil {
    public static Date getDate(Cursor cursor, String field) {
        Long time = cursor.getLong(cursor.getColumnIndex(field));
        return time != null ? new Date(time) : null;
    }

    public static Long getDate(Date date) {
        return date != null ? date.getTime() : null;
    }

    public static Long getLong(Cursor cursor, String field) {
        return cursor.getLong(cursor.getColumnIndex(field));
    }

    public static Long getLong(Long value) {
        return value;
    }

    public static Integer getInteger(Cursor cursor, String field) {
        return cursor.getInt(cursor.getColumnIndex(field));
    }

    public static String getString(Cursor cursor, String field) {
        return cursor.getString(cursor.getColumnIndex(field));
    }

    public static String getCharacter(Character character) {
        return String.valueOf(character);
    }

    public static Character getCharacter(Cursor cursor, String field) {
        return getString(cursor, field).charAt(0);
    }

    public static Long getModel(Model model) {
        return model != null ? model.getId() : null;
    }

    public static String getString(String value) {
        return value;
    }

    public static Integer getInteger(Integer value) {
        return value;
    }

    public static String getTimeZone(TimeZone timeZone) {
        return timeZone != null ? timeZone.getID() : null;
    }

    public static TimeZone getTimeZone(Cursor cursor, String field) {
        String timeZone = getString(cursor, field);
        return timeZone != null ? TimeZone.getTimeZone(timeZone) : null;
    }

}
