package com.reminders.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [ForeignKey(
    entity = Topic::class,
    parentColumns = ["id"],
    childColumns = ["topicId"],
    onDelete = ForeignKey.CASCADE)]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "deadline")
    val deadline: Date?,
    @ColumnInfo(name = "priority")
    val priority: Int,
    @ColumnInfo(name = "topicId")
    val topicId: Int
)