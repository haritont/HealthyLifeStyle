package vika.app.healthy_lifestyle.recommend

import android.content.Context
import android.content.SharedPreferences
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.calculations.DateToday

class MealPlanManager {
    private val sportPlan: List<SportPlan> = listOf(
        SportPlan(
            2,
            listOf("Бег", "Виды спорта", "Ходьба", "Езда на велосипеде")
        ),
        SportPlan(
            1,
            listOf("Кондицирующие упражнения", "Ходьба", "Езда на велосипеде")
        ),
        SportPlan(
            0,
            listOf("Виды спорта", "Кондицирующие упражнения")
        )
    )

    fun getMealPlan(meal: Int, context: Context): MealPlan {
        val record = RecordRepository(context).getRecordByDate(DateToday().getToday())!!
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        val breakfast = sharedPreferences.getString("breakfast", null)?.toDouble() ?: 0.3
        val lunch = sharedPreferences.getString("lunch", null)?.toDouble() ?: 0.3
        val dinner = sharedPreferences.getString("dinner", null)?.toDouble() ?: 0.2
        val snack = sharedPreferences.getString("snack", null)?.toDouble() ?: 0.2

        when (meal) {
            0 -> {
                return MealPlan(
                    kiloTarget = record.targetKilocalories * breakfast,
                    proteinTarget = record.targetProteins * breakfast,
                    fatTarget = record.targetFats * breakfast,
                    carbTarget = record.targetCarbohydrates * breakfast
                )
            }
            1 -> {
                return MealPlan(
                    kiloTarget = record.targetKilocalories * lunch,
                    proteinTarget = record.targetProteins * lunch,
                    fatTarget = record.targetFats * lunch,
                    carbTarget = record.targetCarbohydrates * lunch
                )
            }
            2 -> {
                return MealPlan(
                    kiloTarget = record.targetKilocalories * dinner,
                    proteinTarget = record.targetProteins * dinner,
                    fatTarget = record.targetFats * dinner,
                    carbTarget = record.targetCarbohydrates * dinner
                )
            }
            else -> return MealPlan(
                kiloTarget = record.targetKilocalories * snack,
                proteinTarget = record.targetProteins * snack,
                fatTarget = record.targetFats * snack,
                carbTarget = record.targetCarbohydrates * snack
            )
        }
    }

    fun getSportPlan(target: Int): SportPlan?{
        return sportPlan.find{it.target == target}
    }
}
