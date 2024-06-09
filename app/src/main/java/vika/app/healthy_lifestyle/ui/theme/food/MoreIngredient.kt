package vika.app.healthy_lifestyle.ui.theme.food

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.RedLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.general.defaultOptionProduct

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
        openDialog = isOpen
        onDispose { }
    }

    if (openDialog) {
        val ingredient = FoodActivity().getIngredient(context, title)

        val nameState = remember { mutableStateOf(title) }
        val kilocaloriesState = remember { mutableStateOf(ingredient.kilocalories.toString()) }
        val proteinsState = remember { mutableStateOf(ingredient.proteins.toString()) }
        val fatsState = remember { mutableStateOf(ingredient.fats.toString()) }
        val carbohydratesState = remember { mutableStateOf(ingredient.carbohydrates.toString()) }

        var options = TypeRepository(context).getAllByProduct()
        if (options!!.isEmpty()){
            options = defaultOptionProduct
        }
        val typeState = remember { mutableStateOf(ingredient.type.type) }

        var favoriteState by remember { mutableStateOf(ingredient.favorite) }
        var exceptionState by remember { mutableStateOf(ingredient.exception) }

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
        Dialog(
            onDismissRequest = {
                openDialog = false
                onOpenChange(false)
            }
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                )
                {
                    Text(
                        text = title,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Dropdown(
                            options,
                            ingredient.type.type
                        ) { currentOption ->
                            typeState.value = currentOption
                        }

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
                                    onValueChange = { newLogin ->
                                        proteinsState.value = newLogin
                                    },
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
                                    onValueChange = { newLogin ->
                                        fatsState.value = newLogin
                                    },
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
                                    FoodActivity().updateIngredient(
                                        context,
                                        ingredient.id,
                                        nameState.value,
                                        kilocaloriesState.value.replace(",", ".")
                                            .toDouble(),
                                        proteinsState.value.replace(",", ".").toDouble(),
                                        fatsState.value.replace(",", ".").toDouble(),
                                        carbohydratesState.value.replace(",", ".")
                                            .toDouble(),
                                        typeState.value,
                                        favoriteState,
                                        exceptionState
                                    )
                                    openDialog = false
                                    onOpenChange(false)
                                    Toast.makeText(
                                        context,
                                        "Изменено: ".plus(nameState.value),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Ок")
                        }
                        TextButton(
                            onClick = {
                                openDialog = false
                                onOpenChange(false)
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