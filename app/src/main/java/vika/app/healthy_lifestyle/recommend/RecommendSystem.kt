package vika.app.healthy_lifestyle.recommend

import android.content.Context
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.recommend.database.RecommendProductRepository

class RecommendSystem(
    val context: Context,
    val target: Int,
    val meal: Int
) {
    private val minMark = 2.5
    var adviceTarget = ""
    var adviceProgress = ""

    fun getProducts(count: Int): List<RecommendProduct>{
        return getListProduct().shuffled().take(count)
    }
    private fun getListProduct(): List<RecommendProduct>{
        return RecommendProductRepository(context).getRecommendProductList(minMark, target, meal);
    }

    fun getMarkMeal(ingredient: Ingredient): Double {
        var mark = 0.0
        val plan = MealPlanManager().getMealPlan(target, meal)

        val targetKilo = RecordRepository(context).targetKilocalories(DateToday().getToday())
        var kilocalories = ingredient.kilocalories

        adviceTarget = "по ккал: ".plus(plan?.kiloMin).plus("% - ").plus(plan?.kiloMax).plus("%\n")
            .plus("по белкам: ".plus(plan?.proteinMin).plus("% - ").plus(plan?.proteinMax).plus("%\n"))
            .plus("по жирам: ".plus(plan?.fatMin).plus("% - ").plus(plan?.fatMax).plus("%\n"))
            .plus("по углеводам: ".plus(plan?.carbMin).plus("% - ").plus(plan?.carbMax).plus("%\n"))

        if (kilocalories != 0.0) {
            val protein = (ingredient.proteins * 4) / kilocalories * 100
            val fats = (ingredient.fats * 9) / kilocalories * 100
            val carbohydrates = (ingredient.carbohydrates * 4) / kilocalories * 100
            kilocalories = kilocalories / targetKilo * 100

            adviceProgress = "по ккал: ".plus(kilocalories.toInt().toString()).plus("%\n")
                .plus("по белкам: ".plus(protein.toInt().toString()).plus("%\n"))
                .plus("по жирам: ".plus(fats.toInt().toString()).plus("%\n"))
                .plus("по углеводам: ".plus(carbohydrates.toInt().toString()).plus("%\n"))

            if (protein >= plan!!.proteinMin && protein <= plan.proteinMax) {
                mark += 1.0
            }

            if (fats >= plan.fatMin && fats <= plan.fatMax) {
                mark += 1.0
            }

            if (carbohydrates >= plan.carbMin && carbohydrates <= plan.carbMax) {
                mark += 1.0
            }

            if (kilocalories >= plan.kiloMin && kilocalories <= plan.kiloMax) {
                mark += 1.0
            }
        }

        if (plan!!.types.contains(ingredient.type)) {
            mark += 1.0
        }
        return mark
    }
}