package vika.app.healthy_lifestyle.ui.theme.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.RedLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun AddIngredient(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit
) {
    var openDialog by remember { mutableStateOf(isOpen) }

    val context = LocalContext.current

    val nameState = remember { mutableStateOf("") }
    val kilocaloriesState = remember { mutableStateOf("") }
    val proteinsState = remember { mutableStateOf("") }
    val fatsState = remember { mutableStateOf("") }
    val carbohydratesState = remember { mutableStateOf("") }

    val options = listOf(
        "Без типа",
        "Напиток",
        "Фрукт",
        "Сладкое",
        "Приправа",
        "Алкоголь",
        "Дичь",
        "Рыба",
        "Орех",
        "Ягода",
        "Вода",
        "Овощ",
        "Мучное",
        "Зелень",
        "Соус",
        "Уксус",
        "Мясо",
        "Субпродукт",
        "Сыр",
        "Боб",
        "Крупа",
        "Гриб",
        "Молочное",
        "Масло",
        "Яйцо"
    )
    val typeState = remember { mutableStateOf(options[0]) }

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        onOpenChange(openDialog)
        onDispose { }
    }

    var colorName by remember {
        mutableStateOf(Color.Transparent)
    }

    var colorKilo by remember {
        mutableStateOf(Color.Transparent)
    }

    var colorProtein by remember {
        mutableStateOf(Color.Transparent)
    }

    var colorFat by remember {
        mutableStateOf(Color.Transparent)
    }

    var colorCarb by remember {
        mutableStateOf(Color.Transparent)
    }

    if (isOpen) {
        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
                onOpenChange(openDialog)
            }
        ) {
            Card(
                modifier = Modifier
                    .size(500.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                )
                {
                    Text(
                        text = "Добавить ингредиент",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

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
                                    painterResource(R.drawable.ingredient),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .border(3.dp, colorKilo, RoundedCornerShape(10.dp))
                                .weight(1f)
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                TextFieldBlue(
                                    value = kilocaloriesState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.kilocalories),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { newLogin ->
                                        kilocaloriesState.value = newLogin
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    leadingIcon = {
                                        Image(
                                            painterResource(R.drawable.kilocalories),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                )
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .border(3.dp, colorProtein, RoundedCornerShape(10.dp))
                                .weight(1f)
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                TextFieldBlue(
                                    value = proteinsState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.proteins),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { newLogin -> proteinsState.value = newLogin },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    leadingIcon = {
                                        Image(
                                            painterResource(R.drawable.proteins),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .border(3.dp, colorFat, RoundedCornerShape(10.dp))
                                .weight(1f)
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                TextFieldBlue(
                                    value = fatsState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.fats),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { newLogin -> fatsState.value = newLogin },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    leadingIcon = {
                                        Image(
                                            painterResource(R.drawable.fats),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .border(3.dp, colorCarb, RoundedCornerShape(10.dp))
                                .weight(1f)
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                TextFieldBlue(
                                    value = carbohydratesState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.carbohydrates),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { newLogin ->
                                        carbohydratesState.value = newLogin
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    leadingIcon = {
                                        Image(
                                            painterResource(R.drawable.carbohydrates),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Dropdown(
                        options,
                        options[0]
                    ) {
                        currentOption ->
                        typeState.value = currentOption
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
                                if (kilocaloriesState.value == ""){
                                    check = false
                                    colorKilo = RedLight
                                }
                                if (proteinsState.value == ""){
                                    check = false
                                    colorProtein = RedLight
                                }
                                if (fatsState.value == ""){
                                    check = false
                                    colorFat = RedLight
                                }
                                if (carbohydratesState.value == ""){
                                    check = false
                                    colorCarb = RedLight
                                }
                                if (check) {
                                    FoodActivity().addNewIngredient(
                                        context,
                                        nameState.value,
                                        kilocaloriesState.value.replace(",", ".").toDouble(),
                                        proteinsState.value.replace(",", ".").toDouble(),
                                        fatsState.value.replace(",", ".").toDouble(),
                                        carbohydratesState.value.replace(",", ".").toDouble(),
                                        typeState.value
                                    )
                                    openDialog = !openDialog
                                    onOpenChange(openDialog)
                                }
                                      },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Добавить")
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