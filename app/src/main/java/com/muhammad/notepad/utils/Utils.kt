package com.muhammad.notepad.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun Long.formattedDate() : String{
    val localDate = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault()).date
    val year = localDate.year
    val month = localDate.month.name.take(3).replaceFirstChar { it.uppercase() }
    val day = localDate.day
    return "$year-$month-$day"
}