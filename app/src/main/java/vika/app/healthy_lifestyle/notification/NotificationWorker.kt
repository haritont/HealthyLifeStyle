package vika.app.healthy_lifestyle.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.LoadingActivity
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val meal = inputData.getString("MEAL") ?: return Result.failure()

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationIntent = Intent(applicationContext, LoadingActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(applicationContext, "REMINDER_CHANNEL")
            .setSmallIcon(R.drawable.snackbar)
            .setContentTitle("Напоминание")
            .setContentText(meal)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(meal.hashCode(), notification)

        return Result.success()
    }
}

fun scheduleNotification(context: Context, meal: String, hour: Int, minute: Int) {
    val now = Calendar.getInstance()
    val notificationTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    if (now.after(notificationTime)) {
        notificationTime.add(Calendar.DAY_OF_MONTH, 1)
    }

    val delay = notificationTime.timeInMillis - now.timeInMillis

    val workRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .setInputData(Data.Builder().putString("MEAL", meal).build())
        .addTag(meal)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}