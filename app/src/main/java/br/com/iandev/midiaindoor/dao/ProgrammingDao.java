package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Program;
import br.com.iandev.midiaindoor.model.Programming;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class ProgrammingDao extends Dao<Programming> {
    public ProgrammingDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "Programming";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("channel INTEGER");
        attributes.add("program INTEGER");
        attributes.add("daysOfWeek TEXT");
        attributes.add("startTime INTEGER");
        attributes.add("endTime INTEGER");
        return attributes;
    }

    @Override
    protected ContentValues getContentValues(Programming entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("channel", SQLiteUtil.getModel(entity.getChannel()));
        contentValues.put("program", SQLiteUtil.getModel(entity.getProgram()));
        contentValues.put("daysOfWeek", SQLiteUtil.getString(entity.getDaysOfWeek()));
        contentValues.put("startTime", SQLiteUtil.getLong(entity.getStartTime()));
        contentValues.put("endTime", SQLiteUtil.getLong(entity.getEndTime()));
        return contentValues;
    }

    @Override
    protected Programming getEntity(Cursor cursor) {
        Programming entity = new Programming(null);
        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setChannel(new Channel(SQLiteUtil.getLong(cursor, "channel")));
        entity.setProgram(new Program(SQLiteUtil.getLong(cursor, "program")));
        entity.setDaysOfWeek(SQLiteUtil.getString(cursor, "daysOfWeek"));
        entity.setStartTime(SQLiteUtil.getLong(cursor, "startTime"));
        entity.setEndTime(SQLiteUtil.getLong(cursor, "endTime"));
        return entity;
    }
}
