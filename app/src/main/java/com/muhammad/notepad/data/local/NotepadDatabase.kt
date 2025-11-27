package com.muhammad.notepad.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammad.notepad.data.local.dao.NoteDao
import com.muhammad.notepad.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 2, exportSchema = true)
abstract class NotepadDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}