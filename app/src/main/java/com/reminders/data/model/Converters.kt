package com.reminders.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(date: Long?): LocalDate? {
        if (date == null || date == 0L) {
            return null
        }

        return LocalDate.ofEpochDay(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDateString(date: LocalDate?): Long {
        if (date == null) {
            return 0
        }

        return date.toEpochDay()
    }
}