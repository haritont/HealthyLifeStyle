package vika.app.healthy_lifestyle.ui.theme.tracker.water

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import vika.app.healthy_lifestyle.activity.main.MainActivity

@Composable
fun WaterItem(
    water: Water
) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(
        spec = if (water.isStart) {
            LottieCompositionSpec.Asset("water_add.json")
        } else {
            LottieCompositionSpec.Asset("water.json")
        }
    )
    var isPlaying by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) {
            isPlaying = true
        }
        if (progress == 1f) {
            isPlaying = false
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .clickable(
                    onClick = {
                        if (water.isStart) {
                            isPlaying = true
                            water.onAddValue(water.value)
                            MainActivity().onClickWater(context, water.value)
                        }
                    }
                )
                .size(40.dp),
            progress = { progress }
        )
    }
}
