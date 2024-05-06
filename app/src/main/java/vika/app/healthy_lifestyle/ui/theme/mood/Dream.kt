package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.mood.MoodActivity
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun Dream (
    hour: Int,
    minute: Int
) {
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
    ){
        Text(
            text = LocalContext.current.getString(
                R.string.dream
            ),
            fontWeight = FontWeight.Bold
        )

        val hourState = remember { mutableIntStateOf(hour) }
        val minuteState = remember { mutableIntStateOf(minute) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                TextFieldBlue(
                    value = hourState.intValue.toString(),
                    label = {
                        Text(
                            LocalContext.current.getString(R.string.hour),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> hourState.intValue = newLogin.toInt() },
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
                    value = minuteState.intValue.toString(),
                    label = {
                        Text(
                            LocalContext.current.getString(R.string.minute),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> minuteState.intValue = newLogin.toInt() },
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
                hourState.intValue,
                minuteState.intValue
            )
        }
    }
}