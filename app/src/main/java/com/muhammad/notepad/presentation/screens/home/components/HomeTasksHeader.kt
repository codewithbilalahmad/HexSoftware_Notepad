package com.muhammad.notepad.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.notepad.R
import com.muhammad.notepad.presentation.components.AppLogo
import com.muhammad.notepad.presentation.components.AppProgressBar

@Composable
fun HomeTasksHeader(modifier: Modifier = Modifier, totalTasks: Int, completedTasks: Int) {
    val progress = if(totalTasks > 0) completedTasks.toFloat() / totalTasks.toFloat() else 0f
    val percentage = (progress * 100).toInt()
    Column(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppLogo()
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.today_task),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = "$completedTasks of $totalTasks ${stringResource(R.string.completed)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppProgressBar(
                progress = progress,
                modifier = Modifier.weight(1f)
            )
            Text(text = "$percentage%", style = MaterialTheme.typography.bodyMedium)
        }
    }
}