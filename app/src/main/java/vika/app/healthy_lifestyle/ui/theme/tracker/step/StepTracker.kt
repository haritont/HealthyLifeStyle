package vika.app.healthy_lifestyle.ui.theme.tracker.step

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.ui.theme.main.CircularProgressBar

@Composable
fun StepTracker() {
    val context = LocalContext.current
    var isTrack by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val intent = Intent(context, StepCounterService::class.java)
        context.startForegroundService(intent)
        isTrack = true
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressBar(
            text ="Шаги",
            progressValue = SportActivity().getProgressSteps(context).toDouble(),
            targetValue = 10000.0,
            burnedValue = MainActivity().getBurnedKilocalories(context)
        )

        Spacer(modifier = Modifier.height(16.dp))

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
