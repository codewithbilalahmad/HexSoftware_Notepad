package com.muhammad.notepad.presentation.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.muhammad.notepad.R
import com.muhammad.notepad.domain.model.Note
import com.muhammad.notepad.presentation.components.AppCheckBox
import com.muhammad.notepad.utils.formattedDate

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onToggleCompleted: (id: Long, Boolean) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onEditNote: (Long, String) -> Unit,
) {
    val density = LocalDensity.current
    var cardHeight by remember { mutableStateOf(0.dp) }
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart ||
                value == SwipeToDismissBoxValue.StartToEnd
            ) {
                onDeleteNote(note.id ?: 0L)
                true
            } else false
        }
    )
    SwipeToDismissBox(state = swipeToDismissState, backgroundContent = {
        Row(
            modifier = modifier
                .height(cardHeight)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.error)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onError
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onError
            )
        }
    }) {
        Card(
            modifier = modifier.onGloballyPositioned { coordinates ->
                cardHeight = with(density) {
                    coordinates.size.height.toDp()
                }
            },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AppCheckBox(checked = note.completed, onCheckChange = { newValue ->
                    onToggleCompleted(note.id ?: 0L, newValue)
                })
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val titleColor by animateColorAsState(
                        targetValue = if (note.completed) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onBackground,
                        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
                        label = "titleColor"
                    )
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = titleColor,
                            textDecoration = if (note.completed) TextDecoration.LineThrough else TextDecoration.None
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    onEditNote(note.id ?: 0L, note.title)
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_edit),
                                    contentDescription = null
                                )
                            }
                            IconButton(
                                onClick = {
                                    onDeleteClick(note.id ?: 0L)
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.error,
                                    contentColor = MaterialTheme.colorScheme.onError
                                )
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                                    contentDescription = null
                                )
                            }
                        }
                        Text(
                            text = note.createdAt.formattedDate(),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
                        )
                    }
                }
            }
        }
    }
}