package vika.app.healthy_lifestyle.ui.theme.tracker.weight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton

@Composable
fun  WeightTracker(
    currentWeight: Double
) {
    val context = LocalContext.current
    val weightState = remember { mutableDoubleStateOf(currentWeight) }
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = LocalContext.current.getString(R.string.weight),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .background(
                    color = BlueLight,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
                .width(150.dp)
        ) {
            ImageButton(icon = R.drawable.minus) {
                weightState.doubleValue -= 0.1
                MainActivity().saveWeight(weightState.doubleValue, context)
            }
            Text(
                text = "%.1f".format(weightState.doubleValue)
            )
            ImageButton(icon = R.drawable.add) {
                weightState.doubleValue += 0.1
                MainActivity().saveWeight(weightState.doubleValue, context)
            }
        }
    }
}