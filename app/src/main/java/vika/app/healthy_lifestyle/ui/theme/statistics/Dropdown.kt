package vika.app.healthy_lifestyle.ui.theme.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun Dropdown(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Ккал") },
                onClick = {
                    onOptionSelected("Ккал")
                    expanded = false
                })
            DropdownMenuItem(
                text = { Text("Вода") },
                onClick = {
                    onOptionSelected("Вода")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Белки") },
                onClick = {
                    onOptionSelected("Белки")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Жиры") },
                onClick = {
                    onOptionSelected("Жиры")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Углеводы") },
                onClick = {
                    onOptionSelected("Углеводы")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Спорт") },
                onClick = {
                    onOptionSelected("Спорт")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Вес") },
                onClick = {
                    onOptionSelected("Вес")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Сон") },
                onClick = {
                    onOptionSelected("Сон")
                    expanded = false
                }
            )
        }
    }

    Box(
        modifier = Modifier
            .clickable { expanded = true }
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Text(selectedOption, color = White)
    }
}