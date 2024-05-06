package vika.app.healthy_lifestyle.ui.theme.tracker.step

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.ui.theme.main.CircularProgressBar

@Composable
fun StepTracker() {
    val context = LocalContext.current
    var stepValue by remember { mutableStateOf(0) }
    var lastStep by remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        val stepSensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (stepValue == 0){
                    lastStep = event.values[0].toInt()
                }
                stepValue = event.values[0].toInt() - lastStep + 1
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(stepSensorListener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(stepSensorListener)
        }
    }


    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressBar(
            text ="Шаги",
            progressValue = stepValue.toDouble(),
            targetValue = 10000.0,
            burnedValue = MainActivity().getBurnedKilocalories(context)
        )
    }
}