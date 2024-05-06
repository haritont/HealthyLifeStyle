package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.calculations.MealCalc
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.food.FastKPFC

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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            FastKPFC()
            Advice(value = FoodActivity().getAdvice())

            LazyRow() {
                item {
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(500.dp)
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
                            0
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(350.dp)
                    ) {
                        vika.app.healthy_lifestyle.ui.theme.general.list.List(
                            itemList = filteredListDish,
                            add = { name, value, date, option ->
                                FoodActivity().addDish(
                                    context,
                                    name,
                                    value,
                                    date,
                                    option
                                )
                            },
                            typeToMore = 1,
                            updateException = { name, exception ->
                                FoodActivity().updateExceptionDish(
                                    context,
                                    name,
                                    exception
                                )
                            },
                            updateFavorite = { name, favorite ->
                                FoodActivity().updateFavoriteDish(
                                    context,
                                    name,
                                    favorite
                                )
                            },
                            "Введите объем съеденного блюда в гр.",
                            listOf(
                                "Завтрак",
                                "Обед",
                                "Ужин",
                                "Перекус"
                            ),
                            MealCalc().getCurrentMeal(),
                            1
                        )
                    }
                }
            }
        }
    }
}
