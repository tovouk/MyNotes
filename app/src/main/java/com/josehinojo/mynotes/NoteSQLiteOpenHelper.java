package com.josehinojo.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteSQLiteOpenHelper extends SQLiteOpenHelper {

    /*
    This file is modeled after this sqliteOpenHelper tutorial
    http://hmkcode.com/android-tutorial/android-sqlite-content-provider-tutorial/

    This is generally not the way you want to go about using an SQLite Database.
    Normally you'd use a content provider to communicate with the Database or in some cases
    you'd use Room and LiveData

     */

    // Todo add content provider functionality

    private static final int DATABASE_VERSION_NUMBER = 1;
    private static final String DATABASE_NAME = "NOTES_DB";

    public NoteSQLiteOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION_NUMBER);
    }

    //TABLE NAME For DATABASE
    private static final String NOTES_TABLE = "NOTES";
    //COLUMN NAMES
    private static final String ID = "ID";
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";
    //Array of COLUMNS
    private static final String[] COLUMNS = {ID,TITLE,CONTENT};

    private static NoteSQLiteOpenHelper dbInstance;

    public static NoteSQLiteOpenHelper getDbInstance(Context context){
        if(dbInstance == null){
            dbInstance = new NoteSQLiteOpenHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    //creates database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE = "CREATE TABLE NOTES ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "content TEXT )";
        db.execSQL(CREATE_NOTE);
    }
    //executes when database upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NOTES");
        Log.i("upgraded database:","current version " + Integer.toString(newVersion));
        this.onCreate(db);
    }
    //adds a note to the database
    public void addNote(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,note.getTitle());
        contentValues.put(CONTENT,note.getContent());
        db.insert(NOTES_TABLE,null,contentValues);
        db.close();
    }
    //gets note by id
    public Note getNoteById(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(NOTES_TABLE,COLUMNS,ID + "= ?",new String[] {String.valueOf(id)},
                null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        Note note = new Note();
        note.setId(Integer.parseInt(cursor.getString(0)));
        note.setTitle(cursor.getString(1));
        note.setContent(cursor.getString(2));

        cursor.close();
        return note;
    }

    public List<Note> getNoteByTitle(String title){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(NOTES_TABLE,COLUMNS,TITLE + " like %?%",new String[] {title},
                null,null,null,null);
        ArrayList<Note> notes = new ArrayList<Note>();
        Note note;
        if(cursor.moveToFirst()){
            do{
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));

                notes.add(note);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ NOTES_TABLE,null);

        Note note;
        if(cursor.moveToFirst()){
            do{
                note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));

                notes.add(note);
            }while (cursor.moveToNext());
        }
        Log.i("getting notes",notes.toString());
        cursor.close();
        return notes;
    }

    public int updateBook(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(CONTENT, note.getContent());

        int updated = db.update(NOTES_TABLE,
                values, // column/value
                ID +" = ?",
                new String[] { String.valueOf(note.getId()) });

        db.close();
        Log.i("updated book",note.toString());
        return updated;

    }

    //DeathNote?
    public void deleteNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(NOTES_TABLE,
                ID + " = ?",
                new String[] { String.valueOf(note.getId()) });

        db.close();

        Log.d("deleted Note", note.toString());

    }


}
