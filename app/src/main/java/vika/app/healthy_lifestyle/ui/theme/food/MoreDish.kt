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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
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
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.RedLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListText
import vika.app.healthy_lifestyle.ui.theme.general.list.Search

@Composable
fun MoreDish(
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
        val dish = FoodActivity().getDish(context, title)

        val itemListIngredient = mutableListOf<Item>()
        val ingredients = FoodActivity().getAllProducts(context)
        if (ingredients != null) {
            for (ingredient in ingredients) {
                itemListIngredient.add(
                    Item(
                        ingredient.name,
                        ingredient.type,
                        ingredient.favorite,
                        ingredient.exception,
                        if (ingredient.isDish) 1 else 0
                    )
                )
            }
        }
        var filteredListIngredient by remember { mutableStateOf(itemListIngredient) }

        val recipeList = FoodActivity().getRecipe(context, dish.id)
        val selectListIngredient = remember { mutableStateListOf<ItemText>() }

        remember {
            recipeList.forEach { recipe ->
                val recipeName = FoodActivity().getIngredient(context, recipe.idIngredient).name
                if (selectListIngredient.none { it.title == recipeName }) {
                    selectListIngredient.add(ItemText(recipeName, recipe.valueIngredient))
                }
            }
        }

        val nameState = remember { mutableStateOf(title) }
        val kilocaloriesState = remember { mutableStateOf(dish.kilocalories.toString()) }
        val proteinsState = remember { mutableStateOf(dish.proteins.toString()) }
        val fatsState = remember { mutableStateOf(dish.fats.toString()) }
        val carbohydratesState = remember { mutableStateOf(dish.carbohydrates.toString()) }

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
        val typeState = remember { mutableStateOf(dish.type) }

        var favoriteState by remember { mutableStateOf(dish.favorite) }
        var exceptionState by remember { mutableStateOf(dish.exception) }

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

        var colorAdd by remember {
            mutableStateOf(Color.Transparent)
        }
        Dialog(
            onDismissRequest = {
                openDialog = false
                onOpenChange(false)
            }
        ) {
            Card(
                modifier = Modifier.padding(8.dp),
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
                        Text(
                            text = title,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )

                        Dropdown(
                            options,
                            dish.type
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

                    LazyColumn {
                        item {
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
                                            painterResource(R.drawable.dish),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                )
                            }

                            LazyColumn(
                                modifier = Modifier.height(500.dp)
                            ) {
                                item {
                                    Text(
                                        text = "Добавленные ингредиенты",
                                        modifier = Modifier.padding(8.dp),
                                        fontWeight = FontWeight.Bold,
                                        color = Black
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .border(3.dp, colorAdd, RoundedCornerShape(10.dp))
                                    ) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .width(300.dp)
                                                .height(200.dp)
                                        ) {
                                            items(selectListIngredient) { item ->
                                                key(item) {
                                                    ItemListDelete(
                                                        title = item.title,
                                                        value = item.value,
                                                        delete = { title ->
                                                            selectListIngredient.remove(
                                                                selectListIngredient.find { it.title == title }
                                                            )
                                                            val ingredient =
                                                                FoodActivity().getIngredient(
                                                                    context,
                                                                    title
                                                                )
                                                            kilocaloriesState.value =
                                                                (kilocaloriesState.value.toDouble()
                                                                        - ingredient.kilocalories / 100 * item.value).toString()
                                                            proteinsState.value =
                                                                (proteinsState.value.toDouble()
                                                                        - ingredient.proteins / 100 * item.value).toString()
                                                            fatsState.value =
                                                                (fatsState.value.toDouble()
                                                                        - ingredient.fats / 100 * item.value).toString()
                                                            carbohydratesState.value =
                                                                (carbohydratesState.value.toDouble()
                                                                        - ingredient.carbohydrates / 100 * item.value).toString()
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    Search(
                                        itemList = itemListIngredient,
                                        onSearchResults = {
                                            filteredListIngredient = it.toMutableList()
                                        }
                                    )

                                    LazyColumn(
                                        modifier = Modifier
                                            .width(300.dp)
                                            .height(200.dp)
                                    ) {
                                        items(filteredListIngredient) { item ->
                                            key(item.title) {
                                                ItemListText(
                                                    title = item.title,
                                                    textInDialog = "Введите вес в гр.",
                                                    add = { title, value ->
                                                        selectListIngredient.add(
                                                            ItemText(
                                                                title,
                                                                value
                                                            )
                                                        )
                                                        val ingredient =
                                                            FoodActivity().getIngredient(
                                                                context,
                                                                title
                                                            )
                                                        kilocaloriesState.value =
                                                            (kilocaloriesState.value.toDouble()
                                                                    + ingredient.kilocalories / 100 * value).toString()
                                                        proteinsState.value =
                                                            (proteinsState.value.toDouble()
                                                                    + ingredient.proteins / 100 * value).toString()
                                                        fatsState.value =
                                                            (fatsState.value.toDouble()
                                                                    + ingredient.fats / 100 * value).toString()
                                                        carbohydratesState.value =
                                                            (carbohydratesState.value.toDouble()
                                                                    + ingredient.carbohydrates / 100 * value).toString()
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
                                                .border(3.dp, colorKilo, RoundedCornerShape(10.dp))
                                                .weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                TextFieldBlue(
                                                    value = "%.1f".format(kilocaloriesState.value.toDouble()),
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
                                                .border(
                                                    3.dp,
                                                    colorProtein,
                                                    RoundedCornerShape(10.dp)
                                                )
                                                .weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                TextFieldBlue(
                                                    value = "%.1f".format(proteinsState.value.toDouble()),
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
                                                    value = "%.1f".format(fatsState.value.toDouble()),
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
                                                    value = "%.1f".format(carbohydratesState.value.toDouble()),
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
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                var check = true
                                if (nameState.value == "") {
                                    check = false
                                    colorName = RedLight
                                }
                                if (kilocaloriesState.value == "") {
                                    check = false
                                    colorKilo = RedLight
                                }
                                if (proteinsState.value == "") {
                                    check = false
                                    colorProtein = RedLight
                                }
                                if (fatsState.value == "") {
                                    check = false
                                    colorFat = RedLight
                                }
                                if (carbohydratesState.value == "") {
                                    check = false
                                    colorCarb = RedLight
                                }
                                if (selectListIngredient.size == 0){
                                    check = false
                                    colorAdd = RedLight
                                }
                                if (check) {
                                    FoodActivity().updateDish(
                                        context,
                                        dish.id,
                                        nameState.value,
                                        kilocaloriesState.value.replace(",", ".")
                                            .toDouble(),
                                        proteinsState.value.replace(",", ".")
                                            .toDouble(),
                                        fatsState.value.replace(",", ".").toDouble(),
                                        carbohydratesState.value.replace(",", ".")
                                            .toDouble(),
                                        typeState.value,
                                        favoriteState,
                                        exceptionState,
                                        selectListIngredient
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