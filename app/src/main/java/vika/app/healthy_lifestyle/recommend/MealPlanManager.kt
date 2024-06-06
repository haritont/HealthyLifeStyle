package vika.app.healthy_lifestyle.recommend

import android.content.Context
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

        when (meal) {
            0 -> {
                return MealPlan(
                    kiloTarget = record.targetKilocalories * 0.3,
                    proteinTarget = record.targetProteins * 0.3,
                    fatTarget = record.targetFats * 0.3,
                    carbTarget = record.targetCarbohydrates * 0.3
                )
            }
            1 -> {
                return MealPlan(
                    kiloTarget = record.targetKilocalories * 0.3,
                    proteinTarget = record.targetProteins * 0.3,
                    fatTarget = record.targetFats * 0.3,
                    carbTarget = record.targetCarbohydrates * 0.3
                )
            }
            2 -> {
                return MealPlan(
                    kiloTarget = record.targetKilocalories * 0.2,
                    proteinTarget = record.targetProteins * 0.2,
                    fatTarget = record.targetFats * 0.2,
                    carbTarget = record.targetCarbohydrates * 0.2
                )
            }
            else -> return MealPlan(
                kiloTarget = record.targetKilocalories * 0.2,
                proteinTarget = record.targetProteins * 0.2,
                fatTarget = record.targetFats * 0.2,
                carbTarget = record.targetCarbohydrates * 0.2
            )
        }
    }

    fun getSportPlan(target: Int): SportPlan?{
        return sportPlan.find{it.target == target}
    }
}
