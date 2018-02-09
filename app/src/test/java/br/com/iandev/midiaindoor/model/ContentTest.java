package br.com.iandev.midiaindoor.model;

import android.support.test.runner.AndroidJUnitRunner;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.iandev.midiaindoor.BuildConfig;
import br.com.iandev.midiaindoor.core.integration.IntegrationApi;
import br.com.iandev.midiaindoor.core.interfaces.UpdatebleException;
import br.com.iandev.midiaindoor.util.IntervalUtil;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;


/**
 * Created by Lucas on 24/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 24/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ContentTest extends AndroidJUnitRunner {
    @Test
    public void mustUpdate() throws Exception {
        Date date = new Date();

        Content content = new Content(null);
        assertFalse(content.mustUpdate(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime()));
        assertFalse(content.mustUpdate(date));
        content.setStatus('A');
        assertTrue(content.mustUpdate(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.mustUpdate(date));
        content.setStatus('A');
        assertTrue(content.mustUpdate(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() + IntervalUtil.parse("00:00:01")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.mustUpdate(date));
        content.setStatus('A');
        assertFalse(content.mustUpdate(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.mustUpdate(date));
        content.setStatus('A');
        assertTrue(content.mustUpdate(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        assertFalse(content.mustUpdate(date));
        content.setStatus('A');
        assertTrue(content.mustUpdate(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        assertFalse(content.mustUpdate(date));
        content.setStatus('A');
        assertFalse(content.mustUpdate(date));
    }

    @Test
    public void outOfDate() throws Exception {
        Date date = new Date();

        Content content = new Content(null);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:04"));
        assertTrue(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:00"));
        assertFalse(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        assertFalse(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        assertFalse(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:09"));
        assertFalse(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));

        content = new Content(null);
        content.setStatus('A');
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:09"));
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:00"));
        assertTrue(content.outOfDate(date));

        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.outOfDate(date));
    }

    @Test
    public void getNextUpdateDate() throws Exception {
        Date date = new Date();

        Content content = new Content(null);
        try {
            content.getNextUpdateDate();
            fail("If lastUpdateDate is null an UpdatebleException is expected");
        } catch (UpdatebleException ex) {
            assertEquals("lastUpdateDate must not be null", ex.getMessage());
        } catch (Exception ex) {
            fail("If lastUpdateDate is null an UpdatebleException is expected");
        }

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        try {
            content.getNextUpdateDate();
            fail("If updateInterval is null an UpdatebleException is expected");
        } catch (UpdatebleException ex) {
            assertEquals("updateInterval must be greater than 0", ex.getMessage());
        } catch (Exception ex) {
            fail("If updateInterval is null an UpdatebleException is expected");
        }


        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertEquals(date.getTime() - IntervalUtil.parse("00:00:05"), content.getNextUpdateDate().getTime());

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:15"));
        assertEquals(date.getTime() + IntervalUtil.parse("00:00:05"), content.getNextUpdateDate().getTime());
    }

    @Test
    public void canPlay() throws Exception {
        Date date = new Date();

        Content content = new Content(null);
        assertFalse(content.canPlay(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime()));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:05"));
        assertTrue(content.canPlay(date));
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:04"));
        assertFalse(content.canPlay(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() + IntervalUtil.parse("00:00:01")));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));
        content.setStatus('I');
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content = new Content(null);
        content.setStatus('A');
        assertFalse(content.canPlay(date));
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        assertTrue(content.canPlay(date));

        content.setStatus('I');
        content.setUpdateInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content.setStatus('I');
        content.setUpdateToleranceInterval(IntervalUtil.parse("00:00:05"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content.setStatus('I');
        content.setUpdateInterval(IntervalUtil.parse("00:00:04"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertFalse(content.canPlay(date));

        content.setStatus('I');
        content.setUpdateToleranceInterval(-1L);
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:10"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        assertFalse(content.canPlay(date));
        content.setStatus('A');
        assertTrue(content.canPlay(date));

        content = new Content(null);
        content.setLastUpdateDate(new Date(date.getTime() - IntervalUtil.parse("00:00:10")));
        content.setUpdateInterval(IntervalUtil.parse("00:00:11"));
        content.setStatus('A');
        assertTrue(content.canPlay(date));
        content.setStartDate(new Date(date.getTime() + IntervalUtil.parse("00:00:01")));
        assertFalse(content.canPlay(date));
        content.setStartDate(new Date(date.getTime()));
        assertTrue(content.canPlay(date));
        content.setEndDate(new Date(date.getTime() - IntervalUtil.parse("00:00:01")));
        assertFalse(content.canPlay(date));
        content.setEndDate(new Date(date.getTime()));
        assertTrue(content.canPlay(date));
    }

    @Test
    public void parse() throws Exception {
        JSONObject jsonObject = new JSONObject();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Long id = 1L;
        jsonObject.put("id", id);
        String description = "DESCRIPTION";
        jsonObject.put("description", description);
        Character status = 'A';
        jsonObject.put("status", status);

        Long durationInterval = 2L;
        jsonObject.put("durationInterval", durationInterval);
        Date lastPlaybackDate = dateFormat.parse("2017-01-03 12:20:20");
        jsonObject.put("lastPlaybackDate", lastPlaybackDate.getTime());

        Long updateInterval = 3L;
        jsonObject.put("updateInterval", updateInterval);
        Long updateToleranceInterval = 4L;
        jsonObject.put("updateToleranceInterval", updateToleranceInterval);
        Date lastUpdateDate = dateFormat.parse("2017-01-01 01:01:01");
        jsonObject.put("lastUpdateDate", lastUpdateDate.getTime());
        Date lastUpdateAttemptDate = dateFormat.parse("2018-03-01 01:05:01");
        jsonObject.put("lastUpdateAttemptDate", lastUpdateAttemptDate.getTime());

        Date startDate = dateFormat.parse("2017-01-02 02:02:02");
        jsonObject.put("startDate", startDate.getTime());
        Date endDate = dateFormat.parse("2017-01-03 03:03:03");
        jsonObject.put("endDate", endDate.getTime());

        IntegrationApi integrationApi;
        integrationApi = IntegrationApi.BDO;
        jsonObject.put("integrationApi", integrationApi.getName());
        String accessUrl = "ACCESS_URL";
        jsonObject.put("accessUrl", accessUrl);
        String hash = "HASH";
        jsonObject.put("hash", hash);
        String alias = "ALIAS";
        jsonObject.put("alias", alias);
        String indexFilePath = "INDEX_FILE_PATH";
        jsonObject.put("indexFilePath", indexFilePath);

        Content model = new Content(null).parse(jsonObject);

        assertEquals(id, model.getId());
        assertEquals(description, model.getDescription());
        assertEquals(status, model.getStatus());

        assertEquals(durationInterval, model.getDurationInterval());
        assertEquals(lastPlaybackDate, model.getLastPlaybackDate());

        assertEquals(updateInterval, model.getUpdateInterval());
        assertEquals(updateToleranceInterval, model.getUpdateToleranceInterval());
        assertEquals(lastUpdateDate, model.getLastUpdateDate());
        assertEquals(lastUpdateAttemptDate, model.getLastUpdateAttemptDate());

        assertEquals(startDate, model.getStartDate());
        assertEquals(endDate, model.getEndDate());

        assertEquals(integrationApi.getName(), model.getIntegrationApi().getName());
        assertEquals(accessUrl, model.getAccessUrl());
        assertEquals(hash, model.getHash());
        assertEquals(alias, model.getAlias());
        assertEquals(indexFilePath, model.getIndexFilePath());
    }

    @Test
    public void clearFiles() throws Exception {

    }

    @Test
    public void getPackageFile() throws Exception {

    }

    @Test
    public void getBaseDirectory() throws Exception {

    }

    @Test
    public void uncompressPackage() throws Exception {

    }

    @Test
    public void getURLForPlayer() throws Exception {
        Content content = new Content(0L);
        content.setIndexFilePath("index.html");
        content.setIntegrationApi(IntegrationApi.BDO);
        assertFalse(null == content.getURLForPlayer());
        assertTrue("url must start with 'file://'", content.getURLForPlayer().startsWith("file://"));
    }
}