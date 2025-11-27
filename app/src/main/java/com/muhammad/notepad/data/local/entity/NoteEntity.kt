package com.muhammad.notepad.data.local.entity

import androidx.room.*

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val completed: Boolean,
    val createdAt : Long
)
