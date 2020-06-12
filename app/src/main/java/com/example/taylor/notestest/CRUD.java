package com.example.taylor.notestest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor on 2020/6/11.
 */

public class CRUD {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    private static final String[] columns = {
            NoteDatabase.ID,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME,
            NoteDatabase.MODE
    };

    public CRUD(Context context) {
        dbHandler = new NoteDatabase(context);
    }
    public void open(){
        db = dbHandler.getWritableDatabase();
    }
    public void close(){
        dbHandler.close();
    }

    public Note addNote(Note note)
    {
        //添加一个笔记到数据库中
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDatabase.CONTENT,note.getContent());
        contentValues.put(NoteDatabase.TIME,note.getTime());
        contentValues.put(NoteDatabase.MODE,note.getTag());
        long insertId = db.insert(NoteDatabase.TABLE_NAME,null,contentValues);//这里是设置此时传进去的id值insertId
        note.setId(insertId);
        return note;
    }
    public Note getNote(long id)
    {
        //获取note的所有值
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME,columns,NoteDatabase.ID +"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        Note e = new Note(cursor.getString(1),cursor.getString(2),cursor.getInt(3));
        return e ;
    }
    public List<Note> getAllNotes(){
        //添加每一条数据到list中
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME,columns,NoteDatabase.ID +"=?",
                null,null,null,null,null);
        List<Note> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToFirst()){
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDatabase.MODE)));
                notes.add(note);
            }
        }
        return notes;
    }
        public int updateNote(Note note){
            //更新一个note数据
            ContentValues values = new ContentValues();
            values.put(NoteDatabase.CONTENT,note.getContent());
            values.put(NoteDatabase.TIME,note.getTime());
            values.put(NoteDatabase.MODE,note.getTag());

            //update row
            return db.update(NoteDatabase.TABLE_NAME,values,
                    NoteDatabase.ID + "= ?",new String[]{String.valueOf(note.getId())});
        }
        //删除值
        public  void removeNote(Note note){
            //remove
            db.delete(NoteDatabase.TABLE_NAME,NoteDatabase.ID + "="+note.getId(),null);
        }
}
