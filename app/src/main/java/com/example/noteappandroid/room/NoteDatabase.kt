package com.example.noteappandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.noteappandroid.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object {

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : NoteDatabase{

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(context,NoteDatabase::class.java,"note_database").addCallback(NoteDatabaseCallback(scope = scope)).build()
                INSTANCE = instance
                instance

            }
        }
    }

    private class NoteDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                val noteDao = database.getNoteDao()
                scope.launch {
                    noteDao.insert(Note("title 1", "description 1"))
                    noteDao.insert(Note("title 2", "description 2"))
                    noteDao.insert(Note("title 3", "description 3"))

                }
            }
        }
    }
}