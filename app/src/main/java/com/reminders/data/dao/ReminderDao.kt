package com.reminders.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.reminders.data.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert
    suspend fun create(reminder: Reminder)

    @Update
    suspend fun update(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM reminder WHERE topicId = :topicId ORDER BY deadline DESC, priority DESC")
    fun getAll(topicId: Int): Flow<List<Reminder>>

    @Query("SELECT * FROM reminder WHERE id = :id")
    fun getReminder(id: Int): Flow<Reminder>
}