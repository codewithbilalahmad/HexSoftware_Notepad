package com.muhammad.notepad.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import com.muhammad.notepad.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(0.8f),
                        MaterialTheme.colorScheme.primary,
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null, modifier = Modifier.size(28.dp)
        )
    }
}

@Preview
@Composable
private fun AppLogoPreview() {
    AppLogo()
}