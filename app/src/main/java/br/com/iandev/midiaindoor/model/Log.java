package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import java.util.Date;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.util.DateUtil;
import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 28/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 28/12/2016  Lucas
 */

public class Log extends Model<Log> {
    private Date date;
    private String tag;
    private Integer event;
    private Integer message;
    private String json;

    public Log(Long id) {
        super(id);
    }

    @Override
    public Log parse(JSONObject jsonObject) {
        Log model = new Log(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setDate(JSONUtil.getDate(jsonObject, "date"));
            model.setTag(JSONUtil.getString(jsonObject, "tag"));
            model.setEvent(JSONUtil.getInteger(jsonObject, "event"));
            model.setMessage(JSONUtil.getInteger(jsonObject, "message"));
            model.setJson(JSONUtil.getString(jsonObject, "json"));
        } catch (Exception ex) {
        }
        return model;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return String.format(
                "#%d %s %s %s %s",
                getId(),
                DateUtil.format(getDate(), TimeZone.getDefault()),
                getEvent(),
                getMessage(),
                getJson()
        );
    }


}
