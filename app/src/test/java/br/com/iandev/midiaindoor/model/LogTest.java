package br.com.iandev.midiaindoor.model;

import android.support.test.runner.AndroidJUnitRunner;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import br.com.iandev.midiaindoor.BuildConfig;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 25/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 25/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogTest extends AndroidJUnitRunner {
    @Test
    public void parse() throws Exception {
        JSONObject jsonObject = new JSONObject();

        Long id = 1L;
        jsonObject.put("id", id);
        Date date = new Date();
        jsonObject.put("date", date.getTime());
        String tag = "tag";
        jsonObject.put("tag", tag);
        Integer event = 1;
        jsonObject.put("event", event);
        Integer message = 2;
        jsonObject.put("message", message);
        String json = "{}";
        jsonObject.put("json", json);

        Log model = new Log(null).parse(jsonObject);

        assertEquals(id, model.getId());
        assertEquals(date, model.getDate());
        assertEquals(tag, model.getTag());
        assertEquals(event, model.getEvent());
        assertEquals(message, model.getMessage());
        assertEquals(json, model.getJson());

    }

}