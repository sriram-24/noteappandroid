package com.example.noteappandroid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteappandroid.R

class UpdateActivity : AppCompatActivity() {

    lateinit var editTextTitle: EditText
    lateinit var editTextDescription : EditText
    lateinit var buttonCancel : Button
    lateinit var buttonSave : Button

    var currentNoteId : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.title = "Add Note"

        editTextTitle = findViewById(R.id.editTextUpdateTitle)
        editTextDescription = findViewById(R.id.editTextUpdateDescription)
        buttonSave = findViewById(R.id.buttonNoteUpdateSave)
        buttonCancel = findViewById(R.id.buttonNoteUpdateCancel)

        buttonCancel.setOnClickListener{
            Toast.makeText(this, "New note Cancelled.", Toast.LENGTH_SHORT).show()
            finish()
        }

        buttonSave.setOnClickListener {
            updateNote()
        }
        getAndSetData()
    }

    private fun updateNote(){

        val updatedTitle = editTextTitle.text.toString()
        val updateDesctiption = editTextDescription.text.toString()

        val intent = Intent()
        intent.putExtra("updatedTitle",updatedTitle)
        intent.putExtra("updatedDescription", updateDesctiption)
        if (currentNoteId != -1){
            println("updateNoteExec")
            intent.putExtra("noteId",currentNoteId)
            println("$updatedTitle $updateDesctiption $currentNoteId ")
            setResult(RESULT_OK,intent)
            finish()
        }

    }

    fun getAndSetData(){
        val currentTitle = intent.getStringExtra("currentTitle").toString()
        val currentDescription = intent.getStringExtra("currentDescription").toString()
        currentNoteId = intent.getIntExtra("noteId",-1)

        editTextTitle.setText(currentTitle)
        editTextDescription.setText(currentDescription)

    }
}