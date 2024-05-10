package vika.app.healthy_lifestyle.ui.theme.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithDialog(
    getCurrentTime: (currentTime: String) -> Unit,
    currentDate: String
) {
    val datePickerState = rememberDatePickerState()
    val showDialog = rememberSaveable { mutableStateOf(false) }
    var currentTime by remember { mutableStateOf(currentDate)}

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { showDialog.value = true }
    ){
        ImageButton(icon = R.drawable.calendar) {
            showDialog.value = true
        }
        Text(
            text = DateToday().formatDateDDMMYYYY(currentTime),
            color = Black
        )
    }

    if (showDialog.value) {
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    currentTime = formatDate(datePickerState.selectedDateMillis ?: 0)
                    getCurrentTime(currentTime)
                }) {
                    Text("Ок")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

fun formatDate(dateMillis: Long): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Date(dateMillis))
}