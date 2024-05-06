package vika.app.healthy_lifestyle.activity.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.food.NutritionRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.ActivismRepository
import vika.app.healthy_lifestyle.bean.main.History
import vika.app.healthy_lifestyle.bean.sport.Activism
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
            }
        }
    }

    fun getHistory(context: Context, currentDate: String): List<History> {
        val nutritionList = NutritionRepository(context).getNutritionByDate(currentDate)
        val physicalExerciseList = ActivismRepository(context).getActivismByDate(currentDate)

        val historyList = mutableListOf<History>()
        for (nutrition in nutritionList){
            historyList.add(
                History(
                    name = nutrition.name,
                    value = nutrition.value,
                    meal = nutrition.meal,
                    type = 0
                )
            )
        }

        for (physicalExercise in physicalExerciseList){
            historyList.add(
                History(
                    name = physicalExercise.name,
                    value = physicalExercise.value,
                    type = 1
                )
            )
        }

        return historyList
    }
}