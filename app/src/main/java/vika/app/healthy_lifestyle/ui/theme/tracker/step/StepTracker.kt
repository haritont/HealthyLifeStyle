package vika.app.healthy_lifestyle.ui.theme.tracker.step

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
            progressValue = SportActivity().getProgressSteps(context).toDouble() - 1.0,
            targetValue = 10000.0,
            burnedValue = MainActivity().getBurnedKilocalories(context),
            isStepTracker = true
        )
    }
}
