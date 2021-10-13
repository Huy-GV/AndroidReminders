package com.reminders.data.dao

import androidx.room.*
import com.reminders.data.model.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert
    suspend fun create(topic: Topic)
    @Update
    suspend fun update(topic: Topic)
    @Delete
    suspend fun delete(topic: Topic)
    @Query("SELECT * from topic ORDER BY name ASC")
    fun getAll(): Flow<List<Topic>>
    @Query("SELECT * from topic WHERE id = :id")
    fun getTopic(id: Int): Flow<Topic>
}