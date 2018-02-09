package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 20/03/2017.
 * Changes:
 * Date        Responsable     Change
 * 20/03/2017  Lucas
 */

public class PersonDao extends Dao<Person> {
    public PersonDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "Person";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();

        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("name TEXT");
        attributes.add("alias TEXT");
        attributes.add("status TEXT");

        return attributes;
    }

    @Override
    protected ContentValues getContentValues(Person entity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("name", SQLiteUtil.getString(entity.getName()));
        contentValues.put("alias", SQLiteUtil.getString(entity.getAlias()));
        contentValues.put("status", SQLiteUtil.getCharacter(entity.getStatus()));

        return contentValues;
    }

    @Override
    protected Person getEntity(Cursor cursor) {
        Person entity = new Person(null);

        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setName(SQLiteUtil.getString(cursor, "name"));
        entity.setAlias(SQLiteUtil.getString(cursor, "alias"));
        entity.setStatus(SQLiteUtil.getCharacter(cursor, "status"));

        return entity;
    }
}
