package com.muhammad.notepad.data.repository

import com.muhammad.notepad.data.local.dao.NoteDao
import com.muhammad.notepad.data.mapper.toNoteEntity
import com.muhammad.notepad.data.mapper.toNoteList
import com.muhammad.notepad.domain.model.Note
import com.muhammad.notepad.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImp(
    private val noteDao: NoteDao,
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map { it.toNoteList() }
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(id: Long, title: String) {
        noteDao.updateNote(id = id, title = title)
    }

    override suspend fun deleteNote(id: Long) {
        noteDao.deleteNote(id)
    }

    override suspend fun toggleNoteComplete(id: Long, isCompleted: Boolean) {
        noteDao.toggleNoteComplete(id = id, isCompleted = isCompleted)
    }

    override fun getCompletedNoteCount(): Flow<Int> {
        return noteDao.getCompletedNoteCount()
    }
}