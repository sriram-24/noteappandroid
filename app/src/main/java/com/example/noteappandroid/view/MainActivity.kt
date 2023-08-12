package com.example.noteappandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappandroid.NoteApplication
import com.example.noteappandroid.R
import com.example.noteappandroid.adapter.NoteAdapter
import com.example.noteappandroid.viewModel.NoteViewModel
import com.example.noteappandroid.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerViewCard)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this,viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.AllNotes.observe(this, Observer { notes ->

            noteAdapter.setNotes(notes)

        })

    }
}