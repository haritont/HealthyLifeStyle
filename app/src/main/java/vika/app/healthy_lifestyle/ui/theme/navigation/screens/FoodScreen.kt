package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.calculations.MealCalc
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.food.FastKPFC
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete

@Composable
fun FoodScreen() {
    val context = LocalContext.current

    val itemListIngredient = mutableListOf<Item>()
    val ingredients = FoodActivity().getAllIngredients(context)
    for (ingredient in ingredients) {
        itemListIngredient.add(
            Item(
                ingredient.name,
                ingredient.type,
                ingredient.favorite,
                ingredient.exception
            )
        )
    }
    val filteredListIngredient by remember { mutableStateOf(itemListIngredient) }

    val itemListDish = mutableListOf<Item>()
    val dishes = FoodActivity().getAllDishes(context)
    for (dish in dishes) {
        itemListDish.add(
            Item(
                dish.name,
                dish.type,
                dish.favorite,
                dish.exception
            )
        )
    }
    val filteredListDish by remember { mutableStateOf(itemListDish) }

    val selectListProduct = remember { mutableStateListOf<ItemText>() }

    var lastListProduct = FoodActivity().getLastNutrition(context)
    lastListProduct = lastListProduct.reversed()

    for (nutrition in lastListProduct) {
        selectListProduct.add(ItemText(nutrition.name, nutrition.value))
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            if (selectListProduct.size != 0) {
                Text(
                    text = "Последние добавленные",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                LazyColumn(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(8.dp)
                ) {
                    items(selectListProduct) { item ->
                        key(item) {
                            ItemListDelete(
                                title = item.title,
                                value = item.value,
                                delete = { title ->
                                    selectListProduct.remove(
                                        selectListProduct.find { it.title == title }
                                    )
                                    FoodActivity().deleteNutrition(
                                        context,
                                        item.title,
                                        item.value,
                                        DateToday().getToday()
                                    )
                                }
                            )
                        }
                    }
                }
            }

            FastKPFC()
            Advice(value = FoodActivity().getAdvice())

//            LazyRow{
//                item {
            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(520.dp)
            ) {
                vika.app.healthy_lifestyle.ui.theme.general.list.List(
                    itemList = filteredListIngredient,
                    add = { name, value, date, option ->
                        FoodActivity().addIngredient(
                            context,
                            name,
                            value,
                            date,
                            option
                        )
                        selectListProduct.add(0, ItemText(name, value))
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    },
                    typeToMore = 0,
                    updateException = { name, exception ->
                        FoodActivity().updateExceptionIngredient(
                            context,
                            name,
                            exception
                        )
                    },
                    updateFavorite = { name, favorite ->
                        FoodActivity().updateFavoriteIngredient(
                            context,
                            name,
                            favorite
                        )
                    },
                    "Введите объем съеденного ингредиента в гр.",
                    listOf(
                        "Завтрак",
                        "Обед",
                        "Ужин",
                        "Перекус"
                    ),
                    MealCalc().getCurrentMeal(),
                    typeAdd = 0,
                    clickSearch = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 5)
                        }
                    }
                )
            }

//                    Spacer(Modifier.width(8.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .size(350.dp)
//                    ) {
//                        vika.app.healthy_lifestyle.ui.theme.general.list.List(
//                            itemList = filteredListDish,
//                            add = { name, value, date, option ->
//                                FoodActivity().addDish(
//                                    context,
//                                    name,
//                                    value,
//                                    date,
//                                    option
//                                )
//                                selectListProduct.add(ItemText(name, value))
//                                searchKey = 0
//                            },
//                            typeToMore = 1,
//                            updateException = { name, exception ->
//                                FoodActivity().updateExceptionDish(
//                                    context,
//                                    name,
//                                    exception
//                                )
//                            },
//                            updateFavorite = { name, favorite ->
//                                FoodActivity().updateFavoriteDish(
//                                    context,
//                                    name,
//                                    favorite
//                                )
//                            },
//                            "Введите объем съеденного блюда в гр.",
//                            listOf(
//                                "Завтрак",
//                                "Обед",
//                                "Ужин",
//                                "Перекус"
//                            ),
//                            MealCalc().getCurrentMeal(),
//                            1,
//                            {
//                                searchKey = 1
//                            }
//                        )
//                    }
//                }
//            }
        }
    }
}

