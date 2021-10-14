package com.reminders.data.dao

import androidx.room.*
import com.reminders.data.model.Reminder
import com.reminders.data.model.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert
    suspend fun create(reminder: Reminder)
    @Update
    suspend fun update(reminder: Reminder)
    @Delete
    suspend fun delete(reminder: Reminder)
    @Query("SELECT * from reminder WHERE topicId = :topicId ORDER BY deadline DESC")
    fun getAll(topicId: Int): Flow<List<Reminder>>
    @Query("SELECT * from reminder WHERE id = :id")
    fun getReminder(id: Int): Flow<Reminder>
}