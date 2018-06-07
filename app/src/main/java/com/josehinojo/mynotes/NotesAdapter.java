package com.josehinojo.mynotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends
        RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    // Todo add onListItemClickListener to pass Note title and content as Extras and allow
    // updating and editing
    private ArrayList<Note> notes;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView note_preview;

        public MyViewHolder(View view){
            super(view);
            note_preview = view.findViewById(R.id.note_preview);
        }

    }
    public void setNotes(ArrayList<Note> list){
        notes = list;
    }

    public NotesAdapter(ArrayList<Note> notes){
        this.notes = notes;
    }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Note note = notes.get(position);
            holder.note_preview.setText(note.getTitle());
        }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_preview,parent, false);
        return new MyViewHolder(v);
    }


}
