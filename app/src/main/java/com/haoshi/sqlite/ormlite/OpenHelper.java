package com.haoshi.sqlite.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author: HaoShi
 */
public class OpenHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "ormlite";
    private static final int DB_VERSION = 1;

    private static OpenHelper openHelper;

    public OpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public synchronized static OpenHelper getInstance(Context context) {
        if (openHelper == null) {
            openHelper = new OpenHelper(context);
        }
        return openHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Personnel.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {
            TableUtils.dropTable(connectionSource, Personnel.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
