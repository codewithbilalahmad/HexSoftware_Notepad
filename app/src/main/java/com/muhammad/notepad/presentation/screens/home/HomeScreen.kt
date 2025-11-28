package com.muhammad.notepad.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.notepad.R
import com.muhammad.notepad.presentation.components.AppTextField
import com.muhammad.notepad.presentation.screens.home.components.AddNoteSection
import com.muhammad.notepad.presentation.components.AppAlertDialog
import com.muhammad.notepad.presentation.screens.home.components.HomeTasksHeader
import com.muhammad.notepad.presentation.screens.home.components.NoteCard
import com.muhammad.notepad.utils.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    ObserveAsEvents(viewModel.events) {event ->
        when(event){
            HomeEvent.ScrollToTop -> {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .padding(vertical = 4.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeTasksHeader(totalTasks = state.totalTasks, completedTasks = state.completeTasks)
            AddNoteSection(note = state.noteQuery, onAddNote = {
                viewModel.onAction(HomeAction.OnCreateNote)
            }, isCreateNoteEnable = state.noteQuery.isNotEmpty(), onNoteQueryChange = { text ->
                viewModel.onAction(HomeAction.OnNoteQueryChange(text))
            })
        }
    }) { paddingValues ->
        when {
            state.isNotesLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    ContainedLoadingIndicator()
                }
            }

            state.notes.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_notes),
                        contentDescription = null, modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_notes_found),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    contentPadding = paddingValues, state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.notes, key = { it.id ?: 0L }) { note ->
                        NoteCard(note = note, onEditNote = { id , title ->
                            viewModel.onAction(HomeAction.OnUpdateClick(id = id, title = title))
                        }, onDeleteNote = { id ->
                            viewModel.onAction(HomeAction.OnDeleteNote(id))
                        }, onDeleteClick = {id ->
                            viewModel.onAction(HomeAction.OnDeleteClick(id))
                        }, onToggleCompleted = { id, completed ->
                            viewModel.onAction(HomeAction.OnToggleNoteCompleted(id, completed))
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .animateItem())
                    }
                }
            }
        }
    }
    if (state.showEditNoteDialog) {
        AppAlertDialog(
            onDismiss = {
                viewModel.onAction(HomeAction.OnDismissNoteUpdateDialog)
            },
            onDismissClick = {
                viewModel.onAction(HomeAction.OnDismissNoteUpdateDialog)
            },
            onConfirmClick = {
                viewModel.onAction(HomeAction.OnUpdateNote)
            },
            title = stringResource(R.string.edit_note),
            messageContent = {
                AppTextField(value = state.noteTitle, onValueChange = {newValue ->
                    viewModel.onAction(HomeAction.OnNoteTitleChange(newValue))
                }, hint = R.string.whats_doing, onKeyBoardAction = {
                    viewModel.onAction(HomeAction.OnUpdateNote)
                }, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
            },
            dismissText = stringResource(R.string.cancel),
            confirmText = stringResource(R.string.confirm)
        )
    }
    if (state.showDeleteNoteDialog) {
        AppAlertDialog(
            onDismiss = {
                viewModel.onAction(HomeAction.OnDismissNoteDeleteDialog)
            },
            onDismissClick = {
                viewModel.onAction(HomeAction.OnDismissNoteDeleteDialog)
            },
            onConfirmClick = {
                viewModel.onAction(HomeAction.OnDeleteNote())
            },
            title = stringResource(R.string.delete_note),
            message = stringResource(R.string.delete_note_description),
            dismissText = stringResource(R.string.cancel),
            confirmText = stringResource(R.string.confirm)
        )
    }
}