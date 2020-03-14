package com.example.globalpharma.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserGP.db";

    // User table name
    private static final String TABLE_USER = "User";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_PASSWORD = "password";

    //Database
    SQLiteDatabase db;

    // create table sql query
    private String CREATE_USER_TABLE_QUERY = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT UNIQUE," + COLUMN_PASSWORD + " TEXT" + ")";

    //Drop table query
    private String DROP_USER_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_USER;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_QUERY);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE_QUERY);
        this.onCreate(db);
    }

    public boolean onInsert(User user){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("password", user.getPassword());
        contentValues.put("phone_number", user.getPhone());

        long ins = db.insert(TABLE_USER, null, contentValues);
        if(ins == -1) return false;
        else return true;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        if (phoneNumber.length() <= 8 || phoneNumber.length() > 9 ) {//||
                //(!phoneNumber.startsWith("69") || !phoneNumber.startsWith("67") || !phoneNumber.startsWith("65"))){
                   return false;
        }
        else{
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM User WHERE phone_number = ?", new String[]{phoneNumber});
            if(cursor.getCount() > 0)
                return false;
            else
                return true;
        }
    }

    public String searchPassword (String phonenumber){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT phone_number, password FROM User", null);
        String pass = " ", user;
        if(cursor.moveToFirst()){
            do{
                user = cursor.getString(0);
                if(user.toString().equals(phonenumber)){
                    pass = cursor.getString(1);
                    break;
                }
                else pass = null;
            }while (cursor.moveToNext());
        }
        return pass;
    }

    public boolean tryToLogIn(String phone, String password){
        db = this.getReadableDatabase();
        String pass_found = " ";
        Cursor cursor1 = db.rawQuery("SELECT phone_number FROM User", null);
        if(cursor1.moveToFirst()){
            do{
                if(phone.equals(cursor1.getString(0))) {
                    pass_found = searchPassword(phone);
                    break;
                }
                else
                    pass_found = null;
            }while(cursor1.moveToNext());
        }
        if(pass_found.equals(password))
            return  true;
        else {
            return false;
        }
    }
}


