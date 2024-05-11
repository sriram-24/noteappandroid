package com.example.noteappandroid.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.recyclerview.widget.ItemTouchHelper
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
    lateinit var noteAdapter :NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      set App bar title
        supportActionBar?.title = "Notes App"

        val recyclerView : RecyclerView = findViewById(R.id.recyclerViewCard)
        recyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this,viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.AllNotes.observe(this, Observer { notes ->

            noteAdapter.setNotes(notes)

        })
        registerResultActivityLauncher()

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showConfirmationForDelete(viewHolder)
            }

        }).attachToRecyclerView(recyclerView)
    }

    private fun showConfirmationForDelete(viewHolder : RecyclerView.ViewHolder){
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Confirmation")
        dialogMessage.setMessage("Are you sure you want to delete?")
        dialogMessage.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
            noteAdapter.notifyDataSetChanged()
        })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener{dialog, which->
            noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
        })
        dialogMessage.create().show()
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
                deleteAllNotes()
            }

        }
        return true
    }

    private fun deleteAllNotes(){
        val deleteDialog = AlertDialog.Builder(this)
        deleteDialog.setTitle("Confirmation")
        deleteDialog.setMessage("Are you sure you want to delete all the notes?")
        deleteDialog.setNegativeButton("No",DialogInterface.OnClickListener{
            dialog, which ->
            dialog.cancel()
        })
        deleteDialog.setPositiveButton("Yes", DialogInterface.OnClickListener{
            dialog, which ->
            noteViewModel.deleteAll()
        })
        deleteDialog.create().show()
    }
}