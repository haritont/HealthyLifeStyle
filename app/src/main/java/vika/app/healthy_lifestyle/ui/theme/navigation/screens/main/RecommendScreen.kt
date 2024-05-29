package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.food.Nutrition
import vika.app.healthy_lifestyle.recommend.RecommendSystem
import vika.app.healthy_lifestyle.recommend.database.RecommendProductRepository
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Orange
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.app.White
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

    var breakfastMaxValue = 0.0
    var lunchMaxValue = 0.0
    var dinnerMaxValue = 0.0
    var snackMaxValue = 0.0

    for (nutrition in nutritionToday) {
        val product = FoodActivity().getIngredient(context, nutrition.name)
        if (nutrition.meal == "Завтрак") {
            breakfastList.add(nutrition)

            breakfast.kilocalories += nutrition.value * product.kilocalories / 100
            breakfast.proteins += nutrition.value * product.proteins / 100
            breakfast.carbohydrates += nutrition.value * product.carbohydrates / 100
            breakfast.fats += nutrition.value * product.fats / 100

            if (breakfastMaxValue <=  nutrition.value){
                breakfastMaxValue = nutrition.value
                breakfast.type = product.type
            }
        }

        if (nutrition.meal == "Обед") {
            lunchList.add(nutrition)

            lunch.kilocalories += nutrition.value * product.kilocalories / 100
            lunch.proteins += nutrition.value * product.proteins / 100
            lunch.carbohydrates += nutrition.value * product.carbohydrates / 100
            lunch.fats += nutrition.value * product.fats / 100

            if (lunchMaxValue <=  nutrition.value){
                lunchMaxValue = nutrition.value
                lunch.type = product.type
            }
        }

        if (nutrition.meal == "Ужин") {
            dinnerList.add(nutrition)

            dinner.kilocalories += nutrition.value * product.kilocalories / 100
            dinner.proteins += nutrition.value * product.proteins / 100
            dinner.carbohydrates += nutrition.value * product.carbohydrates / 100
            dinner.fats += nutrition.value * product.fats / 100

            if (dinnerMaxValue <=  nutrition.value){
                dinnerMaxValue = nutrition.value
                dinner.type = product.type
            }
        }

        if (nutrition.meal == "Перекус") {
            snackList.add(nutrition)

            snack.kilocalories += nutrition.value * product.kilocalories / 100
            snack.proteins += nutrition.value * product.proteins / 100
            snack.carbohydrates += nutrition.value * product.carbohydrates / 100
            snack.fats += nutrition.value * product.fats / 100

            if (snackMaxValue <=  nutrition.value){
                snackMaxValue = nutrition.value
                snack.type = product.type
            }
        }
    }

    val breakfastRecommend = RecommendSystem(context, type, 0)
    val breakfastMark = breakfastRecommend.getMarkMeal(breakfast)

    val lunchRecommend = RecommendSystem(context, type, 1)
    val lunchMark = lunchRecommend.getMarkMeal(lunch)

    val dinnerRecommend = RecommendSystem(context, type, 2)
    val dinnerMark = dinnerRecommend.getMarkMeal(dinner)

    val snackRecommend = RecommendSystem(context, type, 3)
    val snackMark = snackRecommend.getMarkMeal(snack)

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
        verticalArrangement = Arrangement.SpaceAround,
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
                var text = ""
                text = if (breakfastList.isEmpty()) {
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
                    text = text,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Row {
                    Column {
                        Text(
                            text = "Цель",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = breakfastRecommend.adviceTarget,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    Column {
                        Text(
                            text = "Ваше значение",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = breakfastRecommend.adviceProgress,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    items(breakfastList) { item ->
                        key(item.name) {
                            val product = FoodActivity().getIngredient(context, item.name)
                            val mark =
                                RecommendProductRepository(context).getRecommendProduct(item.name).mark
                            RecommendItem(
                                title = item.name,
                                color =
                                if (mark >= 3) {
                                    Green
                                } else if (mark >= 2) {
                                    Yellow
                                } else if (mark >= 1) {
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

            if (lunchCheck) {
                var text = ""
                text = if (lunchList.isEmpty()) {
                    "Нет информации об обеде"
                } else {
                    if (lunchMark >= 3.75) {
                        "Ваш обед отличный"
                    } else if (lunchMark >= 2.5) {
                        "Ваш обед хороший"
                    } else if (lunchMark >= 1.25) {
                        "Ваш обед средний"
                    } else {
                        "Ваш обед плохой"
                    }
                }
                Text(
                    text = text,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Row {
                    Column {
                        Text(
                            text = "Цель",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = lunchRecommend.adviceTarget,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    Column {
                        Text(
                            text = "Ваше значение",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = lunchRecommend.adviceProgress,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    items(lunchList) { item ->
                        key(item.name) {
                            val product = FoodActivity().getIngredient(context, item.name)
                            val mark =
                                RecommendProductRepository(context).getRecommendProduct(item.name).mark
                            RecommendItem(
                                title = item.name,
                                color =
                                if (mark >= 2) {
                                    Green
                                } else if (mark >= 1) {
                                    Yellow
                                } else if (mark >= 0.5) {
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

            if (dinnerCheck) {
                var text = ""
                text = if (dinnerList.isEmpty()) {
                    "Нет информации об ужине"
                } else {
                    if (dinnerMark >= 3.75) {
                        "Ваш ужин отличный"
                    } else if (dinnerMark >= 2.5) {
                        "Ваш ужин хороший"
                    } else if (dinnerMark >= 1.25) {
                        "Ваш ужин средний"
                    } else {
                        "Ваш ужин плохой"
                    }
                }
                Text(
                    text = text,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Row {
                    Column {
                        Text(
                            text = "Цель",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = dinnerRecommend.adviceTarget,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    Column {
                        Text(
                            text = "Ваше значение",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = dinnerRecommend.adviceProgress,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    items(dinnerList) { item ->
                        key(item.name) {
                            val product = FoodActivity().getIngredient(context, item.name)
                            val mark =
                                RecommendProductRepository(context).getRecommendProduct(item.name).mark
                            RecommendItem(
                                title = item.name,
                                color =
                                if (mark >= 2) {
                                    Green
                                } else if (mark >= 1) {
                                    Yellow
                                } else if (mark >= 0.5) {
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

            if (snackCheck) {
                var text = ""
                text = if (snackList.isEmpty()) {
                    "Нет информации о перекусе"
                } else {
                    if (snackMark >= 3.75) {
                        "Ваш перекус отличный"
                    } else if (snackMark >= 2.5) {
                        "Ваш перекус хороший"
                    } else if (snackMark >= 1.25) {
                        "Ваш перекус средний"
                    } else {
                        "Ваш перекус плохой"
                    }
                }
                Text(
                    text = text,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Row {
                    Column {
                        Text(
                            text = "Цель",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = snackRecommend.adviceTarget,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    Column {
                        Text(
                            text = "Ваше значение",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = snackRecommend.adviceProgress,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    items(snackList) { item ->
                        key(item.name) {
                            val product = FoodActivity().getIngredient(context, item.name)
                            val mark =
                                RecommendProductRepository(context).getRecommendProduct(item.name).mark
                            RecommendItem(
                                title = item.name,
                                color =
                                if (mark >= 2) {
                                    Green
                                } else if (mark >= 1) {
                                    Yellow
                                } else if (mark >= 0.5) {
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
}