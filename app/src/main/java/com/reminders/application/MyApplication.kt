package com.reminders.application

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.reminders.data.database.AppDatabase

@RequiresApi(Build.VERSION_CODES.O)
class MyApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
