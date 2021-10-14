  package com.reminders

import androidx.lifecycle.*
import androidx.room.Room
import com.reminders.data.dao.*
import com.reminders.data.database.AppDatabase
import com.reminders.data.model.*
import kotlinx.coroutines.launch
import java.util.*

class AppViewModel(
    private val topicDao: TopicDao,
    private val reminderDao: ReminderDao

) : ViewModel() {
    fun getTopics() : LiveData<List<Topic>> {
        return topicDao.getAll().asLiveData()
    }

    fun createTopic(name: String, creationDate: Date) {
        viewModelScope.launch {
            topicDao.create(Topic(name = name, creationDate = creationDate))
        }
    }

    fun getReminders(topicId: Int) : LiveData<List<Reminder>> {
        return reminderDao.getAll(topicId).asLiveData()
    }

    fun createReminder(content: String, deadline: Date, priority: Int, topicId: Int) {
        viewModelScope.launch {
            reminderDao.create(Reminder(
                content = content,
                deadline = deadline,
                priority = priority,
                topicId = topicId
            ))
        }
    }



    class Factory(
        private val reminderDao: ReminderDao,
        private val topicDao: TopicDao
        ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(topicDao, reminderDao) as T
        }
    }

}