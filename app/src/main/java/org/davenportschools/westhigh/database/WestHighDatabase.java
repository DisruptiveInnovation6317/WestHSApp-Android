package org.davenportschools.westhigh.database;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WestHighDatabase extends SQLiteOpenHelper {
    private static WestHighDatabase INSTANCE;

    private static final String DATABASE_NAME = "WestHigh";
    private static final int DATABASE_VERSION = 1;

    private WestHighDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        INSTANCE = new WestHighDatabase(context);
    }

    public static WestHighDatabase getInstance() {
        if (INSTANCE == null) {
            throw new IllegalAccessError("Database not created yet. :(");
        }

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StaffTableContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(StaffTableContract.DROP_TABLE);
        onCreate(db);
    }

    public boolean staffIsEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, StaffTableContract.TABLE_NAME);
        db.close();
        return count == 0;
    }
}
