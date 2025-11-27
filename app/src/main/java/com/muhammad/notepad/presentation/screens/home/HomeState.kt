package com.muhammad.notepad.presentation.screens.home

import com.muhammad.notepad.domain.model.Note

data class HomeState(
    val notes : List<Note> = emptyList(),
    val isNotesLoading : Boolean = true,
    val noteTitle : String = "",
    val selectedNoteId : Long? = null,
    val showEditNoteDialog : Boolean = false,
    val showDeleteNoteDialog : Boolean = false,
    val totalTasks : Int = 0,
    val completeTasks : Int = 0,
    val noteQuery : String = ""
)
