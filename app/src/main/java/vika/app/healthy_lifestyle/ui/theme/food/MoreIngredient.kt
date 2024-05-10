package vika.app.healthy_lifestyle.ui.theme.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun MoreIngredient(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit,
    title: String
) {
    var openDialog by remember { mutableStateOf(isOpen) }

    val context = LocalContext.current

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        onOpenChange(openDialog)
        onDispose { }
    }

    if (isOpen) {

        val ingredient = FoodActivity().getIngredient(context, title)

        val nameState = remember { mutableStateOf(title) }
        val kilocaloriesState = remember { mutableStateOf(ingredient.kilocalories) }
        val proteinsState = remember { mutableStateOf(ingredient.proteins) }
        val fatsState = remember { mutableStateOf(ingredient.fats) }
        val carbohydratesState = remember { mutableStateOf(ingredient.carbohydrates) }

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
        val typeState = remember { mutableStateOf(ingredient.type) }

        var favoriteState by remember { mutableStateOf(ingredient.favorite) }
        var exceptionState by remember { mutableStateOf(ingredient.exception) }

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
                            ingredient.type
                        ) {
                                currentOption ->
                            typeState.value = currentOption
                        }

                        Text(
                            text = title,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )

                        Image(
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        exceptionState = !exceptionState
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
                                    })
                                .size(35.dp)
                                .padding(8.dp),
                            painter = painterResource(if (!favoriteState) R.drawable.like_false else R.drawable.like_true),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = kilocaloriesState.value.toString(),
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.kilocalories),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> kilocaloriesState.value = newLogin.toDouble() },
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
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = proteinsState.value.toString(),
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.proteins),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> proteinsState.value = newLogin.toDouble() },
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
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = fatsState.value.toString(),
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.fats),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> fatsState.value = newLogin.toDouble() },
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

                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = carbohydratesState.value.toString(),
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.carbohydrates),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> carbohydratesState.value = newLogin.toDouble() },
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

                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                FoodActivity().updateIngredient(
                                    context,
                                    ingredient.id,
                                    nameState.value,
                                    kilocaloriesState.value.toString().replace(",", ".").toDouble(),
                                    proteinsState.value.toString().replace(",", ".").toDouble(),
                                    fatsState.value.toString().replace(",", ".").toDouble(),
                                    carbohydratesState.value.toString().replace(",", ".").toDouble(),
                                    typeState.value,
                                    favoriteState,
                                    exceptionState
                                )
                                openDialog = !openDialog
                                onOpenChange(openDialog)
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