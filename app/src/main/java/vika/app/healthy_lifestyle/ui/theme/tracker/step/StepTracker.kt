package vika.app.healthy_lifestyle.ui.theme.tracker.step

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.ui.theme.main.CircularProgressBar

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun StepTracker() {
    val context = LocalContext.current
    var isTrack by remember { mutableStateOf(true) }
    var hasPermission by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                1
            )
        } else {
            hasPermission = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasPermission = true
            val intent = Intent(context, StepCounterService::class.java)
            context.startForegroundService(intent)
            isTrack = true
        } else {
            isTrack = false
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            val intent = Intent(context, StepCounterService::class.java)
            context.startForegroundService(intent)
            isTrack = true
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isTrack) {
            CircularProgressBar(
                text = "Шаги",
                progressValue = SportActivity().getProgressSteps(context).toDouble(),
                targetValue = 10000.0,
                burnedValue = MainActivity().getBurnedKilocalories(context),
                isStepTracker = true
            )
        }
        else{
            permissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }
}
