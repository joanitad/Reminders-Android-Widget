package com.reminders.reminderwidget.data

import android.content.Context
import com.reminders.R
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

class ReminderRepository(context: Context) : ReminderApiService {

    private val reminders: List<String>

    init {
        reminders = loadRemindersFromRaw(context, R.raw.reminders)
    }

    private fun loadRemindersFromRaw(context: Context, resourceId: Int): List<String> {
        val reminderList = mutableListOf<String>()
        try {
            context.resources.openRawResource(resourceId).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
                    lines.forEach { line ->
                        val trimmed = line.trim()
                        if (trimmed.isNotEmpty()) {
                            reminderList.add(trimmed)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return reminderList
    }

    override suspend fun getRandomReminder(): String {
        return if (reminders.isEmpty()) {
            "No reminders found."
        } else {
            reminders[Random.nextInt(reminders.size)]
        }
    }
}
