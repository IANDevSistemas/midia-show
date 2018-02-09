package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.core.integration.IntegrationApi;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 12/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 12/12/2016  Lucas
 */

public class ContentDao extends Dao<Content> {
    public ContentDao(Context context) {
        super(context);
    }

    @Override
    public String getEntityName() {
        return "Content";
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add("id INTEGER PRIMARY KEY AUTOINCREMENT");
        attributes.add("description TEXT");
        attributes.add("status TEXT");

        attributes.add("durationInterval INTEGER");
        attributes.add("lastPlaybackDate INTEGER");

        attributes.add("updateInterval INTEGER");
        attributes.add("updateToleranceInterval INTEGER");
        attributes.add("lastUpdateDate INTEGER");
        attributes.add("lastUpdateAttemptDate INTEGER");

        attributes.add("startDate INTEGER");
        attributes.add("endDate INTEGER");

        attributes.add("integrationApi INTEGER");
        attributes.add("accessUrl TEXT");
        attributes.add("hash TEXT");
        attributes.add("alias TEXT");
        attributes.add("indexFilePath TEXT");

        return attributes;
    }

    @Override
    protected ContentValues getContentValues(Content entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", SQLiteUtil.getLong(entity.getId()));
        contentValues.put("description", SQLiteUtil.getString(entity.getDescription()));
        contentValues.put("status", SQLiteUtil.getCharacter(entity.getStatus()));

        contentValues.put("durationInterval", SQLiteUtil.getLong(entity.getDurationInterval()));
        contentValues.put("lastPlaybackDate", SQLiteUtil.getDate(entity.getLastPlaybackDate()));

        contentValues.put("updateInterval", SQLiteUtil.getLong(entity.getUpdateInterval()));
        contentValues.put("updateToleranceInterval", SQLiteUtil.getLong(entity.getUpdateToleranceInterval()));
        contentValues.put("lastUpdateDate", SQLiteUtil.getDate(entity.getLastUpdateDate()));
        contentValues.put("lastUpdateAttemptDate", SQLiteUtil.getDate(entity.getLastUpdateAttemptDate()));

        contentValues.put("startDate", SQLiteUtil.getDate(entity.getStartDate()));
        contentValues.put("endDate", SQLiteUtil.getDate(entity.getEndDate()));

        contentValues.put("integrationApi", entity.getIntegrationApi() != null ? entity.getIntegrationApi().toString() : null);
        contentValues.put("accessUrl", SQLiteUtil.getString(entity.getAccessUrl()));
        contentValues.put("hash", SQLiteUtil.getString(entity.getHash()));
        contentValues.put("alias", SQLiteUtil.getString(entity.getAlias()));
        contentValues.put("indexFilePath", SQLiteUtil.getString(entity.getIndexFilePath()));

        return contentValues;
    }

    @Override
    protected Content getEntity(Cursor cursor) {
        Content entity = new Content(null);

        entity.setId(SQLiteUtil.getLong(cursor, "id"));
        entity.setDescription(SQLiteUtil.getString(cursor, "description"));
        entity.setStatus(SQLiteUtil.getCharacter(cursor, "status"));

        entity.setDurationInterval(SQLiteUtil.getLong(cursor, "durationInterval"));
        entity.setLastPlaybackDate(SQLiteUtil.getDate(cursor, "lastPlaybackDate"));

        entity.setUpdateInterval(SQLiteUtil.getLong(cursor, "updateInterval"));
        entity.setUpdateToleranceInterval(SQLiteUtil.getLong(cursor, "updateToleranceInterval"));
        entity.setLastUpdateDate(SQLiteUtil.getDate(cursor, "lastUpdateDate"));
        entity.setLastUpdateAttemptDate(SQLiteUtil.getDate(cursor, "lastUpdateAttemptDate"));

        entity.setStartDate(SQLiteUtil.getDate(cursor, "startDate"));
        entity.setEndDate(SQLiteUtil.getDate(cursor, "endDate"));

        entity.setIntegrationApi(IntegrationApi.fromValue(SQLiteUtil.getString(cursor, "integrationApi")));
        entity.setAccessUrl(SQLiteUtil.getString(cursor, "accessUrl"));
        entity.setHash(SQLiteUtil.getString(cursor, "hash"));
        entity.setAlias(SQLiteUtil.getString(cursor, "alias"));
        entity.setIndexFilePath(SQLiteUtil.getString(cursor, "indexFilePath"));

        return entity;
    }

}
