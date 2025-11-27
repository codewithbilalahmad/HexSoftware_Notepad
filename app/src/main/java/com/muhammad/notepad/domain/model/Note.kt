package com.muhammad.notepad.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Note(
    val id : Long?=null,
    val title : String,
    val completed : Boolean,
    val createdAt : Long
)