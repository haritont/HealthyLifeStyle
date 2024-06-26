package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.mood.MoodActivity
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun Dream (
    hour: Int,
    minute: Int
) {
    var openDialogCalculateDream by remember { mutableStateOf(false) }
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
    ){
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = LocalContext.current.getString(
                    R.string.dream
                ),
                fontWeight = FontWeight.Bold
            )
            ImageButton(
                icon = R.drawable.calculate
            ) {
                openDialogCalculateDream = true
            }
        }

        var hourState by remember { mutableStateOf(hour.toString()) }
        var minuteState by remember { mutableStateOf(minute.toString()) }

        if (openDialogCalculateDream) {
            CalculateDreamDialog(
                onDismiss = { openDialogCalculateDream = false },
                onCalculate = { hourStart, minuteStart, hourEnd, minuteEnd ->
                    val sleep = calculateSleepDuration(hourStart, minuteStart, hourEnd, minuteEnd)
                    hourState = sleep.first.toString()
                    minuteState = sleep.second.toString()
                    MoodActivity().addDream(hourState.toInt(), minuteState.toInt())
                    openDialogCalculateDream = false
                }
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                TextFieldBlue(
                    value = hourState,
                    label = {
                        Text(
                            LocalContext.current.getString(R.string.hour),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> hourState = newLogin },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Image(
                            painterResource(R.drawable.hour),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                TextFieldBlue(
                    value = minuteState,
                    label = {
                        Text(
                            LocalContext.current.getString(R.string.minute),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> minuteState = newLogin },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Image(
                            painterResource(R.drawable.minute),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                )
            }
        }

        ButtonBlue(text = LocalContext.current.getString(R.string.add)) {
            MoodActivity().addDream(
                hourState.toInt(),
                minuteState.toInt()
            )
        }
    }
}

fun calculateSleepDuration(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int): Pair<Int, Int> {
    val startTotalMinutes = startHour * 60 + startMinute
    val endTotalMinutes = endHour * 60 + endMinute

    val durationMinutes = if (endTotalMinutes >= startTotalMinutes) {
        endTotalMinutes - startTotalMinutes
    } else {
        (24 * 60 - startTotalMinutes) + endTotalMinutes
    }

    val hours = durationMinutes / 60
    val minutes = durationMinutes % 60

    return Pair(hours, minutes)
}