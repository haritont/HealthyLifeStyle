package vika.app.healthy_lifestyle.ui.theme.tracker.water

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun WaterTracker(
    currentValue: Int
) {
    var currentValueState by remember{ mutableIntStateOf(currentValue) }
    Column(
        modifier = Modifier.border(
            width = 4.dp,
            color = Blue,
            shape = RoundedCornerShape(10.dp)
        ).padding(15.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.background(
                color = BlueLight,
                shape = RoundedCornerShape(10.dp)
            ).
        padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = LocalContext.current.getString(R.string.water_today)
                    .plus(currentValueState.toString())
                    .plus(LocalContext.current.getString(R.string.milliliter_abbreviation))
            )
        }

        Row(
            modifier = Modifier.background(
                White
            ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WaterItem(50,
                onAddValue = {value -> currentValueState += value}
            )
            WaterItem(100,
                onAddValue = {value -> currentValueState += value}
            )
            WaterItem(250,
                onAddValue = {value -> currentValueState += value}
            )
        }

        Row(
            modifier = Modifier.background(
                White
            ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WaterItem(300,
                onAddValue = {value -> currentValueState += value}
            )
            WaterItem(750,
                onAddValue = {value -> currentValueState += value}
            )
            WaterItem(1000,
                onAddValue = {value -> currentValueState += value}
            )
        }
    }
}