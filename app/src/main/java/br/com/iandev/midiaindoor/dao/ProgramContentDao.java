package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.model.Program;
import br.com.iandev.midiaindoor.model.ProgramContent;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class ProgramContentDao extends Dao<ProgramContent> {

    public ProgramContentDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "ProgramContent";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();

        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("content INTEGER");
        attributes.add("program INTEGER");
        attributes.add("sequence INTEGER");

        return attributes;
    }

    @Override
    protected ContentValues getContentValues(ProgramContent entity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("content", SQLiteUtil.getModel(entity.getContent()));
        contentValues.put("program", SQLiteUtil.getModel(entity.getProgram()));
        contentValues.put("sequence", SQLiteUtil.getInteger(entity.getSequence()));

        return contentValues;
    }

    @Override
    protected ProgramContent getEntity(Cursor cursor) {
        ProgramContent entity = new ProgramContent(SQLiteUtil.getLong(cursor, "id"));
        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setContent(new Content(SQLiteUtil.getLong(cursor, "content")));
        entity.setProgram(new Program(SQLiteUtil.getLong(cursor, "program")));
        entity.setSequence(SQLiteUtil.getInteger(cursor, "sequence"));

        return entity;
    }

}
