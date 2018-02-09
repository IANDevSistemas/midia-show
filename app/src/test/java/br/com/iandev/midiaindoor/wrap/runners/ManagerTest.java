package br.com.iandev.midiaindoor.wrap.runners;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.ConnectionFactoryTest;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.util.DateUtil;
import br.com.iandev.midiaindoor.wrap.facede.ContentFacede;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucas on 17/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 17/04/2017  Lucas
 */
public class ManagerTest extends ConnectionFactoryTest {

    private static TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
    private static JSONObject jsonObject = null;

    static {
        try {
            jsonObject = new JSONObject(new StringBuilder()
                    .append(" { ")
                    .append("").append(" content: [ ")
                    //                .append("").append("").append(" { id: 2, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: 1531353600000 } ")
                    .append("").append("").append(" { id: 1, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:02", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 2, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:04", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 3, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:07", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 4, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:09", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 5, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:03", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 6, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append("null").append(" } ")
                    .append("").append("").append(" ,{ id: 7, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:10", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 8, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:11", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 9, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:15", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 10, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:16", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 11, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:12", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 12, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:14", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append("").append(" ,{ id: 13, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, lastUpdateAttemptDate: ").append(DateUtil.parse("2017-01-01 00:00:13", "yyyy-MM-dd HH:mm:ss", timeZone).getTime()).append(" } ")
                    .append("").append(" ] ")
                    .append(" } ")
                    .toString());
        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

//    @Test
//    public void mustDownload() throws Exception {
//
//    }
//
//    @Test
//    public void mustUpdate() throws Exception {
//
//    }

    @Test
    public void newContentList() throws Exception {
        App app = App.getInstance(getContext());

        ContentFacede contentFacede = new ContentFacede(app);
        contentFacede.set(new Content(null).parseList(jsonObject.getJSONArray("content")));

        Manager manager = new Manager(App.getInstance(getContext()));

        List<Content> contentList = manager.newContentList();

        int idx = -1;
        assertEquals(Long.valueOf(6L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(1L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(5L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(2L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(3L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(4L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(7L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(8L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(11L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(13L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(12L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(9L), contentList.get(++idx).getId());
        assertEquals(Long.valueOf(10L), contentList.get(++idx).getId());

    }

}