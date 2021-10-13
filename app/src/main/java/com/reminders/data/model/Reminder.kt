package com.reminders.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = arrayOf(
    ForeignKey(
        entity = Topic::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE)
    )
)
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "deadline")
    val deadline: Date,
    @ColumnInfo(name = "priority")
    val priority: Int,
    @ColumnInfo(name = "topicId")
    val topicId: Int
)