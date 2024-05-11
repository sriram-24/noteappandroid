package com.example.noteappandroid.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.noteappandroid.R

class NoteAddActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription : EditText
    lateinit var buttonCancel : Button
    lateinit var buttonSave : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        supportActionBar?.title = "Add Note"

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonSave = findViewById(R.id.buttonNoteAddSave)
        buttonCancel = findViewById(R.id.buttonNoteAddCancel)

        buttonCancel.setOnClickListener{
            Toast.makeText(this, "New note Cancelled.",Toast.LENGTH_SHORT).show()
            finish()
        }

        buttonSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val noteTitle : String = editTextTitle.text.toString()
        val noteDescription : String = editTextDescription.text.toString()

        val intent = Intent()
        intent.putExtra("title",noteTitle)
        intent.putExtra("description", noteDescription)
        println("saveTriggered")
        setResult(RESULT_OK,intent)
        finish()
    }
}