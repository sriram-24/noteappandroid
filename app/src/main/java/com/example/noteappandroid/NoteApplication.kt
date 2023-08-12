package com.example.noteappandroid

import android.app.Application
import com.example.noteappandroid.repository.NoteRepository
import com.example.noteappandroid.room.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { NoteDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { NoteRepository(database.getNoteDao()) }

}