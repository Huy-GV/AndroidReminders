package com.reminders.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
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
    var content: String,
    @ColumnInfo(name = "deadline")
    var deadline: LocalDate?,
    @ColumnInfo(name = "priority")
    var priority: Int,
    @ColumnInfo(name = "topicId")
    val topicId: Int
) : Parcelable