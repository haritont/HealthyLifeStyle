package vika.app.healthy_lifestyle.ui.theme.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun CircularProgressBar(
    text: String,
    progressValue: Double,
    targetValue: Double,
    burnedValue: Double
) {
    val progressColor = if (progressValue >= targetValue) {
        Red
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
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progressValue.toFloat() / targetValue.toFloat() },
                    modifier = Modifier
                        .size(100.dp),
                    trackColor = White,
                    color = progressColor,
                    strokeWidth = 10.dp
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
