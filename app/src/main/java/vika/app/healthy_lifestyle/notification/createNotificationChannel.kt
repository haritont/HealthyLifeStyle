package vika.app.healthy_lifestyle.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.WorkManager

fun createNotificationChannel(context: Context) {
    val name = "ReminderChannel"
    val descriptionText = "Channel for Reminder"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel("REMINDER_CHANNEL", name, importance).apply {
        description = descriptionText
    }
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

fun rescheduleNotification(context: Context, meal: String, newHour: Int, newMinute: Int) {
    WorkManager.getInstance(context).cancelAllWorkByTag(meal)

    scheduleNotification(context, meal, newHour, newMinute)
}