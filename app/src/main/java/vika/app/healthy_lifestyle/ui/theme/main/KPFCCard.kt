package vika.app.healthy_lifestyle.ui.theme.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.bean.KPFC
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun KPFCCard(
    text: String,
    progressValue: Double,
    targetValue: Double
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
            .size(110.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
            LinearProgressIndicator(
                progress = progressValue.toFloat() / targetValue.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = progressColor,
                trackColor = White
            )
            Text(
                text = "Прогресс: ${progressValue.toInt()} / ${targetValue.toInt()}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun KPFCList(
    kpfc: List <KPFC>,
    context: Context
){
    LazyRow {
        item {
            CircularProgressBar(
                text = LocalContext.current.getString(R.string.kilocalories),
                progressValue = MainActivity().getProgressKilocalories(context),
                targetValue = MainActivity().getTargetKilocalories(context),
                burnedValue = MainActivity().getBurnedKilocalories(context)
            )

            Spacer(modifier = Modifier.width(5.dp))
        }

        items(kpfc) { item ->
            KPFCCard(
                text = item.text,
                progressValue = item.progressValue,
                targetValue = item.targetValue
            )
        }
    }
}
