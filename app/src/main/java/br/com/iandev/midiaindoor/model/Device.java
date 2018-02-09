package br.com.iandev.midiaindoor.model;

import org.json.JSONObject;

import java.util.Date;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.core.interfaces.Updateble;
import br.com.iandev.midiaindoor.core.interfaces.UpdatebleException;
import br.com.iandev.midiaindoor.util.JSONUtil;

/**
 * Created by Lucas on 07/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 07/12/2016  Lucas
 */

public class Device extends Model<Device> implements Updateble {
    private String description;
    private Character status;

    private Person person;
    private Channel channel;

    private String code;
    private TimeZone timeZone;

    private Long updateInterval;
    private Long updateToleranceInterval;

    private Date lastUpdateDate;
    private Date lastUpdateAttemptDate;

    private Date lastAuthenticationDate;
    private Long tokenExpirationInterval;
    private String token;

    public Device(Long id) {
        super(id);
    }

    @Override
    public Device parse(JSONObject jsonObject) {
        Device model = new Device(null);
        try {
            model.setId(JSONUtil.getLong(jsonObject, "id"));
            model.setDescription(JSONUtil.getString(jsonObject, "description"));
            model.setStatus(JSONUtil.getCharacter(jsonObject, "status"));

            model.setPerson(new Person(JSONUtil.getLong(jsonObject, "person")));
            model.setChannel(new Channel(JSONUtil.getLong(jsonObject, "channel")));

            model.setCode(JSONUtil.getString(jsonObject, "code"));
            model.setTimeZone(JSONUtil.getTimeZone(jsonObject, "timeZone"));

            model.setUpdateInterval(JSONUtil.getLong(jsonObject, "updateInterval"));
            model.setUpdateToleranceInterval(JSONUtil.getLong(jsonObject, "updateToleranceInterval"));

            model.setLastUpdateDate(JSONUtil.getDate(jsonObject, "lastUpdateDate"));
            model.setLastUpdateDate(JSONUtil.getDate(jsonObject, "lastUpdateAttemptDate"));

            model.setLastAuthenticationDate(JSONUtil.getDate(jsonObject, "lastAuthenticationDate"));
            model.setTokenExpirationInterval(JSONUtil.getLong(jsonObject, "tokenExpirationInterval"));
            model.setToken(JSONUtil.getString(jsonObject, "token"));

        } catch (Exception ex) {
            // Silence is golden
        }
        return model;
    }

    @Override
    public Date getNextUpdateDate() throws UpdatebleException {

        if (this.getLastUpdateDate() == null) {
            throw new UpdatebleException("lastUpdateDate must not be null");
        }

        Long updateInterval = this.getUpdateInterval();
        if (updateInterval == null || updateInterval < 0L) {
            updateInterval = 0L;
        }

        return new Date(this.getLastUpdateDate().getTime() + updateInterval);
    }

    @Override
    public boolean mustUpdate(Date date) {
        boolean mustUpdate = true;
        try {
            mustUpdate = this.getNextUpdateDate().getTime() <= date.getTime();
        } catch (Exception ex) {
            // Silence is golden
        }
        return mustUpdate;
    }

    @Override
    public boolean outOfDate(Date date) {
        Long updateToleranceInterval = this.getUpdateToleranceInterval();

        if (updateToleranceInterval == null) {
            updateToleranceInterval = 0L;
        }

        boolean outOfDate;
        try {
            outOfDate = this.getNextUpdateDate().getTime() + updateToleranceInterval < date.getTime();
        } catch (Exception ex) {
            return true;
        }

        return outOfDate && updateToleranceInterval != -1L;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public Long getUpdateInterval() {
        return updateInterval;
    }

    @Override
    public void setUpdateInterval(Long updateInterval) {
        this.updateInterval = updateInterval;
    }

    @Override
    public Long getUpdateToleranceInterval() {
        return updateToleranceInterval;
    }

    @Override
    public void setUpdateToleranceInterval(Long updateToleranceInterval) {
        this.updateToleranceInterval = updateToleranceInterval;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    @Override
    public Date getLastUpdateAttemptDate() {
        return lastUpdateAttemptDate;
    }

    @Override
    public void setLastUpdateAttemptDate(Date lastUpdateAttemptDate) {
        this.lastUpdateAttemptDate = lastUpdateAttemptDate;
    }


    public Date getLastAuthenticationDate() {
        return lastAuthenticationDate;
    }

    public void setLastAuthenticationDate(Date lastAuthenticationDate) {
        this.lastAuthenticationDate = lastAuthenticationDate;
    }

    public Long getTokenExpirationInterval() {
        return tokenExpirationInterval;
    }

    public void setTokenExpirationInterval(Long tokenExpirationInterval) {
        this.tokenExpirationInterval = tokenExpirationInterval;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format("%d - %s", this.getId(), this.getDescription());
    }
}
