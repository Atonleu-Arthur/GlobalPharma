package com.example.globalpharma.Model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlarmProvider extends ContentProvider {
    private AlarmDbHelper mAlarmDbHelper;

    private static final String LOG_TAG = AlarmProvider.class.getSimpleName();

    private static final int REMINDER = 100;

    private static final int REMINDER_ID = 101;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AlarmContract.CONTENT_AUTHORITY, AlarmContract.PATH_ALARM, REMINDER);
        URI_MATCHER.addURI(AlarmContract.CONTENT_AUTHORITY, AlarmContract.PATH_ALARM + "/#", REMINDER_ID);
    }

    @Override
    public boolean onCreate() {
        mAlarmDbHelper = new AlarmDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mAlarmDbHelper.getReadableDatabase();

        Cursor cursor = null;

        int match = URI_MATCHER.match(uri);
        switch (match){
            case REMINDER:
                cursor = sqLiteDatabase.query(AlarmContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case REMINDER_ID:
                selection = AlarmContract.AlarmEntry.ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(AlarmContract.AlarmEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                    throw new IllegalArgumentException("Can not query unknown URI "+ uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match){
            case REMINDER:
                return AlarmContract.AlarmEntry.CONTENT_LIST_TYPE;
            case REMINDER_ID:
                return AlarmContract.AlarmEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI "+ uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = URI_MATCHER.match(uri);
        switch (match){
            case REMINDER:
                return insertReminder(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for "+ uri);
        }
    }

    private Uri insertReminder(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = mAlarmDbHelper.getWritableDatabase();

        long id = sqLiteDatabase.insert(AlarmContract.AlarmEntry.TABLE_NAME, null, values);
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mAlarmDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = URI_MATCHER.match(uri);
        switch (match){
            case REMINDER:
                rowsDeleted = sqLiteDatabase.delete(AlarmContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDER_ID:
                selection = AlarmContract.AlarmEntry.ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = sqLiteDatabase.delete(AlarmContract.AlarmEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for "+ uri);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = URI_MATCHER.match(uri);
        switch (match){
            case REMINDER:
                return  updateReminder(uri, values, selection, selectionArgs);
            case REMINDER_ID:
                selection = AlarmContract.AlarmEntry.ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return  updateReminder(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for "+ uri);
        }
    }

    private int updateReminder(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if(values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = mAlarmDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(AlarmContract.AlarmEntry.TABLE_NAME, values, selection, selectionArgs);

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
