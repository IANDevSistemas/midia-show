package br.com.iandev.midiaindoor.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.com.iandev.midiaindoor.util.SQLiteUtil;

/**
 * Created by Lucas on 19/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 19/12/2016  Lucas
 */

public class ConnectionFactory extends SQLiteOpenHelper {
    /*
    cd C:\Users\Lucas\AppData\Local\Android\sdk\platform-tools\
    adb -d shell "start-as br.com.iandev.midiaindoor ls -l /data/data/br.com.iandev.midiaindoor/databases/"
    adb -d shell "start-as br.com.iandev.midiaindoor cp /data/data/br.com.iandev.midiaindoor/databases/midiaindoor.sqlite /sdcard/midiaindoor.sqlite"
    adb pull /sdcard/midiaindoor.sqlite
    */
    public static final String DATABASE_NAME = "br.com.midiaindoor.sqlite";
    public static final int DATABASE_VERSION = 170418;

    private static ConnectionFactory instance;
    private List<Dao> daos = new ArrayList<Dao>() {{
        add(new ChannelDao(null));
        add(new ContentDao(null));
        add(new DeviceDao(null));
        add(new LogDao(null));
        add(new PersonDao(null));
        add(new ProgramContentDao(null));
        add(new ProgramDao(null));
        add(new ProgrammingDao(null));
    }};
    private Set<String> tableNames = new LinkedHashSet<String>();

    public ConnectionFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        readTableNames(this.getReadableDatabase());
    }

    public static ConnectionFactory getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectionFactory(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = null;

        // Drop all tables
        cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND NOT name LIKE '%android%' AND NOT name LIKE '%sqlite%'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String tableName = SQLiteUtil.getString(cursor, "name");
                sqLiteDatabase.execSQL("DROP TABLE '" + tableName + "';");
                cursor.moveToNext();
            }
        }
        cursor.close();

        for (Dao dao : getDaos()) {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            List<String> attributes = dao.getAttributes();
            for (String attribute : attributes) {
                if (first) {
                    sb.append(attribute);
                    first = false;
                } else {
                    sb.append(", ");
                    sb.append(attribute);
                }
            }
            sqLiteDatabase.execSQL(String.format("CREATE TABLE %s(%s);", dao.getEntityName(), sb.toString()));

            readTableNames(sqLiteDatabase);
        }

        sqLiteDatabase.execSQL("CREATE TABLE Sequence(id VARCHAR(32) PRIMARY KEY, value INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    protected void finalize() {
        try {
            if (instance != null) {
                instance.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            super.finalize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public List<Dao> getDaos() {
        return daos;
    }

    public Set<String> getTableNames() {
        return tableNames;
    }

    private void readTableNames(SQLiteDatabase sqLiteDatabase) {
        // Store all tables names
        getTableNames().clear();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND NOT name LIKE '%android%' AND NOT name LIKE '%sqlite%'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String tableName = SQLiteUtil.getString(cursor, "name");
                if (!"Sequence".equals(tableName)) {
                    getTableNames().add(tableName);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}
