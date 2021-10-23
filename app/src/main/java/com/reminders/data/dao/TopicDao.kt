package com.reminders.data.dao

import androidx.room.*
import com.reminders.data.model.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert
    suspend fun create(topic: Topic)
//    @Update
//    suspend fun update(topic: Topic)
    @Query("UPDATE topic SET name = :name WHERE id = :topicId")
    suspend fun update(topicId: Int, name: String)
//    @Delete
//    suspend fun delete(topic: Topic)
    @Query("DELETE FROM topic WHERE id = :topicId")
    suspend fun delete(topicId: Int)
    @Query("SELECT * from topic ORDER BY name ASC")
    fun getAll(): Flow<List<Topic>>
    @Query("SELECT * from topic WHERE id = :id")
    fun getTopic(id: Int): Flow<Topic>

    @Query("SELECT COUNT(*) FROM reminder WHERE reminder.topicId = :id")
    fun getReminderCount(id: Int): Flow<Int>
}