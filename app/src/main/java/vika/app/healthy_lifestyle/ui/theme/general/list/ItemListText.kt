package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.White
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun ItemListText(
    title: String,
    add: (String, Double) -> Unit,
    textInDialog: String
){
    var openDialog by remember { mutableStateOf(false) }
    var valueState by remember { mutableStateOf("") }

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
            }
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            )
            {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = textInDialog,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    TextFieldBlue(
                        value = valueState,
                        onValueChange = { newValue -> valueState = newValue },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Row {
                        TextButton(
                            onClick = {
                                if (valueState != "") {
                                    add(
                                        title,
                                        valueState.replace(",", ".").toDouble()
                                    )
                                    openDialog = false
                                }
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Добавить")
                        }
                        TextButton(
                            onClick = { openDialog = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Отмена")
                        }
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .shadow(3.dp, RoundedCornerShape(5.dp))
            .background(
                color = White,
                shape = RoundedCornerShape(5.dp)
            )
            .border(
                width = 1.dp,
                color = BlueUltraLight,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable { openDialog = true }
            .width(300.dp)
            .height(50.dp)
    )
    {
        Row(
            horizontalArrangement = Arrangement.Absolute.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Black,
                maxLines = 3
            )
        }
    }
}