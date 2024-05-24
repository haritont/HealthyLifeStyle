package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.White
import vika.app.healthy_lifestyle.ui.theme.food.MoreDish
import vika.app.healthy_lifestyle.ui.theme.food.MoreIngredient
import vika.app.healthy_lifestyle.ui.theme.general.DatePickerWithDialog
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.general.emojiMap
import vika.app.healthy_lifestyle.ui.theme.sport.MorePhysicalExercise

@Composable
fun ItemList(
    title: String,
    emoji: String,
    favorite: Boolean,
    exception: Boolean,
    add: (name: String, value: Double, date: String, option: String) -> Unit,
    typeToMore: Int,
    updateException: (name: String, exception: Boolean) -> Unit,
    updateFavorite: (name: String, favorite: Boolean) -> Unit,
    textInDialog: String,
    options: List<String>,
    firstOption: String
) {
    var favoriteState by remember { mutableStateOf(favorite) }
    var exceptionState by remember { mutableStateOf(exception) }

    var openDialogAdd by remember { mutableStateOf(false) }
    var valueState by remember { mutableStateOf("") }
    var currentDate = DateToday().getToday()
    var currentOptionSelect = firstOption

    var openDialogMore by remember { mutableStateOf(false) }
    var openDialogMoreIngredient by remember { mutableStateOf(false) }
    var openDialogMoreDish by remember { mutableStateOf(false) }
    var openDialogMorePhysicalExercise by remember { mutableStateOf(false) }

    if (openDialogMore){
        MoreIngredient(
            isOpen = openDialogMoreIngredient,
            onOpenChange = {
                openDialogMore = it
                           },
            title = title
        )

        MoreDish(
            isOpen = openDialogMoreDish,
            onOpenChange = {
            openDialogMore = it
        },
            title = title
        )

        MorePhysicalExercise(
            isOpen = openDialogMorePhysicalExercise,
            onOpenChange = {
                openDialogMore = it
            },
            title = title
        )

        when (typeToMore) {
            0 -> {
                openDialogMoreIngredient = true
            }
            1 -> {
                openDialogMoreDish = true
            }
            2 -> {
                openDialogMorePhysicalExercise = true
            }
            3 -> {

            }
        }
    }

    if (openDialogAdd) {
        Dialog(
            onDismissRequest = { openDialogAdd = !openDialogAdd }
        ) {
            Card(
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = textInDialog,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (options.isNotEmpty()) {
                            Dropdown(
                                options,
                                firstOption
                            ) { currentOption ->
                                currentOptionSelect = currentOption
                            }
                        }
                        DatePickerWithDialog(
                            currentDate = currentDate,
                            getCurrentTime = {
                                currentTime -> currentDate = currentTime
                            }
                        )
                    }
                    TextFieldBlue(
                        value = valueState,
                        onValueChange = { newValue -> valueState = newValue },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                add(
                                    title,
                                    valueState.replace(",", ".").toDouble(),
                                    currentDate,
                                    currentOptionSelect
                                )
                                openDialogAdd = !openDialogAdd
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Добавить")
                        }
                        TextButton(
                            onClick = { openDialogAdd = false },
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
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .clickable { openDialogAdd = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = emojiMap.getValue(emoji)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    maxLines = 3
                )
            }

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                exceptionState = !exceptionState
                                updateException(title, exceptionState)
                            })
                        .size(35.dp)
                        .padding(8.dp),
                    painter = painterResource(if (!exceptionState) R.drawable.exception_false else R.drawable.exception_true),
                    contentDescription = null
                )

                Image(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                favoriteState = !favoriteState
                                updateFavorite(title, favoriteState)
                            })
                        .size(35.dp)
                        .padding(8.dp),
                    painter = painterResource(if (!favoriteState) R.drawable.like_false else R.drawable.like_true),
                    contentDescription = null
                )

                ImageButton(icon = R.drawable.add) {
                    openDialogAdd = true
                }
                ImageButton(icon = R.drawable.more) {
                    openDialogMore = true
                }

            }
        }
    }
}

