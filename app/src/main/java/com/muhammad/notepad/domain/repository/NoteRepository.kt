package com.muhammad.notepad.domain.repository

import com.muhammad.notepad.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(id: Long, title: String)
    suspend fun deleteNote(id: Long)
    suspend fun toggleNoteComplete(id: Long, isCompleted: Boolean)
    fun getCompletedNoteCount(): Flow<Int>
}