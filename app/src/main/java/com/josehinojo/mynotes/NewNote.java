package com.josehinojo.mynotes;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class NewNote extends AppCompatActivity {

    TextInputEditText noteTitle;
    EditText noteContent;

    NoteSQLiteOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        noteTitle = findViewById(R.id.noteTitle);
        noteContent = findViewById(R.id.noteContent);

        db = new NoteSQLiteOpenHelper(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNote:
                Note note = new Note();
                note.setTitle(noteTitle.getText().toString());
                note.setContent(noteContent.getText().toString());
                db.addNote(note);
                NotesAdapter notesAdapter = MainActivity.getAdapter();
                notesAdapter.setNotes(db.getNotes());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
