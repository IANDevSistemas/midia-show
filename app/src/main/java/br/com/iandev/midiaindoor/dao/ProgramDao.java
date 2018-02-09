package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Program;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class ProgramDao extends Dao<Program> {
    public ProgramDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "Program";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();

        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("description TEXT");
        attributes.add("status TEXT");

        return attributes;
    }

    @Override
    protected ContentValues getContentValues(Program entity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("description", SQLiteUtil.getString(entity.getDescription()));
        contentValues.put("status", SQLiteUtil.getCharacter(entity.getStatus()));

        return contentValues;
    }

    @Override
    protected Program getEntity(Cursor cursor) {
        Program entity = new Program(null);
        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setDescription(SQLiteUtil.getString(cursor, "description"));
        entity.setStatus(SQLiteUtil.getCharacter(cursor, "status"));

        return entity;
    }

}
