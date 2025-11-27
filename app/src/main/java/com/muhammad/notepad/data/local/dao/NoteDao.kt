package com.muhammad.notepad.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muhammad.notepad.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM NoteEntity ORDER BY createdAt DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("DELETE FROM NoteEntity WHERE id = :id")
    suspend fun deleteNote(id: Long)

    @Query("UPDATE NoteEntity SET title = :title WHERE id = :id")
    suspend fun updateNote(id: Long, title: String)

    @Query("UPDATE NoteEntity SET completed = :isCompleted WHERE id = :id")
    suspend fun toggleNoteComplete(id: Long, isCompleted: Boolean)

    @Query("SELECT COUNT(*) FROM NoteEntity WHERE completed = 1")
    fun getCompletedNoteCount(): Flow<Int>
}