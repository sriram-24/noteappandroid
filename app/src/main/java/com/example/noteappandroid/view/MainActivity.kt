package com.example.noteappandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noteappandroid.NoteApplication
import com.example.noteappandroid.R
import com.example.noteappandroid.viewModel.NoteViewModel
import com.example.noteappandroid.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this,viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.AllNotes.observe(this, Observer { notes ->

        })

    }
}