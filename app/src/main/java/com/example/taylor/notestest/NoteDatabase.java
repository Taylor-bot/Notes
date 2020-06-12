package com.example.taylor.notestest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Taylor on 2020/6/11.
 */

public class NoteDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String ID = "id";
    public static final String TIME = "time";
    public static final String MODE = "mode";

//    public NoteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public NoteDatabase(Context context) {
        super(context, "notes", null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CONTENT + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL,"
                + MODE + " INTEGER DEFAULT 1)");
    }
}
