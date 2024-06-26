package vika.app.healthy_lifestyle.ui.theme.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun Dropdown(
    options: List<String>,
    firstOption: String,
    getCurrentOption: (currentOption: String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(firstOption) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(5.dp)
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .border(
                width = 4.dp,
                color = BlueUltraLight,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = White
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(8.dp)
        ) {
            Text(
                selectedOption,
                color = Black
            )
            Image(
                modifier = Modifier
                    .size(35.dp)
                    .padding(8.dp),
                painter = painterResource(R.drawable.bottom),
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = Black
                        )
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        getCurrentOption(option)
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = White
                        )
                )
            }
        }
    }
}

