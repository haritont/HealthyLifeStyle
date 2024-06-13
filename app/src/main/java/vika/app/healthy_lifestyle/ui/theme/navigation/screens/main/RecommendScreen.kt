package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.main.Type
import vika.app.healthy_lifestyle.recommend.MealPlanManager
import vika.app.healthy_lifestyle.recommend.RecommendSystem
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.White
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.instruction.InstructionRecommend
import vika.app.healthy_lifestyle.ui.theme.main.RecommendPage

@Composable
fun RecommendScreen() {
    val context = LocalContext.current
    val meals = listOf("Завтрак", "Обед", "Ужин", "Перекус")
    var selectedMealIndex by remember { mutableStateOf(0) }
    val nutritionToday = FoodActivity().getLastNutrition(context)
    val nutritionMap = mutableMapOf<String, Ingredient>()

    meals.forEach {
        nutritionMap[it] = Ingredient(name = it, kilocalories = 0.0, proteins = 0.0, fats = 0.0, carbohydrates = 0.0, type = Type())
    }

    nutritionToday.forEach { nutrition ->
        val product = FoodActivity().getIngredient(context, nutrition.name)
        val meal = nutrition.meal

        if (meal in meals) {
            nutritionMap[meal]?.apply {
                kilocalories += nutrition.value * product.kilocalories / 100
                proteins += nutrition.value * product.proteins / 100
                fats += nutrition.value * product.fats / 100
                carbohydrates += nutrition.value * product.carbohydrates / 100
            }
        }
    }

    var isShowingTips by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            ImageButton(
                icon = R.drawable.question,
                onClick = { isShowingTips = !isShowingTips }
            )
        }

        if (isShowingTips) {
            InstructionRecommend(
                isOpen = true,
                onOpenChange = { isOpen -> isShowingTips = isOpen }
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            meals.forEachIndexed { index, meal ->
                val isSelected = index == selectedMealIndex
                Text(
                    text = meal,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    modifier = Modifier
                        .background(if (isSelected) Blue else White, RoundedCornerShape(5.dp))
                        .padding(8.dp)
                        .clickable {
                            selectedMealIndex = index
                        }
                )
            }
        }

        val selectedMeal = meals[selectedMealIndex]
        val nutritionList = when (selectedMeal) {
            "Завтрак" -> nutritionToday.filter { it.meal == "Завтрак" }
            "Обед" -> nutritionToday.filter { it.meal == "Обед" }
            "Ужин" -> nutritionToday.filter { it.meal == "Ужин" }
            "Перекус" -> nutritionToday.filter { it.meal == "Перекус" }
            else -> emptyList()
        }

        val recommendationSystem = RecommendSystem(context, PersonalDataRepository(context).getPersonalData()?.target ?: 0, MealPlanManager().getMealPlan(selectedMealIndex, context))

        RecommendPage(
            text = if (nutritionList.isEmpty()) "Нет информации" else selectedMeal,
            recommend = recommendationSystem,
            meal = nutritionMap[selectedMeal]!!,
            mealList = nutritionList
        )
    }
}