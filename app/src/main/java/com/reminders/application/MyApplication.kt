package com.reminders.application

import android.app.Application
import com.reminders.data.database.AppDatabase


class MyApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
