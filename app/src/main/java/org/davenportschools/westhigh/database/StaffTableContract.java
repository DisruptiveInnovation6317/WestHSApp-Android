package org.davenportschools.westhigh.database;

public class StaffTableContract {
    public static final String TABLE_NAME = "staff";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_EMAIL = "email";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_FIRST_NAME + " TEXT," +
            COLUMN_LAST_NAME + " TEXT," +
            COLUMN_EMAIL + " TEXT);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
