package vika.app.healthy_lifestyle.ui.theme.tracker.step

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    LaunchedEffect(Unit) {
        val intent = Intent(context, StepCounterService::class.java)
        context.startForegroundService(intent)
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
    }
}
