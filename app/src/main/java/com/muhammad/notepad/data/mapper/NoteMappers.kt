package com.muhammad.notepad.data.mapper

import com.muhammad.notepad.data.local.entity.NoteEntity
import com.muhammad.notepad.domain.model.Note

fun Note.toNoteEntity() : NoteEntity{
    return NoteEntity(
        id = id ?: 0, title = title, completed = completed, createdAt = createdAt
    )
}

fun NoteEntity.toNote() : Note{
    return Note(
        id = id, title = title, completed = completed, createdAt = createdAt
    )
}

fun List<NoteEntity>.toNoteList() : List<Note>{
    return map { it.toNote() }
}