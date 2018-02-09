package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class DeviceDao extends Dao<Device> {

    public DeviceDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "DeviceFacede";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();

        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("description TEXT");
        attributes.add("status TEXT");

        attributes.add("person INTEGER");
        attributes.add("channel INTEGER");

        attributes.add("code TEXT");
        attributes.add("timeZone TEXT");

        attributes.add("updateInterval INTEGER");
        attributes.add("updateToleranceInterval INTEGER");

        attributes.add("lastUpdateDate INTEGER");
        attributes.add("lastUpdateAttemptDate INTEGER");

        attributes.add("lastAuthenticationDate INTEGER");
        attributes.add("tokenExpirationInterval INTEGER");
        attributes.add("token TEXT");
        return attributes;
    }

    @Override
    protected ContentValues getContentValues(Device entity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("description", SQLiteUtil.getString(entity.getDescription()));
        contentValues.put("status", SQLiteUtil.getCharacter(entity.getStatus()));

        contentValues.put("person", SQLiteUtil.getModel(entity.getPerson()));
        contentValues.put("channel", SQLiteUtil.getModel(entity.getChannel()));

        contentValues.put("code", SQLiteUtil.getString(entity.getCode()));
        contentValues.put("timeZone", SQLiteUtil.getTimeZone(entity.getTimeZone()));

        contentValues.put("updateInterval", SQLiteUtil.getLong(entity.getUpdateInterval()));
        contentValues.put("updateToleranceInterval", SQLiteUtil.getLong(entity.getUpdateToleranceInterval()));

        contentValues.put("lastUpdateDate", SQLiteUtil.getDate(entity.getLastUpdateDate()));
        contentValues.put("lastUpdateAttemptDate", SQLiteUtil.getDate(entity.getLastUpdateAttemptDate()));

        contentValues.put("lastAuthenticationDate", SQLiteUtil.getDate(entity.getLastAuthenticationDate()));
        contentValues.put("tokenExpirationInterval", SQLiteUtil.getLong(entity.getTokenExpirationInterval()));
        contentValues.put("token", SQLiteUtil.getString(entity.getToken()));

        return contentValues;
    }

    @Override
    protected Device getEntity(Cursor cursor) {
        Device entity = new Device(null);
        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setDescription(SQLiteUtil.getString(cursor, "description"));
        entity.setStatus(SQLiteUtil.getCharacter(cursor, "status"));

        entity.setPerson(new Person(SQLiteUtil.getLong(cursor, "person")));
        entity.setChannel(new Channel(SQLiteUtil.getLong(cursor, "channel")));

        entity.setCode(SQLiteUtil.getString(cursor, "code"));
        entity.setTimeZone(SQLiteUtil.getTimeZone(cursor, "timeZone"));

        entity.setUpdateInterval(SQLiteUtil.getLong(cursor, "updateInterval"));
        entity.setUpdateToleranceInterval(SQLiteUtil.getLong(cursor, "updateToleranceInterval"));

        entity.setLastUpdateDate(SQLiteUtil.getDate(cursor, "lastUpdateDate"));
        entity.setLastUpdateAttemptDate(SQLiteUtil.getDate(cursor, "lastUpdateAttemptDate"));

        entity.setLastAuthenticationDate(SQLiteUtil.getDate(cursor, "lastAuthenticationDate"));
        entity.setTokenExpirationInterval(SQLiteUtil.getLong(cursor, "tokenExpirationInterval"));
        entity.setToken(SQLiteUtil.getString(cursor, "token"));

        return entity;
    }
}









