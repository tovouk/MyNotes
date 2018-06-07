package com.josehinojo.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    RecyclerView notesRecyclerView;

    static NoteSQLiteOpenHelper db;
    public static NotesAdapter notesAdapter;

    public static NotesAdapter getAdapter(){
        if(notesAdapter == null){
            notesAdapter = new NotesAdapter(db.getNotes());
        }
        return notesAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecyclerView = findViewById(R.id.notes_recycler_view);

        db = new NoteSQLiteOpenHelper(getApplicationContext());

        notesAdapter = new NotesAdapter(db.getNotes());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        notesRecyclerView.setLayoutManager(layoutManager);
        notesRecyclerView.setAdapter(notesAdapter);
        notesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNew:
                Intent intent = new Intent(this,NewNote.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
