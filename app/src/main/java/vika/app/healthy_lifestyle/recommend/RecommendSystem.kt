package vika.app.healthy_lifestyle.recommend

import android.content.Context
import vika.app.healthy_lifestyle.base.data.repository.food.IngredientRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.PhysicalExerciseRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.main.ProductMark

class RecommendSystem(
    val context: Context,
    val target: Int,
    val meal: Int
) {
    var progressKilo = ""
    var progressProtein = ""
    var progressFat = ""
    var progressCarb = ""

    var targetKilo = ""
    var targetProtein = ""
    var targetFat = ""
    var targetCarb = ""

    fun getProducts(count: Int): List<Ingredient>?{
        return getListProduct()?.shuffled()?.take(count)
    }

    fun getSports(count: Int): List<PhysicalExercise>?{
        val recommendSports = mutableListOf<PhysicalExercise>()
        val physicalExercises = PhysicalExerciseRepository(context).getAllPhysicalExercises()
        val plan = MealPlanManager().getSportPlan(target)

        for (physicalExercise in physicalExercises){
            if (physicalExercise.type in plan!!.types){
                recommendSports.add(physicalExercise)
            }
        }
        return recommendSports?.shuffled()?.take(count)
    }
    private fun getListProduct(): List<Ingredient>?{
        val types = listOf("Фрукт", "Рыба", "Крупа", "Овощ", "Мясо", "Молочное", "Яйцо")
        val products = mutableListOf<Ingredient>()
        for (type in types){
            val productList = IngredientRepository(context).getAllProductByType(type)
            if (productList != null) {
                products.addAll(productList)
            }
        }
        return products
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

    fun getMarkProduct(product: Ingredient, value: Double): Int {
        val plan = MealPlanManager().getMealPlan(target, meal)

        val target = RecordRepository(context).getRecordByDate(DateToday().getToday())
        val kilocalories = product.kilocalories * value / 100
        val protein = product.proteins * value / 100
        val fats = product.fats * value / 100
        val carb = product.carbohydrates * value / 100

        if (kilocalories / target!!.targetKilocalories * 100 > plan!!.kiloMax) {
            return -1
        }
        if (protein / target.targetProteins * 100 > plan.proteinMax) {
            return -2
        }
        if (fats / target.targetCarbohydrates * 100 > plan.fatMax) {
            return -3
        }
        if (carb / target.targetCarbohydrates * 100 > plan.carbMax) {
            return -4
        }
        return 0
    }

    fun getReplaceProduct(currentProduct: List<ProductMark>): List<ProductMark>{
        val products = mutableListOf<ProductMark>()
        val sortedProduct = currentProduct.sortedWith(compareBy<ProductMark> { it.mark != 0 }
            .thenByDescending { it.favorite })

        val plan = MealPlanManager().getMealPlan(target, meal)
        val target = RecordRepository(context).getRecordByDate(DateToday().getToday())

        var kilocalories = 0.0
        var protein = 0.0
        var fats = 0.0
        var carb = 0.0

        val replacedProduct = mutableListOf<ProductMark>()

        for (product in sortedProduct){
            if (product.mark == 0){
                products.add(product)
                kilocalories += product.kilocalories * product.value / 100.0
                protein += product.proteins * product.value / 100.0
                fats += product.fats * product.value / 100.0
                carb += product.carbohydrates * product.value / 100.0
            }
            else {
                if (product.favorite) {
                    var value = product.value
                    when (product.mark) {
                        -1 -> {
                            while (product.kilocalories * value / 100.0 / target!!.targetKilocalories * 100.0 > plan!!.kiloMax){
                                value -= value * 0.1
                            }
                        }
                        -2 -> {
                            while (product.proteins * value / 100.0 / target!!.targetProteins * 100.0 > plan!!.proteinMax){
                                value -= value * 0.1
                            }
                        }
                        -3 -> {
                            while (product.fats * value / 100.0 / target!!.targetFats * 100.0 > plan!!.fatMax){
                                value -= value * 0.1
                            }
                        }
                        else -> {
                            while (product.carbohydrates * value / 100.0 / target!!.targetCarbohydrates * 100.0 > plan!!.carbMax){
                                value -= value * 0.1
                            }
                        }
                    }
                    products.add(
                        ProductMark(
                            name = product.name,
                            kilocalories = product.kilocalories,
                            proteins = product.proteins,
                            fats = product.fats,
                            carbohydrates = product.carbohydrates,
                            value = value,
                            exception = product.exception,
                            favorite = true,
                            mark = product.mark
                        )
                    )
                    kilocalories += product.kilocalories * value / 100.0
                    protein += product.proteins * value / 100.0
                    fats += product.fats * value / 100.0
                    carb += product.carbohydrates * value / 100.0
                }
                else{
                    replacedProduct.add(product)
                }
            }
        }

        var targetKilo = (plan!!.kiloMax.toDouble() / 100.0 * target!!.targetKilocalories) - kilocalories
        var targetProtein = (plan.proteinMax.toDouble() / 100.0 * target.targetProteins) - protein
        var targetFat = (plan.fatMax.toDouble() / 100.0 * target.targetFats) - fats
        var targetCarb = (plan.carbMax.toDouble() / 100.0 * target.targetCarbohydrates) - carb
        for (product in replacedProduct){
            val value = product.value
            val replaceProduct = IngredientRepository(context).getIngredientByValueTarget(
                value,
                targetKilo,
                targetProtein,
                targetFat,
                targetCarb
            ).shuffled().take(1)[0]
            targetKilo -= replaceProduct.kilocalories * value / 100.0
            targetProtein -= replaceProduct.proteins  * value / 100.0
            targetFat -= replaceProduct.fats  * value / 100.0
            targetCarb -= replaceProduct.carbohydrates  * value / 100.0
            products.add(
                ProductMark(
                    name = product.name,
                    kilocalories = product.kilocalories,
                    proteins = product.proteins,
                    fats = product.fats,
                    carbohydrates = product.carbohydrates,
                    value = value,
                    exception = product.exception,
                    favorite = product.favorite,
                    mark = product.mark,
                    replacement = replaceProduct.name
                )
            )
        }

        if (targetKilo > 0 || targetProtein > 0 || targetCarb > 0 || targetFat > 0){
            val value = listOf(100.0, 150.0, 200.0, 250.0, 300.0).random()
            val replaceProduct = IngredientRepository(context).getIngredientByValueTarget(
                value,
                targetKilo,
                targetProtein,
                targetFat,
                targetCarb
            ).shuffled().take(1)[0]

            targetKilo -= replaceProduct.kilocalories * value / 100
            targetProtein -= replaceProduct.proteins  * value / 100
            targetFat -= replaceProduct.fats  * value / 100
            targetCarb -= replaceProduct.carbohydrates  * value / 100

            products.add(
                ProductMark(
                    name = replaceProduct.name,
                    kilocalories = replaceProduct.kilocalories,
                    proteins = replaceProduct.proteins,
                    fats = replaceProduct.fats,
                    carbohydrates = replaceProduct.carbohydrates,
                    value = value,
                    exception = replaceProduct.exception,
                    favorite = replaceProduct.favorite,
                    mark = 0
                )
            )
        }

        return products
    }
}