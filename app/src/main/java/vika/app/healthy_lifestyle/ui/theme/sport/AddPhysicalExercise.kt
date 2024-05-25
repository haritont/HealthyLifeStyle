package vika.app.healthy_lifestyle.ui.theme.sport

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.RedLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun AddPhysicalExercise(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit
){
    var openDialog by remember { mutableStateOf(isOpen) }

    val context = LocalContext.current

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        openDialog = isOpen
        onDispose { }
    }

    val nameState = remember { mutableStateOf("") }
    val metState = remember { mutableStateOf("") }

    val options = listOf(
        "Без типа"
    )
    val typeState = remember { mutableStateOf(options[0]) }

    var colorName by remember {
        mutableStateOf(Color.Transparent)
    }

    var colorMet by remember {
        mutableStateOf(Color.Transparent)
    }

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
                onOpenChange(openDialog)
            }
        ) {
            Card(
                modifier = Modifier
                    .height(400.dp)
                    .width(450.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                )
                {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Dropdown(
                            options,
                            options[0]
                        ) {
                                currentOption ->
                            typeState.value = currentOption
                        }

                        Text(
                            text = "Добавить упражнение",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .border(3.dp, colorName, RoundedCornerShape(10.dp))
                    ) {
                        TextFieldBlue(
                            value = nameState.value,
                            label = {
                                Text(
                                    LocalContext.current.getString(R.string.input_name),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onValueChange = { newLogin -> nameState.value = newLogin },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            leadingIcon = {
                                Image(
                                    painterResource(R.drawable.sport),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .border(3.dp, colorMet, RoundedCornerShape(10.dp))
                    ) {
                        TextFieldBlue(
                            value = metState.value,
                            label = {
                                Text(
                                    LocalContext.current.getString(R.string.met),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onValueChange = { newLogin -> metState.value = newLogin },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = {
                                Image(
                                    painterResource(R.drawable.sport),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                var check = true
                                if (nameState.value == ""){
                                    check = false
                                    colorName = RedLight
                                }
                                if (metState.value == ""){
                                    check = false
                                    colorMet = RedLight
                                }
                                if (check) {
                                    SportActivity().insertPhysicalExercise(
                                        context,
                                        nameState.value,
                                        metState.value.replace(",", ".").toDouble(),
                                        typeState.value
                                    )
                                    openDialog = !openDialog
                                    onOpenChange(openDialog)
                                }
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Ок")
                        }
                        TextButton(
                            onClick = {
                                openDialog = false
                                onOpenChange(openDialog)
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Отмена")
                        }
                    }
                }
            }
        }
    }
}
