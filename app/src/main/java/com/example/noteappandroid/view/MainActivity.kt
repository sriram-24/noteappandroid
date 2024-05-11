package com.example.noteappandroid.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappandroid.NoteApplication
import com.example.noteappandroid.R
import com.example.noteappandroid.adapter.NoteAdapter
import com.example.noteappandroid.model.Note
import com.example.noteappandroid.viewModel.NoteViewModel
import com.example.noteappandroid.viewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
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
        registerResultActivityLauncher()
    }

    private fun registerResultActivityLauncher(){
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {resultAddNote ->
                val resultCode = resultAddNote.resultCode
                val resultData = resultAddNote.data
                println("resultCode : ${resultCode}")
                println("resultData : ${resultData}")
                if (resultCode == RESULT_OK && resultData !=null){
                    val noteTitle : String = resultData.getStringExtra("title").toString()
                    val noteDescription : String = resultData.getStringExtra("description").toString()
                    val note = Note(
                        title = noteTitle,
                        description = noteDescription
                    )
                    println("NoteOut : ${note}")
                    noteViewModel.insert(note)
                }

            })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        getMenuInflater().inflate(R.menu.new_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemAddNote -> {
                val intent = Intent(this, NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }

            R.id.itemDeleteNote -> {
                Toast.makeText(applicationContext,"Delete Note coming soon",Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }
}