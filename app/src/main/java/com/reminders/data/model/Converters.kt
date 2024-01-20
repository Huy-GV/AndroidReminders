package com.reminders.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(date: Long?): LocalDate? {
        return if (date == null || date == 0L) {
            null
        } else {
            LocalDate.ofEpochDay(date)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDateString(date: LocalDate?): Long {
        return date?.toEpochDay() ?: 0
    }
}