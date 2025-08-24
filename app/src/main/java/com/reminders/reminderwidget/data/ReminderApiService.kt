package com.reminders.reminderwidget.data


interface ReminderApiService {
    suspend fun getRandomReminder(): String
}