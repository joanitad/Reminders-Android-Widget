package com.reminders.reminderwidget

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.color.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.reminders.reminderwidget.data.ReminderRepository

object ReminderPref {
    val reminder = stringPreferencesKey("reminder")
}
class ReminderWidget: GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            val state = currentState<Preferences>()
            val currentReminder = state[ReminderPref. reminder]
            Box (modifier = GlanceModifier.clickable( actionRunCallback< GetReminderCallback>() ).padding(4.dp)) {
                Text(currentReminder ?: "Breathe", style = TextStyle(
                    color = ColorProvider(Color.White, Color.White),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center


                ))
            }
        }
    }

}

class GetReminderCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val repository = ReminderRepository(context)
        val reminder = repository.getRandomReminder()

        updateAppWidgetState(context, glanceId) { pref: MutablePreferences ->
            pref [ReminderPref.reminder] = reminder
        }

        ReminderWidget().update(context, glanceId)
    }

}