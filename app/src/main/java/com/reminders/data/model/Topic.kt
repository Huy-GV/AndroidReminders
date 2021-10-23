package com.reminders.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = "topic")
data class Topic (
    @PrimaryKey(autoGenerate = true)
    val id: Int= 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date")
    val creationDate: LocalDate,
    @ColumnInfo(name = "color")
    val color: Int
)