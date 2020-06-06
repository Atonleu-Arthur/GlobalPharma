package com.example.globalpharma.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AlarmDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "GlobalPharmaReminder.db";

    private static final int DB_VERSION = 1;

    public AlarmDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + AlarmContract.AlarmEntry.TABLE_NAME +
                "(" + AlarmContract.AlarmEntry.ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AlarmContract.AlarmEntry.KEY_TITLE + "  TEXT NOT NULL, " +
                AlarmContract.AlarmEntry.KEY_DATE + " TEXT NOT NULL, " +
                AlarmContract.AlarmEntry.KEY_TIME + " TEXT NOT NULL, " +
                AlarmContract.AlarmEntry.KEY_REPEAT + " TEXT NOT NULL, " +
                AlarmContract.AlarmEntry.KEY_REPEAT_NO + " TEXT NOT NULL, " +
                AlarmContract.AlarmEntry.KEY_REPEAT_TYPE + " TEXT NOT NULL, " +
                AlarmContract.AlarmEntry.KEY_ACTIVE + " TEXT NOT NULL)";

        db.execSQL(SQL_CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
