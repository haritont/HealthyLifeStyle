package vika.app.healthy_lifestyle.ui.theme.tracker.water

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight

@Composable
fun WaterTracker(
    currentValue: Int,
    targetValue: Int
) {
    var currentValueState by remember { mutableIntStateOf(currentValue) }
    val waters = remember { mutableStateListOf<Water>() }

    if (waters.isEmpty()) {
        waters.add(
            Water(
                value = 100,
                onAddValue = { value ->
                    currentValueState += value
                    waters.add(Water(value = 100, onAddValue = {}, isStart = false))
                },
                isStart = true
            )
        )
        val currentWaters = currentValue / 100

        for (count in 1..currentWaters) {
            waters.add(Water(value = 100, onAddValue = {}, isStart = false))
        }
    }

    Column(
        modifier = Modifier
            .border(
                width = 4.dp,
                color = Blue,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(15.dp)
            .size(250.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = BlueLight,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentValueState.toString()
                    .plus(LocalContext.current.getString(R.string.milliliter_abbreviation)).plus(" из ").plus(targetValue.toString()).plus(LocalContext.current.getString(R.string.milliliter_abbreviation))
            )
        }
        
        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 60.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(waters) { water ->
                WaterItem(water)
            }
        }
    }
}

data class Water(val value: Int, val onAddValue: (Int) -> Unit, val isStart: Boolean)