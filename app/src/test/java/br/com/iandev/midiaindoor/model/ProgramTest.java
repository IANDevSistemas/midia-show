package br.com.iandev.midiaindoor.model;

import android.support.test.runner.AndroidJUnitRunner;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
public class ProgramTest extends AndroidJUnitRunner {
    @Test
    public void parse() throws Exception {
        JSONObject jsonObject = new JSONObject();

        Long id = 1L;
        jsonObject.put("id", id);
        String description = "DESCRIPTION";
        jsonObject.put("description", description);
        Character status = 'A';
        jsonObject.put("status", status);

        Program model = new Program(null).parse(jsonObject);

        assertEquals(id, model.getId());
        assertEquals(description, model.getDescription());
        assertEquals(status, model.getStatus());
    }

}