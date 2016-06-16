package com.pickachu.momma.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ishaangarg on 21/01/16.
 */
public class DbSingleton {

    private static SqliteHelper sSQLiteOpenHelper;
    private static AtomicInteger mAtomicInteger = new AtomicInteger();
    private static SQLiteDatabase mDatabase;

    private DbSingleton() {
    }

    public static synchronized void initializeInstance(SqliteHelper helper) {
        if (sSQLiteOpenHelper == null) {
            sSQLiteOpenHelper = helper;
        }
    }

    public static synchronized SqliteHelper getInstance() {
        if (sSQLiteOpenHelper == null) {
            throw new IllegalStateException(DbSingleton.class.getSimpleName() +
                    " is not initialized, call initializeInstance() method first.");
        }

        return sSQLiteOpenHelper;
    }

    public static synchronized SQLiteDatabase openDatabase() {
        if (mAtomicInteger.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = sSQLiteOpenHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public static synchronized void closeDatabase() {
        if (mAtomicInteger.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }

}
