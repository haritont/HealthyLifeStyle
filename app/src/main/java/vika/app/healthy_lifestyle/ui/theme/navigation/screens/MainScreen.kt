package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.bean.KPFC
import vika.app.healthy_lifestyle.ui.theme.main.KPFCList
import vika.app.healthy_lifestyle.ui.theme.tracker.water.WaterTracker
import vika.app.healthy_lifestyle.ui.theme.tracker.weight.WeightTracker

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainScreen () {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            val kpfc = listOf(
                KPFC(
                    text = LocalContext.current.getString(R.string.proteins),
                    progressValue = MainActivity().getProgressProteins(context),
                    targetValue = MainActivity().getTargetProteins(context)
                ),
                KPFC(
                    text = LocalContext.current.getString(R.string.fats),
                    progressValue = MainActivity().getProgressFats(context),
                    targetValue = MainActivity().getTargetFats(context)
                ),
                KPFC(
                    text = LocalContext.current.getString(R.string.carbohydrates),
                    progressValue = MainActivity().getProgressCarbohydrates(context),
                    targetValue = MainActivity().getTargetCarbohydrates(context)
                )
            )
            KPFCList(kpfc, context)

            Spacer(modifier = Modifier.height(10.dp))

            WeightTracker(MainActivity().getWeight(context))

            Spacer(modifier = Modifier.height(10.dp))

            WaterTracker(
                currentValue = MainActivity().getCurrentValueWater(context),
                targetValue = MainActivity().getTargetValueWater(context)
                )
        }
    }
}


