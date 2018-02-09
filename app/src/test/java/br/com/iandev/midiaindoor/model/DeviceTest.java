package br.com.iandev.midiaindoor.model;

import android.support.test.runner.AndroidJUnitRunner;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.BuildConfig;
import br.com.iandev.midiaindoor.core.interfaces.UpdatebleException;
import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.util.JSONUtil;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Lucas on 25/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 25/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DeviceTest extends AndroidJUnitRunner {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void parse() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = new JSONObject();

        Long id = 1L;
        jsonObject.put("id", id);
        String description = "DESCRIPTION";
        jsonObject.put("description", description);
        Character status = 'A';
        jsonObject.put("status", status);

        Person person = new Person(2L);
        jsonObject.put("person", JSONUtil.getModel(person));
        Channel channel = new Channel(3L);
        jsonObject.put("channel", JSONUtil.getModel(channel));

        String code = "CODE";
        jsonObject.put("code", code);

        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        jsonObject.put("timeZone", JSONUtil.getTimeZone(timeZone));

        Long updateInterval = 3L;
        jsonObject.put("updateInterval", updateInterval);
        Long updateToleranceInterval = 4L;
        jsonObject.put("updateToleranceInterval", updateToleranceInterval);

        Date lastUpdateDate = dateFormat.parse("2018-06-13 12:45:56");
        jsonObject.put("lastUpdateDate", lastUpdateDate.getTime());

        Date lastAuthenticationDate = dateFormat.parse("2019-07-15 02:25:58");
        jsonObject.put("lastAuthenticationDate", lastAuthenticationDate.getTime());
        Long tokenExpirationInterval = 5L;
        jsonObject.put("tokenExpirationInterval", tokenExpirationInterval);
        String token = "TOKEN";
        jsonObject.put("token", token);

        Device model = new Device(null).parse(jsonObject);

        assertEquals(id, model.getId());
        assertEquals(description, model.getDescription());
        assertEquals(status, model.getStatus());

        assertFalse(person.getId() == null);
        assertEquals(person.getId(), model.getPerson().getId());
        assertFalse(channel.getId() == null);
        assertEquals(channel.getId(), model.getChannel().getId());

        assertEquals(code, model.getCode());
        assertEquals(timeZone, model.getTimeZone());

        assertEquals(updateInterval, model.getUpdateInterval());
        assertEquals(updateToleranceInterval, model.getUpdateToleranceInterval());

        assertEquals(lastUpdateDate, model.getLastUpdateDate());

        assertEquals(lastAuthenticationDate, model.getLastAuthenticationDate());
        assertEquals(tokenExpirationInterval, model.getTokenExpirationInterval());
        assertEquals(token, model.getToken());
    }


    @Test
    public void getNextUpdateDate() throws Exception {
        Date date = new Date();

        Device device = new Device(null);
        try {
            device.getNextUpdateDate();
            fail("If lastUpdateDate is null an UpdatebleException is expected");
        } catch (UpdatebleException ex) {
            assertEquals("lastUpdateDate must not be null", ex.getMessage());
        } catch (Exception ex) {
            fail("If lastUpdateDate is null an UpdatebleException is expected");
        }

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        assertEquals(device.getLastUpdateDate().getTime(), device.getNextUpdateDate().getTime());

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertEquals(date.getTime() - IntervalUtil.parse("00:00:05"), device.getNextUpdateDate().getTime());

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:15"));
        assertEquals(date.getTime() + IntervalUtil.parse("00:00:05"), device.getNextUpdateDate().getTime());
    }

    @Test
    public void mustUpdate() throws Exception {
        Date date = new Date();

        Device device1 = new Device(null);
        assertTrue(device1.mustUpdate(date));

        Device device2 = new Device(null);
        device2.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device2.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertTrue(device2.mustUpdate(date));

        Device device3 = new Device(null);
        device3.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device3.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        assertTrue(device3.mustUpdate(date));

        Device device4 = new Device(null);
        device4.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device4.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        assertFalse(device4.mustUpdate(date));
    }

    @Test
    public void outOfDate() throws Exception {
        Date date = new Date();

        Device device = new Device(null);
        assertTrue(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        device.setUpdateToleranceInterval(IntervalUtil.parse("00:00:04"));
        assertTrue(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        device.setUpdateToleranceInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        device.setUpdateToleranceInterval(IntervalUtil.parse("00:00:00"));
        assertFalse(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        assertFalse(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        assertFalse(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:09"));
        assertTrue(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));

        device = new Device(null);
        device.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        device.setUpdateInterval(IntervalUtil.parse("00:00:09"));
        device.setUpdateToleranceInterval(IntervalUtil.parse("00:00:00"));
        assertTrue(device.outOfDate(date));

        device.setUpdateToleranceInterval(-1L);
        assertFalse(device.outOfDate(date));
    }

}