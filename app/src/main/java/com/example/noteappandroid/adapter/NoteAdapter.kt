package com.example.noteappandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappandroid.R
import com.example.noteappandroid.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes : List<Note> = ArrayList<Note>()

    class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
        val cardView : CardView = itemView.findViewById(R.id.cardViewNote)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote : com.example.noteappandroid.model.Note = notes[position]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description

    }

    fun setNotes(notes : List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position: Int) : Note{
        return notes[position]
    }
}