package br.com.iandev.midiaindoor.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.model.Model;

/**
 * Created by Lucas on 17/12/2016.
 */

public class JSONUtil {
    public static List<String> parseStringArray(String jsonString) {
        List<String> stringList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                stringList.add(jsonArray.getString(i));
            }
        } catch (JSONException ex) {
//            ex.printStackTrace();
        }
        return stringList;
    }

    public static String getString(JSONObject json, String name) {
        String value = null;
        try {
            value = json.getString(name);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return value;
    }

    public static Character getCharacter(JSONObject json, String name) {
        Character value = null;
        try {
            value = json.getString(name).charAt(0);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return value;
    }

    public static Integer getInteger(JSONObject json, String name) {
        Integer value = null;
        try {
            value = json.getInt(name);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return value;
    }

    public static Long getLong(JSONObject json, String name) {
        Long value = null;
        try {
            value = json.getLong(name);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return value;
    }

    public static Date getDate(JSONObject json, String name) {
        Date value = null;
        try {
            value = new Date(JSONUtil.getLong(json, name));
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return value;
    }

    public static Long getTime(JSONObject json, String name) {
        Long value = null;
        try {
            value = IntervalUtil.parse(getString(json, name));
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
        return value;
    }

    public static Long getModel(Model model) {
        return model != null ? model.getId() : null;
    }

    public static TimeZone getTimeZone(JSONObject json, String name) {
        String timeZone = JSONUtil.getString(json, name);
        return timeZone != null ? TimeZone.getTimeZone(timeZone) : null;
    }

    public static String getTimeZone(TimeZone timeZone) {
        return timeZone != null ? timeZone.getID() : null;
    }

    public static Long getInterval(JSONObject jsonObject, String name) {
        return IntervalUtil.parse(getString(jsonObject, name));
    }
}
