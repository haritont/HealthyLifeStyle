package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.food.Nutrition
import vika.app.healthy_lifestyle.recommend.RecommendSystem
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.White
import vika.app.healthy_lifestyle.ui.theme.main.RecommendPage

@Composable
fun RecommendScreen() {
    val context = LocalContext.current

    val nutritionToday = FoodActivity().getLastNutrition(context)
    val type = PersonalDataRepository(context).getPersonalData().target

    val breakfast = Ingredient(
        name = "Завтрак", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )
    val lunch = Ingredient(
        name = "Обед", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )
    val dinner = Ingredient(
        name = "Ужин", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )
    val snack = Ingredient(
        name = "Перекус", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )

    val breakfastList = mutableListOf<Nutrition>()
    val lunchList = mutableListOf<Nutrition>()
    val dinnerList = mutableListOf<Nutrition>()
    val snackList = mutableListOf<Nutrition>()

    for (nutrition in nutritionToday) {
        val product = FoodActivity().getIngredient(context, nutrition.name)
        if (nutrition.meal == "Завтрак") {
            breakfastList.add(nutrition)

            breakfast.kilocalories += nutrition.value * product.kilocalories / 100
            breakfast.proteins += nutrition.value * product.proteins / 100
            breakfast.carbohydrates += nutrition.value * product.carbohydrates / 100
            breakfast.fats += nutrition.value * product.fats / 100
        }

        if (nutrition.meal == "Обед") {
            lunchList.add(nutrition)

            lunch.kilocalories += nutrition.value * product.kilocalories / 100
            lunch.proteins += nutrition.value * product.proteins / 100
            lunch.carbohydrates += nutrition.value * product.carbohydrates / 100
            lunch.fats += nutrition.value * product.fats / 100
        }

        if (nutrition.meal == "Ужин") {
            dinnerList.add(nutrition)

            dinner.kilocalories += nutrition.value * product.kilocalories / 100
            dinner.proteins += nutrition.value * product.proteins / 100
            dinner.carbohydrates += nutrition.value * product.carbohydrates / 100
            dinner.fats += nutrition.value * product.fats / 100
        }

        if (nutrition.meal == "Перекус") {
            snackList.add(nutrition)

            snack.kilocalories += nutrition.value * product.kilocalories / 100
            snack.proteins += nutrition.value * product.proteins / 100
            snack.carbohydrates += nutrition.value * product.carbohydrates / 100
            snack.fats += nutrition.value * product.fats / 100
        }
    }

    val breakfastRecommend = RecommendSystem(context, type, 0)
    val lunchRecommend = RecommendSystem(context, type, 1)
    val dinnerRecommend = RecommendSystem(context, type, 2)
    val snackRecommend = RecommendSystem(context, type, 3)

    var breakfastCheck by remember { mutableStateOf(true) }
    var lunchCheck by remember { mutableStateOf(false) }
    var dinnerCheck by remember { mutableStateOf(false) }
    var snackCheck by remember { mutableStateOf(false) }

    var breakfastColor by remember { mutableStateOf(Blue) }
    var lunchColor by remember { mutableStateOf(White) }
    var dinnerColor by remember { mutableStateOf(White) }
    var snackColor by remember { mutableStateOf(White) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Row {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = breakfastColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            breakfastColor = Blue
                            lunchColor = White
                            dinnerColor = White
                            snackColor = White

                            breakfastCheck = true
                            lunchCheck = false
                            dinnerCheck = false
                            snackCheck = false
                        }
                ) {
                    Text(
                        text = "Завтрак",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = lunchColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            breakfastColor = White
                            lunchColor = Blue
                            dinnerColor = White
                            snackColor = White

                            breakfastCheck = false
                            lunchCheck = true
                            dinnerCheck = false
                            snackCheck = false
                        }
                ) {
                    Text(
                        text = "Обед",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = dinnerColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            breakfastColor = White
                            lunchColor = White
                            dinnerColor = Blue
                            snackColor = White

                            breakfastCheck = false
                            lunchCheck = false
                            dinnerCheck = true
                            snackCheck = false
                        }
                ) {
                    Text(
                        text = "Ужин",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = snackColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            breakfastColor = White
                            lunchColor = White
                            dinnerColor = White
                            snackColor = Blue

                            breakfastCheck = false
                            lunchCheck = false
                            dinnerCheck = false
                            snackCheck = true
                        }
                ) {
                    Text(
                        text = "Перекус",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }

            if (breakfastCheck) {
                val text = if (breakfastList.isEmpty()) {
                    "Нет информации о завтраке"
                } else {
                    "Завтрак"
                }
                RecommendPage(
                    text = text,
                    recommend = breakfastRecommend,
                    meal = breakfast,
                    mealList = breakfastList
                )
            }

            if (lunchCheck) {
                val text = if (lunchList.isEmpty()) {
                    "Нет информации об обеде"
                } else {
                    "Обед"
                }
                RecommendPage(
                    text = text,
                    recommend = lunchRecommend,
                    meal = lunch,
                    mealList = lunchList
                )
            }

            if (dinnerCheck) {
                val text = if (dinnerList.isEmpty()) {
                    "Нет информации об ужине"
                } else {
                    "Ужин"
                }
                RecommendPage(
                    text = text,
                    recommend = dinnerRecommend,
                    meal = dinner,
                    mealList = dinnerList
                )
            }

            if (snackCheck) {
                val text = if (snackList.isEmpty()) {
                    "Нет информации о перекусе"
                } else {
                    "Перекус"
                }
                RecommendPage(
                    text = text,
                    recommend = snackRecommend,
                    meal = snack,
                    mealList = snackList
                )
            }
        }
    }
}