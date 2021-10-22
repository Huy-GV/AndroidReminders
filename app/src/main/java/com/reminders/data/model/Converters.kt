package com.reminders.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        if (dateString.isNullOrEmpty()) return null
        return LocalDate.parse(dateString)
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String {
        if (date == null) return ""
        return date.toString()
    }
}