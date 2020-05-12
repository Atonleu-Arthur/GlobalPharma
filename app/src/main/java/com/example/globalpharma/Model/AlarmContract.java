package com.example.globalpharma.Model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class AlarmContract {

    public AlarmContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.globalpharma";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ALARM = "reminder-path";

    public static final class AlarmEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ALARM);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_ALARM;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                "/" + CONTENT_AUTHORITY + "/" + PATH_ALARM;

        public static final String TABLE_NAME = "ALARMS";

        public static final String ID = BaseColumns._ID;

        public static final String KEY_TITLE = "TITLE" ;

        public static final String KEY_DATE = "DATE" ;

        public static final String KEY_TIME = "TIME" ;

        public static final String KEY_REPEAT = "REPEAT" ;

        public static final String KEY_REPEAT_NO = "REPEAT_NO" ;

        public static final String KEY_REPEAT_TYPE = "REPEAT_TYPE" ;

        public static final String KEY_ACTIVE = "ACTIVE" ;

    }

    public static final String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
