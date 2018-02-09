package br.com.iandev.midiaindoor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.iandev.midiaindoor.model.Model;

public abstract class Dao<T extends Model> {

    private final Context context;
    protected SQLiteDatabase db;

    public Dao(Context context) {
        this.context = context;
    }

    public abstract String getEntityName();

    public abstract List<String> getAttributes();

    protected abstract ContentValues getContentValues(T entity);

    protected abstract T getEntity(Cursor cursor);

    @Nullable
    public final T read(T entity) {
        SelectArgs selectArgs = new SelectArgs();

        selectArgs.put("id = ?", entity.getId().toString());

        List<T> list = list(selectArgs);
        return list.size() == 1 ? list.get(0) : null;
    }

    public final List<T> list() {
        return list(new SelectArgs());
    }

    public final List<T> list(SelectArgs selectArgs) {
        List<T> list = new ArrayList<>();
        try {
            openConnection();
            Cursor cursor = db.query(getEntityName(), null, selectArgs.getWhere(), selectArgs.getArgs(), null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(getEntity(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public final long insertOrUpdate(T entity) {
        if (this.update(entity) < 1L) {
            this.insert(entity);
            return 0L;
        }
        return 1L;
    }

    public final void insertOrUpdate(List<T> entityList) {
        try {
            openConnection();
            for (T entity : entityList) {
                this.insertOrUpdate(entity);
            }
        } finally {
            closeConnection();
        }
    }

    public final long insert(T entity) {
        long id = 0;
        boolean mustClose = false;
        try {
            if (db == null) {
                openConnection();
                mustClose = true;
            }
            if (entity.getId() == null) {
                entity.setId(this.getNextId());
            }
            id = db.insert(getEntityName(), "", getContentValues(entity));
        } finally {
            if (mustClose) {
                closeConnection();
            }
        }
        return id;
    }

    public final long insert(List<T> entityList) {
        long count = 0;
        try {
            openConnection();
            for (T entity : entityList) {
                count += this.insert(entity);
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public final long update(T entity) {
        long id = 0;
        boolean mustClose = false;
        if (entity.getId() != null) {
            try {
                if (db == null) {
                    openConnection();
                    mustClose = true;
                }
                id = db.update(getEntityName(), getContentValues(entity), "id = ?", new String[]{entity.getId().toString()});
            } finally {
                if (mustClose) {
                    closeConnection();
                }
            }
        }
        return id;
    }

    public final long update(List<T> entityList) {
        long count = 0;
        try {
            openConnection();
            for (T entity : entityList) {
                count += this.update(entity) > 0L ? 1L : 0L;
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public final long delete(T entity) {
        long count = 0;
        boolean mustClose = false;
        try {
            if (db == null) {
                openConnection();
                mustClose = true;
            }
            count = db.delete(getEntityName(), "id = ?", new String[]{entity.getId().toString()});
        } finally {
            if (mustClose) {
                closeConnection();
            }
        }
        return count;
    }

    public final long delete(List<T> entityList) {
        long count = 0;
        try {
            openConnection();
            for (T entity : entityList) {
                count += this.delete(entity);
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public final long delete() {
        long count = 0;
        try {
            openConnection();
            count = db.delete(getEntityName(), null, null);
        } finally {
            closeConnection();
        }
        return count;
    }

    protected void openConnection() {
        if (db == null) {
            db = ConnectionFactory.getInstance(context).getWritableDatabase();
        }
    }

    protected void closeConnection() {
        db = null;
    }

    public final void execSQL(String sql, Object[] args) {
        openConnection();
        try {
            db.execSQL(sql, args);
        } finally {
            closeConnection();
        }
    }

    public final void execSQL(String sql) {
        openConnection();
        try {
            db.execSQL(sql);
        } finally {
            closeConnection();
        }
    }

    public final synchronized Long getNextId() {
        Long value = null;
        boolean mustClose = false;
        try {
            if (db == null) {
                openConnection();
                mustClose = true;
            }

            Cursor c = db.query("Sequence", new String[]{"value"}, "id = ?", new String[]{getEntityName()}, null, null, null);

            if (c.moveToFirst()) {
                value = c.getLong(c.getColumnIndex("value"));
            } else {
                value = 1L;
            }

            ContentValues cv = new ContentValues();
            cv.put("id", getEntityName());
            cv.put("value", value + 1L);

            if (value == 1) {
                db.insert("Sequence", "", cv);
            } else {
                db.update("Sequence", cv, "id = ?", new String[]{getEntityName()});
            }

        } finally {
            if (mustClose) {
                closeConnection();
            }
        }
        return value;
    }

    public final long resetSequence() {
        long count = 0;
        try {
            openConnection();
            count = db.delete("Sequence", "id = ?", new String[]{getEntityName()});
        } finally {
            closeConnection();
        }
        return count;
    }

    @Override
    protected void finalize() throws Throwable {
        closeConnection();
        super.finalize();
    }
}

