package com.github.vv_off.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "messageDB";
    public static final String DB_TABLE_NAME = "message";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATE = "date";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + DB_TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MESSAGE + " TEXT,"
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

    public List<Message> getAllMessageDB() {
        List<Message> messageList = new ArrayList<Message>();
        String selectQuery = "SELECT * FROM " + DB_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messageList;
    }

    public void addMessageDB(String message, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        values.put(KEY_DATE, date);

        db.insert(DB_TABLE_NAME, null, values);
        db.close();
    }

    public void delMessageDB(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateMessageDB(int id, String message, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        values.put(KEY_DATE, date);

        return db.update(DB_TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
