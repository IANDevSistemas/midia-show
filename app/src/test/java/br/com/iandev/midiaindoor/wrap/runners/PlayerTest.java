package br.com.iandev.midiaindoor.wrap.runners;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.BuildConfig;
import br.com.iandev.midiaindoor.core.interfaces.Playable;
import br.com.iandev.midiaindoor.dao.ConnectionFactoryTest;
import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Program;
import br.com.iandev.midiaindoor.model.ProgramContent;
import br.com.iandev.midiaindoor.model.Programming;
import br.com.iandev.midiaindoor.util.DateUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Lucas on 28/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 28/03/2017  Lucas
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PlayerTest extends ConnectionFactoryTest {
    private static final TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");

    private Device device;
    private List<Channel> channelList;
    private List<Programming> programmingList;
    private List<ProgramContent> programContentList;
    private List<Program> programList;
    private List<Content> contentList;
    private App app;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.app = App.getInstance(RuntimeEnvironment.application);

        JSONObject jsonObject = new JSONObject(new StringBuilder()
                .append(" { ")
                .append("").append(" device: { id: 1, status: A, channel: 1 } ")
                .append("").append(" ,channel: [ { id: 1, status: A } ] ")
                .append("").append(" ,program: [ ")
                .append("").append("").append(" { id: 1, status: A } ")
                .append("").append("").append(" ,{ id: 2, status: A } ")
                .append("").append("").append(" ,{ id: 3, status: A } ")
                .append("").append("").append(" ,{ id: 4, status: A } ")
                .append("").append("").append(" ,{ id: 5, status: A } ")
                .append("").append(" ] ")
                .append("").append(" ,programming: [ ")
                .append("").append("").append(" { id: 1, channel: 1, program: 1, daysOfWeek: '1000000', startTime: 00000000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 2, channel: 1, program: 2, daysOfWeek: '1000000', startTime: 00000000, endTime: 43199000 } ")
                .append("").append("").append(" ,{ id: 3, channel: 1, program: 3, daysOfWeek: '1000000', startTime: 43200000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 4, channel: 1, program: 4, daysOfWeek: '0100000', startTime: 00000000, endTime: 43199000 } ")
                .append("").append("").append(" ,{ id: 5, channel: 1, program: 5, daysOfWeek: '0100000', startTime: 00000000, endTime: 43199000 } ")
                .append("").append("").append(" ,{ id: 6, channel: 1, program: 2, daysOfWeek: '0110000', startTime: 43200000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 7, channel: 1, program: 5, daysOfWeek: '0010000', startTime: 00000000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 8, channel: 1, program: 3, daysOfWeek: '0010001', startTime: 00000000, endTime: 43199000 } ")
                .append("").append("").append(" ,{ id: 9, channel: 1, program: 4, daysOfWeek: '0010001', startTime: 43200000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 10, channel: 1, program: 4, daysOfWeek: '0001000', startTime: 00000000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 11, channel: 1, program: 5, daysOfWeek: '0001001', startTime: 43200000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 12, channel: 1, program: 1, daysOfWeek: '0000100', startTime: 00000000, endTime: 43199000 } ")
                .append("").append("").append(" ,{ id: 13, channel: 1, program: 5, daysOfWeek: '0000010', startTime: 00000000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 14, channel: 1, program: 1, daysOfWeek: '0000001', startTime: 00000000, endTime: 86399000 } ")
                .append("").append("").append(" ,{ id: 15, channel: 1, program: 2, daysOfWeek: '0000001', startTime: 00000000, endTime: 86399000 } ")
                .append("").append(" ] ")
                .append("").append(" ,programContent: [ ")
                .append("").append("").append(" { id: 1, program: 1, sequence: 1, content: 1 } ")
                .append("").append("").append(" ,{ id: 2, program: 1, sequence: 2, content: 2 } ")
                .append("").append("").append(" ,{ id: 3, program: 1, sequence: 3, content: 3 } ")
                .append("").append("").append(" ,{ id: 4, program: 2, sequence: 1, content: 4 } ")
                .append("").append("").append(" ,{ id: 5, program: 2, sequence: 2, content: 5 } ")
                .append("").append("").append(" ,{ id: 6, program: 2, sequence: 3, content: 6 } ")
                .append("").append("").append(" ,{ id: 7, program: 3, sequence: 1, content: 2 } ")
                .append("").append("").append(" ,{ id: 8, program: 3, sequence: 2, content: 5 } ")
                .append("").append("").append(" ,{ id: 9, program: 3, sequence: 3, content: 1 } ")
                .append("").append("").append(" ,{ id: 10, program: 4, sequence: 1, content: 4 } ")
                .append("").append("").append(" ,{ id: 11, program: 4, sequence: 2, content: 6 } ")
                .append("").append("").append(" ,{ id: 12, program: 4, sequence: 3, content: 4 } ")
                .append("").append("").append(" ,{ id: 13, program: 4, sequence: 4, content: 2 } ")
                .append("").append("").append(" ,{ id: 14, program: 4, sequence: 5, content: 4 } ")
                .append("").append("").append(" ,{ id: 15, program: 4, sequence: 6, content: 1 } ")
                .append("").append("").append(" ,{ id: 16, program: 5, sequence: 1, content: 3 } ")
                .append("").append("").append(" ,{ id: 17, program: 5, sequence: 2, content: 6 } ")
                .append("").append(" ] ")
                .append("").append(" ,content: [ ")
//                .append("").append("").append(" { id: 2, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: 1531353600000 } ")
                .append("").append("").append(" { id: 1, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: ").append(DateUtil.parse("2017-01-01", timeZone).getTime()).append(", endDate: ").append(DateUtil.parse("2017-01-07", timeZone).getTime()).append(" } ")
                .append("").append("").append(" ,{ id: 2, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: ").append(DateUtil.parse("2017-01-02", timeZone).getTime()).append(", endDate: ").append(DateUtil.parse("2017-01-07", timeZone).getTime()).append(" } ")
                .append("").append("").append(" ,{ id: 3, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: ").append(DateUtil.parse("2017-01-01", timeZone).getTime()).append(", endDate: ").append(DateUtil.parse("2017-01-07", timeZone).getTime()).append(" } ")
                .append("").append("").append(" ,{ id: 4, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: ").append(DateUtil.parse("2017-01-01", timeZone).getTime()).append(", endDate: ").append(DateUtil.parse("2017-01-07", timeZone).getTime()).append(" } ")
                .append("").append("").append(" ,{ id: 5, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: ").append(DateUtil.parse("2017-01-01", timeZone).getTime()).append(", endDate: ").append(DateUtil.parse("2017-01-07", timeZone).getTime()).append(" } ")
                .append("").append("").append(" ,{ id: 6, status: A, lastUpdateDate: 0, updateInterval: -1, updateToleranceInterval: -1, startDate: ").append(DateUtil.parse("2017-01-01", timeZone).getTime()).append(", endDate: ").append(DateUtil.parse("2017-01-06", timeZone).getTime()).append(" } ")
                .append("").append(" ] ")
                .append(" } ")
                .toString());


        device = new Device(null).parse(jsonObject.getJSONObject("device"));
        channelList = new Channel(null).parseList(jsonObject.getJSONArray("channel"));
        programList = new Program(null).parseList(jsonObject.getJSONArray("program"));
        programmingList = new Programming(null).parseList(jsonObject.getJSONArray("programming"));
        programContentList = new ProgramContent(null).parseList(jsonObject.getJSONArray("programContent"));
        contentList = new Content(null).parseList(jsonObject.getJSONArray("content"));
    }

    @Test
    public void testParse() throws Exception {
        assertFalse(device == null);
        assertFalse(channelList == null);
        assertEquals(1, channelList.size());
        assertFalse(programList == null);
        assertEquals(5, programList.size());
        assertFalse(programmingList == null);
        assertEquals(15, programmingList.size());
        assertFalse(programContentList == null);
        assertEquals(17, programContentList.size());
        assertFalse(contentList == null);
        assertEquals(6, contentList.size());
    }


    @Test
    public void getPlayableList1() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(5, playableList.size());
        int idx = -1;
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList2() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(4, playableList.size());
        int idx = -1;
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList3() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-02 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(8, playableList.size());
        int idx = -1;

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList4() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-02 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(3, playableList.size());
        int idx = -1;

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList5() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-03 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(5, playableList.size());
        int idx = -1;

        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList6() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-03 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(11, playableList.size());
        int idx = -1;

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList7() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-04 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(6, playableList.size());
        int idx = -1;

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList8() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-04 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(8, playableList.size());
        int idx = -1;

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList9() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-05 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(3, playableList.size());
        int idx = -1;

        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList10() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-05 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(0, playableList.size());
    }

    @Test
    public void getPlayableList11() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-06 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(2, playableList.size());
        int idx = -1;

        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList12() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-06 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(2, playableList.size());
        int idx = -1;

        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(6L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList13() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-07 00:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(8, playableList.size());
        int idx = -1;

        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
    }

    @Test
    public void getPlayableList14() throws Exception {
        List<Playable> playableList = new Player(getApp()).getPlayableList(
                DateUtil.parse("2017-01-07 12:00:00", "yyyy-MM-dd HH:mm:ss", timeZone), timeZone,
                device, channelList, programList, programmingList, programContentList, contentList);

        assertEquals(11, playableList.size());
        int idx = -1;

        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(5L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(2L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(4L, (long) ((Content) playableList.get(++idx)).getId());
        assertEquals(1L, (long) ((Content) playableList.get(++idx)).getId());

        assertEquals(3L, (long) ((Content) playableList.get(++idx)).getId());
    }

    public App getApp() {
        return app;
    }
}