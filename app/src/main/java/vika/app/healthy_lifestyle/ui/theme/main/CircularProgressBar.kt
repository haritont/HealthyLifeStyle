package vika.app.healthy_lifestyle.ui.theme.main

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.app.White
import vika.app.healthy_lifestyle.ui.theme.tracker.step.StepCounterService

@Composable
fun CircularProgressBar(
    text: String,
    progressValue: Double,
    targetValue: Double,
    burnedValue: Double,
    isStepTracker: Boolean = false
) {
    val context = LocalContext.current
    var isTrack by remember { mutableStateOf(true) }

    val progressColor = if (progressValue >= targetValue) {
        if (isStepTracker){
            Green
        }
        else {
            Red
        }
    } else {
        Blue
    }

    Surface(
        color = BlueUltraLight,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(5.dp)
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .size(170.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold
                )
                if (isStepTracker) {
                    Image(
                        painterResource(
                            if (!isTrack) R.drawable.start
                            else R.drawable.stop
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                if (isTrack) {
                                    val intent = Intent(context, StepCounterService::class.java)
                                    context.stopService(intent)
                                } else {
                                    val intent = Intent(context, StepCounterService::class.java)
                                    context.startForegroundService(intent)
                                }
                                isTrack = !isTrack
                            }
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = progressValue.toFloat() / targetValue.toFloat(),
                    modifier = Modifier.size(100.dp),
                    color = progressColor,
                    strokeWidth = 10.dp,
                    trackColor = White
                )
                Text(
                    text = "${progressValue.toInt()} / ${targetValue.toInt()}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }
            Text(
                text = "Сожжено: ${burnedValue.toInt()} ккал",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}
