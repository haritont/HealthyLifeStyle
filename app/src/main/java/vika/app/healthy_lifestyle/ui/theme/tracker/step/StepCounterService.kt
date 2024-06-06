package vika.app.healthy_lifestyle.ui.theme.tracker.step

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.sport.SportActivity

class StepCounterService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sharedPreferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        startForeground(1, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        var stepValue = SportActivity().getProgressSteps(this)
        var lastStep = sharedPreferences.getString("lastStep", "0")

        if (stepValue == 0){
            lastStep = event.values[0].toInt().toString()
            editor.putString("lastStep", lastStep)
            editor.apply()
        }
        stepValue = event.values[0].toInt() - lastStep.toString().toInt() + 1

        SportActivity().saveProgressSteps(this, stepValue)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun createNotification(): Notification {
        val notificationChannelId = "STEP_COUNTER_CHANNEL"
        val notificationChannel = NotificationChannel(
            notificationChannelId,
            "Step Counter Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)

        val builder = NotificationCompat.Builder(this, notificationChannelId)
        builder.setSmallIcon(R.drawable.step)
            .setContentTitle("Шагомер")
            .setContentText("Считаю ваши шаги...")
            .setOngoing(true)

        return builder.build()
    }
}
