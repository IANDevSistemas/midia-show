package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import java.util.Date;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.util.DateUtil;
import br.com.iandev.midiaindoor.util.IntervalUtil;
import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class Programming extends Model<Programming> {
    private Channel channel;
    private Program program;
    private String daysOfWeek;
    private Long startTime;
    private Long endTime;

    public Programming(Long id) {
        super(id);
    }

    @Override
    public Programming parse(JSONObject jsonObject) {
        Programming model = new Programming(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setChannel(new Channel(JSONUtil.getLong(jsonObject, "channel")));
            model.setProgram(new Program(JSONUtil.getLong(jsonObject, "program")));
            model.setDaysOfWeek(JSONUtil.getString(jsonObject, "daysOfWeek"));
            model.setStartTime(JSONUtil.getInterval(jsonObject, "startTime"));
            model.setEndTime(JSONUtil.getInterval(jsonObject, "endTime"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public boolean mustPlay(Date date, TimeZone timeZone) {
        Long startTime = this.getStartTime();
        if (startTime == null) {
            startTime = IntervalUtil.parse("00:00:00");
        }

        Long endTime = this.getEndTime();
        if (endTime == null) {
            endTime = IntervalUtil.parse("23:59:59");
        }

        String daysOfWeek = this.getDaysOfWeek();
        if (daysOfWeek == null) {
            daysOfWeek = "1111111";
        }

        long time = IntervalUtil.trunc(date.getTime(), timeZone);
        return time >= startTime && time <= endTime && daysOfWeek.length() == 7 && Character.valueOf('1').equals(daysOfWeek.charAt(DateUtil.getDayOfWeek(date, timeZone) - 1));
    }


    @Override
    public String toString() {
        return String.format("%s - %d -> %d", this.getDaysOfWeek(), this.getStartTime(), this.getEndTime());
    }

}
