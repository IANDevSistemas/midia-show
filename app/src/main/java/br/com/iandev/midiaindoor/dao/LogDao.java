package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Log;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 28/12/2016.
 * Changes:
 * Version     Date        Responsable     Change
 * 28/12/2016  Lucas
 */

public class LogDao extends Dao<Log> {

    public LogDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "Log";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("date INTEGER");
        attributes.add("tag TEXT");
        attributes.add("event INTEGER");
        attributes.add("message INTEGER");
        attributes.add("json TEXT");

        return attributes;
    }

    @Override
    protected ContentValues getContentValues(Log entity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("date", SQLiteUtil.getDate(entity.getDate()));
        contentValues.put("tag", SQLiteUtil.getString(entity.getTag()));
        contentValues.put("event", SQLiteUtil.getInteger(entity.getEvent()));
        contentValues.put("message", SQLiteUtil.getInteger(entity.getMessage()));
        contentValues.put("json", SQLiteUtil.getString(entity.getJson()));

        return contentValues;
    }

    @Override
    protected Log getEntity(Cursor cursor) {
        Log entity = new Log(null);

        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setDate(SQLiteUtil.getDate(cursor, "date"));
        entity.setTag(SQLiteUtil.getString(cursor, "tag"));
        entity.setEvent(SQLiteUtil.getInteger(cursor, "event"));
        entity.setMessage(SQLiteUtil.getInteger(cursor, "message"));
        entity.setJson(SQLiteUtil.getString(cursor, "json"));

        return entity;
    }
}
