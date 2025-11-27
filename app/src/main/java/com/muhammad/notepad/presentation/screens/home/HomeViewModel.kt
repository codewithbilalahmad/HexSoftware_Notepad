package com.muhammad.notepad.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.notepad.domain.model.Note
import com.muhammad.notepad.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class HomeViewModel(
    private val noteRepository: NoteRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state, noteRepository.getCompletedNoteCount(), noteRepository.getNotes()
    ) { state, completedNotes, notes ->
        state.copy(
            completeTasks = completedNotes,
            notes = notes,
            totalTasks = notes.size,
            isNotesLoading = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnCreateNote -> onCreateNote()
            is  HomeAction.OnDeleteNote -> onDeleteNote(action.id)
            is HomeAction.OnToggleNoteCompleted -> onToggleNoteCompleted(
                id = action.id,
                completed = action.isCompleted
            )

            HomeAction.OnUpdateNote -> onUpdateNote()
            is HomeAction.OnUpdateClick -> onUpdateClick(id = action.id, title = action.title)
            HomeAction.OnDismissNoteUpdateDialog -> onDismissNoteUpdateDialog()
            is HomeAction.OnNoteQueryChange -> {
                _state.update { it.copy(noteQuery = action.text) }
            }

            is HomeAction.OnNoteTitleChange -> {
                _state.update { it.copy(noteTitle = action.text) }
            }

            is HomeAction.OnDeleteClick -> {
                _state.update { it.copy(showDeleteNoteDialog = true, selectedNoteId = action.id) }
            }

            HomeAction.OnDismissNoteDeleteDialog -> {
                _state.update { it.copy(showDeleteNoteDialog = false, selectedNoteId = null) }
            }
        }
    }

    private fun onDismissNoteUpdateDialog() {
        _state.update { it.copy(showEditNoteDialog = false, selectedNoteId = null, noteTitle = "") }
    }

    private fun onUpdateNote() {
        viewModelScope.launch {
            noteRepository.updateNote(
                id = state.value.selectedNoteId ?: return@launch,
                title = state.value.noteTitle
            )
            onAction(HomeAction.OnDismissNoteUpdateDialog)
        }
    }

    private fun onUpdateClick(id: Long, title: String) {
        _state.update { it.copy(showEditNoteDialog = true, selectedNoteId = id, noteTitle = title) }
    }

    private fun onToggleNoteCompleted(id: Long, completed: Boolean) {
        viewModelScope.launch {
            noteRepository.toggleNoteComplete(id = id, isCompleted = completed)
        }
    }


    private fun onDeleteNote(id : Long?) {
        viewModelScope.launch {
            if(id != null){
                _state.update { it.copy(selectedNoteId = id) }
            }
            noteRepository.deleteNote(id = state.value.selectedNoteId ?: return@launch)
            if(id == null){
                onAction(HomeAction.OnDismissNoteDeleteDialog)
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun onCreateNote() {
        viewModelScope.launch {
            val note = Note(
                title = state.value.noteQuery.trim(),
                completed = false, createdAt = System.currentTimeMillis()
            )
            noteRepository.insertNote(note)
            _state.update {
                it.copy(noteQuery = "")
            }
        }
    }
}