package com.muhammad.notepad.presentation.screens.home

sealed interface HomeAction {
    data class OnToggleNoteCompleted(val id : Long,val isCompleted : Boolean) : HomeAction
    data object OnCreateNote : HomeAction
    data class OnNoteQueryChange(val text : String) : HomeAction
    data class OnNoteTitleChange(val text : String) : HomeAction
    data class OnDeleteNote(val id : Long?=null) : HomeAction
    data class OnUpdateClick(val id : Long,val title : String) : HomeAction
    data class OnDeleteClick(val id : Long) : HomeAction
    data object OnDismissNoteUpdateDialog : HomeAction
    data object OnDismissNoteDeleteDialog : HomeAction
    data object OnUpdateNote : HomeAction
}