package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
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
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Orange
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.app.Yellow
import vika.app.healthy_lifestyle.ui.theme.general.list.RecommendItem

@Composable
fun RecommendScreen() {
    val context = LocalContext.current

    val nutritionToday = FoodActivity().getLastNutrition(context)
    val type = PersonalDataRepository(context).getPersonalData().target

    val breakfast = Ingredient(
        name = "Завтрак", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )
    var lunch = Ingredient(
        name = "Обед", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )
    var dinner = Ingredient(
        name = "Ужин", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )
    var snack = Ingredient(
        name = "Перекус", kilocalories = 0.0, proteins = 0.0,
        fats = 0.0, carbohydrates = 0.0
    )

    val breakfastList = mutableListOf<Nutrition>()
    var maxValue = 0.0
    for (nutrition in nutritionToday) {
        val product = FoodActivity().getIngredient(context, nutrition.name)
        if (nutrition.meal == "Завтрак") {
            breakfastList.add(nutrition)

            breakfast.kilocalories += nutrition.value * product.kilocalories / 100
            breakfast.proteins += nutrition.value * product.proteins / 100
            breakfast.carbohydrates += nutrition.value * product.carbohydrates / 100
            breakfast.fats += nutrition.value * product.fats / 100

            if (maxValue <=  nutrition.value){
                maxValue = nutrition.value
                breakfast.type = product.type
            }
        }
    }

    val breakfastMark = RecommendSystem(context, type, 0).getMark(breakfast)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            var breakfastText = ""
            breakfastText = if (breakfastList.isEmpty()) {
                "Нет информации о завтраке"
            } else {
                if (breakfastMark >= 3.75) {
                    "Ваш завтрак отличный"
                } else if (breakfastMark >= 2.5) {
                    "Ваш завтрак хороший"
                } else if (breakfastMark >= 1.25) {
                    "Ваш завтрак средний"
                } else {
                    "Ваш завтрак плохой"
                }
            }
            Text(
                text = breakfastText,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                color = Black
            )
            LazyColumn(
                modifier = Modifier
                    .height(300.dp)
                    .padding(8.dp)
            ) {
                items(breakfastList) { item ->
                    key(item.name) {
                        val product = FoodActivity().getIngredient(context, item.name)
                        val mark = RecommendSystem(context, type, 0).getMark(product)
                        RecommendItem(
                            title = item.name,
                            color =
                            if (mark >= 3.75) {
                                Green
                            } else if (mark >= 2.5) {
                                Yellow
                            } else if (mark >= 1.25) {
                                Orange
                            } else {
                                Red
                            },
                            kilocalories = product.kilocalories * item.value / 100,
                            proteins = product.proteins * item.value / 100,
                            fats = product.fats * item.value / 100,
                            carbohydrates = product.carbohydrates * item.value / 100
                        )
                    }
                }
            }
        }
    }
}