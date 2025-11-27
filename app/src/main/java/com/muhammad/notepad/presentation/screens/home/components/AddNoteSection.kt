package com.muhammad.notepad.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.muhammad.notepad.R
import com.muhammad.notepad.presentation.components.AppTextField

@Composable
fun AddNoteSection(
    modifier: Modifier = Modifier,
    isCreateNoteEnable: Boolean,
    onNoteQueryChange: (String) -> Unit,
    note: String,
    onAddNote: () -> Unit,
) {
    val density = LocalDensity.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var fabHeight by remember {
        mutableStateOf(0.dp)
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppTextField(
            value = note, onValueChange = onNoteQueryChange,
            modifier = Modifier
                .weight(1f)
                .height(fabHeight),
            hint = R.string.whats_doing,
            onKeyBoardAction = {
                if (isCreateNoteEnable) {
                    onAddNote()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            }
        )
        FloatingActionButton(
            onClick = {
                if (isCreateNoteEnable) {
                    onAddNote()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            }, shape = CircleShape,
            modifier = Modifier
                .alpha(if (isCreateNoteEnable) 1f else 0.4f)
                .onGloballyPositioned { coordinates ->
                    fabHeight = with(density) {
                        coordinates.size.height.toDp()
                    }
                },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                contentDescription = null
            )
        }
    }
}