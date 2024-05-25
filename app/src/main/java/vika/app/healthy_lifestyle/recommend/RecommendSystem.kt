package vika.app.healthy_lifestyle.recommend

import android.content.Context
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.recommend.database.RecommendProductRepository

class RecommendSystem(
    val context: Context,
    val target: Int,
    val meal: Int
) {
    private val minMark = 2.5

    fun getProducts(count: Int): List<RecommendProduct>{
        return getListProduct().shuffled().take(count)
    }
    private fun getListProduct(): List<RecommendProduct>{
        return RecommendProductRepository(context).getRecommendProductList(minMark, target, meal);
    }

    fun getMark(ingredient: Ingredient): Double {
        var mark = 0.0
        val plan = MealPlanManager().getMealPlan(target, meal)

        val kilocalories = ingredient.kilocalories

        if (kilocalories != 0.0) {
            val protein = (ingredient.proteins * 4) / kilocalories * 100
            val fats = (ingredient.fats * 9) / kilocalories * 100
            val carbohydrates = (ingredient.carbohydrates * 4) / kilocalories * 100

            if (protein >= plan!!.proteinMin && protein <= plan.proteinMax) {
                mark += 1.0
            }

            if (fats >= plan.fatMin && fats <= plan.fatMax) {
                mark += 1.0
            }

            if (carbohydrates >= plan.carbMin && carbohydrates <= plan.carbMax) {
                mark += 1.0
            }
        }

        if (plan!!.types.contains(ingredient.type)) {
            mark += 1.0
        }
        return mark
    }
}