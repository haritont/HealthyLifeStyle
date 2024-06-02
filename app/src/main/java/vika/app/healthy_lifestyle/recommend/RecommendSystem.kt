package vika.app.healthy_lifestyle.recommend

import android.content.Context
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.PhysicalExerciseRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.recommend.database.RecommendProductRepository

class RecommendSystem(
    val context: Context,
    val target: Int,
    val meal: Int
) {
    private val minMark = 2.5
    var progressKilo = ""
    var progressProtein = ""
    var progressFat = ""
    var progressCarb = ""

    var targetKilo = ""
    var targetProtein = ""
    var targetFat = ""
    var targetCarb = ""

    fun getProducts(count: Int): List<RecommendProduct>{
        return getListProduct().shuffled().take(count)
    }

    fun getSports(count: Int): List<PhysicalExercise>{
        val recommendSports = mutableListOf<PhysicalExercise>()
        val physicalExercises = PhysicalExerciseRepository(context).getAllPhysicalExercises()
        val plan = MealPlanManager().getSportPlan(target)

        for(physicalExercise in physicalExercises){
            if (physicalExercise.type in plan!!.types){
                recommendSports.add(physicalExercise)
            }
        }
        return recommendSports.shuffled().take(count)
    }
    private fun getListProduct(): List<RecommendProduct>{
        return RecommendProductRepository(context).getRecommendProductList(minMark, target, meal)
    }

    fun getMarkKilo(ingredient: Ingredient): Double{
        val plan = MealPlanManager().getMealPlan(target, meal)

        val targetKilo = RecordRepository(context).targetKilocalories(DateToday().getToday())
        val kilocalories = ingredient.kilocalories / targetKilo * 100

        progressKilo = "%.1f".format(kilocalories).plus("%")
        if (kilocalories >= plan!!.kiloMin && kilocalories <= plan.kiloMax) {
           return 1.0
        }
        else if (kilocalories >= plan.kiloMin - 5 && kilocalories <= plan.kiloMax + 5) {
            return 0.5
        }
        return 0.0
    }

    fun getMarkProtein(ingredient: Ingredient): Double{
        val plan = MealPlanManager().getMealPlan(target, meal)

        val protein = (ingredient.proteins * 4) / ingredient.kilocalories * 100

        progressProtein = "%.1f".format(protein).plus("%")
        if (protein >= plan!!.proteinMin && protein <= plan.proteinMax) {
            return 1.0
        }
        else if (protein >= plan.proteinMin - 5 && protein <= plan.proteinMax + 5) {
            return 0.5
        }
        return 0.0
    }

    fun getMarkFat(ingredient: Ingredient): Double{
        val plan = MealPlanManager().getMealPlan(target, meal)

        val fats = (ingredient.fats * 9) / ingredient.kilocalories * 100

        progressFat = "%.1f".format(fats).plus("%")
        if (fats >= plan!!.fatMin && fats <= plan.fatMax) {
            return 1.0
        }
        else if (fats >= plan.fatMin - 5 && fats <= plan.fatMax + 5) {
            return 0.5
        }
        return 0.0
    }

    fun getMarkCarb(ingredient: Ingredient): Double{
        val plan = MealPlanManager().getMealPlan(target, meal)

        val carbohydrates = (ingredient.carbohydrates * 4) / ingredient.kilocalories * 100

        progressCarb = "%.1f".format(carbohydrates).plus("%")
        if (carbohydrates >= plan!!.carbMin && carbohydrates <= plan.carbMax) {
            return 1.0
        }
        else if (carbohydrates >= plan.carbMin - 5 && carbohydrates <= plan.carbMax + 5) {
            return 0.5
        }
        return 0.0
    }

    fun getTarget() {
        val plan = MealPlanManager().getMealPlan(target, meal)
        targetKilo = "ккал: ".plus(plan?.kiloMin).plus("% - ").plus(plan?.kiloMax).plus("%")
        targetProtein = "белки: ".plus(plan?.proteinMin).plus("% - ").plus(plan?.proteinMax).plus("%")
        targetFat = "жиры: ".plus(plan?.fatMin).plus("% - ").plus(plan?.fatMax).plus("%")
        targetCarb = "углеводы: ".plus(plan?.carbMin).plus("% - ").plus(plan?.carbMax).plus("%")
    }
}