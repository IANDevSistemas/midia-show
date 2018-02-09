package br.com.iandev.midiaindoor.model;

import android.support.test.runner.AndroidJUnitRunner;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.TimeZone;

import br.com.iandev.midiaindoor.BuildConfig;
import br.com.iandev.midiaindoor.util.DateUtil;
import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.util.JSONUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lucas on 25/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 25/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProgrammingTest extends AndroidJUnitRunner {
    @Test
    public void parse() throws Exception {

        JSONObject jsonObject = new JSONObject();

        Long id = 1L;
        jsonObject.put("id", id);
        Channel channel = new Channel(2L);
        jsonObject.put("channel", JSONUtil.getModel(channel));
        Program program = new Program(3L);
        jsonObject.put("program", JSONUtil.getModel(program));
        String daysOfWeek = "DAYSOFWEEK";
        jsonObject.put("daysOfWeek", daysOfWeek);
        Long startTime = 4L;
        jsonObject.put("startTime", startTime);
        Long endTime = 5L;
        jsonObject.put("endTime", endTime);

        Programming model = new Programming(null).parse(jsonObject);

        assertEquals(id, model.getId());
        assertEquals(channel.getId(), model.getChannel().getId());
        assertEquals(program.getId(), model.getProgram().getId());
        assertEquals(daysOfWeek, model.getDaysOfWeek());
        assertEquals(startTime, model.getStartTime());
        assertEquals(endTime, model.getEndTime());

    }

    @Test
    public void mustPlay() throws Exception {
        TimeZone timeZone = TimeZone.getDefault();
        Programming programming = new Programming(null);

        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("111111");
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("1111111");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));


        programming.setDaysOfWeek("0000000");
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("0111111");
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("1011111");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("1101111");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));


        programming.setDaysOfWeek("1110111");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("1111011");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));

        programming.setDaysOfWeek("1111101");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));


        programming.setDaysOfWeek("1111110");
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-03", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-04", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-05", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-06", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-07", timeZone), timeZone));


        programming.setDaysOfWeek("1110001");
        programming.setStartTime(IntervalUtil.parse("00:00:00"));
        programming.setEndTime(IntervalUtil.parse("12:00:00"));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-02 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-03 12:00:01", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-04 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-05 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));
        assertFalse(programming.mustPlay(DateUtil.parse("2017-01-06 12:00:01", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));
        assertTrue(programming.mustPlay(DateUtil.parse("2017-01-07 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone));


    }

}