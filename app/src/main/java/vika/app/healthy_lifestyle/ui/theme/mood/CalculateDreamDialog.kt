package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun CalculateDreamDialog(
    onDismiss: () -> Unit,
    onCalculate: (Int, Int, Int, Int) -> Unit
) {
    val hourStartState = remember { mutableStateOf("") }
    val minuteStartState = remember { mutableStateOf("") }

    val hourEndState = remember { mutableStateOf("") }
    val minuteEndState = remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                TimeInputSection(
                    title = LocalContext.current.getString(R.string.start_dream),
                    hourState = hourStartState.value,
                    onHourChange = { hourStartState.value = it },
                    minuteState = minuteStartState.value,
                    onMinuteChange = { minuteStartState.value = it }
                )

                TimeInputSection(
                    title = LocalContext.current.getString(R.string.end_dream),
                    hourState = hourEndState.value,
                    onHourChange = { hourEndState.value = it },
                    minuteState = minuteEndState.value,
                    onMinuteChange = { minuteEndState.value = it }
                )

                ButtonBlue(text = LocalContext.current.getString(R.string.calc)) {
                    onCalculate(
                        hourStartState.value.toInt(),
                        minuteStartState.value.toInt(),
                        hourEndState.value.toInt(),
                        minuteEndState.value.toInt()
                    )
                }
            }
        }
    }
}

@Composable
fun TimeInputSection(
    title: String,
    hourState: String,
    onHourChange: (String) -> Unit,
    minuteState: String,
    onMinuteChange: (String) -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium
    )
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
                onValueChange = onHourChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Image(
                        painterResource(R.drawable.hour),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
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
                onValueChange = onMinuteChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Image(
                        painterResource(R.drawable.minute),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )
        }
    }
}
