package heg.financemanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import heg.financemanager.DatabaseHandler;

public abstract class AbstractDAO {

    protected final static int DB_VERSION = 1;
    protected final static String DB_FILE = "database.db";

    protected SQLiteDatabase db = null;
    protected DatabaseHandler mHandler = null;

    public AbstractDAO(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, DB_FILE, null, DB_VERSION);
    }

    public SQLiteDatabase open() {
        db = mHandler.getWritableDatabase();
        return db;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }



}
