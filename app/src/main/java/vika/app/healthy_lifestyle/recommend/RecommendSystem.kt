package vika.app.healthy_lifestyle.recommend

import android.content.Context
import vika.app.healthy_lifestyle.base.data.repository.food.IngredientRepository
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.PhysicalExerciseRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.main.ProductMark

class RecommendSystem(
    val context: Context,
    val target: Int,
    val meal: MealPlan
) {
    var progressKilo = ""
    var progressProtein = ""
    var progressFat = ""
    var progressCarb = ""

    var targetKilo = ""
    var targetProtein = ""
    var targetFat = ""
    var targetCarb = ""

    val record = RecordRepository(context).getRecordByDate(DateToday().getToday())!!

    fun getPhysicalExercise(ingredient: Ingredient): Pair<PhysicalExercise?, Double>{
        val kilo = ingredient.kilocalories - record.burnedKilocalories

        if (kilo > 0){
            val value = listOf(30.0, 45.0, 60.0).random()
            val weight = PersonalDataRepository(context).getWeight()
            val physicalExercises = PhysicalExerciseRepository(context).getPhysicalExerciseByTarget(value / 60.0, kilo, weight)

            if (!physicalExercises.isNullOrEmpty()) {
                return Pair(physicalExercises.shuffled().take(1)[0], value)
            }
            return Pair(null, 0.0)
        }
        return Pair(null, 0.0)
    }

    fun getProducts(count: Int): List<Ingredient>? {
        return getListProduct()?.shuffled()?.take(count)
    }

    fun getSports(count: Int): List<PhysicalExercise>? {
        val recommendSports = PhysicalExerciseRepository(context)
            .getAllPhysicalExercises()
            .filter { it.type.type in MealPlanManager().getSportPlan(target)!!.types }
            .toMutableList()

        return recommendSports?.shuffled()?.take(count)
    }

    private fun getListProduct(): List<Ingredient>? {
        val types = listOf("Фрукт", "Рыба", "Крупа", "Овощ", "Мясо", "Молочное", "Яйцо")
        return types.flatMap { type ->
            IngredientRepository(context).getAllProductByType(type) ?: emptyList()
        }
    }

    fun getMarkTopKilo(ingredient: Ingredient): Double {
        return if (ingredient.kilocalories > meal.kiloTarget) 1.0 else 0.0
    }

    private fun calculateMark(value: Double, target: Double): Double {
        val deviation = value / target
        return when {
            deviation in 0.95..1.05 -> 1.0
            deviation in 0.9..1.1 -> 0.5
            else -> -1.0
        }
    }

    fun getMarkKilo(ingredient: Ingredient): Double {
        val kilocalories = ingredient.kilocalories
        progressKilo = "%.1f ккал".format(kilocalories)
        return calculateMark(kilocalories, meal.kiloTarget)
    }

    fun getMarkProtein(ingredient: Ingredient): Double {
        val protein = ingredient.proteins
        progressProtein = "%.1f б".format(protein)
        return calculateMark(protein, meal.proteinTarget)
    }

    fun getMarkFat(ingredient: Ingredient): Double {
        val fats = ingredient.fats
        progressFat = "%.1f ж".format(fats)
        return calculateMark(fats, meal.fatTarget)
    }

    fun getMarkCarb(ingredient: Ingredient): Double {
        val carb = ingredient.carbohydrates
        progressCarb = "%.1f у".format(carb)
        return calculateMark(carb, meal.carbTarget)
    }

    fun getTarget() {
        targetKilo = "ккал: %.1f".format(meal.kiloTarget)
        targetProtein = "белки: %.1f".format(meal.proteinTarget)
        targetFat = "жиры: %.1f".format(meal.fatTarget)
        targetCarb = "углеводы: %.1f".format(meal.carbTarget)
    }

    fun getMarkProduct(product: Ingredient, value: Double): Int {
        val kilocalories = product.kilocalories * value / 100
        val protein = product.proteins * value / 100
        val fats = product.fats * value / 100
        val carb = product.carbohydrates * value / 100

        return when {
            kilocalories > meal.kiloTarget -> -1
            protein > meal.proteinTarget -> -1
            fats > meal.fatTarget -> -1
            carb > meal.carbTarget -> -1
            else -> 0
        }
    }

    fun getReplaceProduct(currentProduct: List<ProductMark>): List<ProductMark> {
        val products = mutableListOf<ProductMark>()

        var kilocalories = 0.0
        var protein = 0.0
        var fats = 0.0
        var carb = 0.0

        val replacedProduct = mutableListOf<ProductMark>()

        for (product in currentProduct) {
            if (product.mark == 0 && product.favorite) {
                if (kilocalories + product.kilocalories * product.value / 100.0 < meal.kiloTarget &&
                    protein + product.proteins * product.value / 100.0 < meal.proteinTarget &&
                    fats + product.fats * product.value / 100.0 < meal.fatTarget &&
                    carb + product.carbohydrates * product.value / 100.0 < meal.carbTarget
                ) {
                    products.add(product)
                    kilocalories += product.kilocalories * product.value / 100.0
                    protein += product.proteins * product.value / 100.0
                    fats += product.fats * product.value / 100.0
                    carb += product.carbohydrates * product.value / 100.0
                } else {
                    val value = getNewValue(
                        kilocalories, protein, fats, carb,
                        product, product.value - 10
                    )

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
                            mark = 0
                        )
                    )
                    kilocalories += product.kilocalories * value / 100.0
                    protein += product.proteins * value / 100.0
                    fats += product.fats * value / 100.0
                    carb += product.carbohydrates * value / 100.0
                }
            } else {
                replacedProduct.add(product)
            }
        }

        var residualKilo = meal.kiloTarget - kilocalories
        var residualProtein = meal.proteinTarget - protein
        var residualFat = meal.fatTarget - fats
        var residualCarb = meal.carbTarget - carb

        for (product in replacedProduct) {
            val value = product.value
            val replacedProducts = getReplacedProducts(
                value,
                if (residualKilo > 0) residualKilo else 0.0,
                if (residualProtein > 0) residualProtein else 0.0,
                if (residualFat > 0) residualFat else 0.0,
                if (residualCarb > 0) residualCarb else 0.0,
            )
            if (replacedProducts!!.isNotEmpty()) {
                val replaceProduct = replacedProducts.shuffled().take(1)[0]
                residualKilo -= replaceProduct.kilocalories * value / 100.0
                residualProtein -= replaceProduct.proteins * value / 100.0
                residualFat -= replaceProduct.fats * value / 100.0
                residualCarb -= replaceProduct.carbohydrates * value / 100.0
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
        }

        if (residualKilo > 0 || residualProtein > 0 || residualCarb > 0 || residualFat > 0) {
            val value = listOf(100.0, 150.0, 200.0, 250.0, 300.0).random()
            val replacedProducts = getReplacedProducts(
                value,
                if (residualKilo > 0) residualKilo else 0.0,
                if (residualProtein > 0) residualProtein else 0.0,
                if (residualFat > 0) residualFat else 0.0,
                if (residualCarb > 0) residualCarb else 0.0,
            )
            if (replacedProducts!!.isNotEmpty()) {
                val replaceProduct = replacedProducts.shuffled().take(1)[0]

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
        }
        return products
    }

    private fun getNewValue(kilocalories: Double, protein: Double, fats: Double, carb: Double,
                            product: ProductMark, currentValue: Double):Double{
        var value = currentValue
        while (kilocalories + product.kilocalories * value / 100.0  > meal.kiloTarget) {
            value -= 10
        }
        while (protein + product.proteins * value / 100.0 > meal.proteinTarget) {
            value -= 10
        }
        while (fats + product.fats * value / 100.0 > meal.fatTarget) {
            value -= 10
        }
        while (carb + product.carbohydrates * value / 100.0 > meal.carbTarget) {
            value -= 10
        }
        return value
    }
    private fun getReplacedProducts(
        value: Double,
        targetKilo: Double,
        targetProtein: Double,
        targetFat: Double,
        targetCarb: Double
    ): List<Ingredient>? {
        val products = mutableListOf<Ingredient>()
        val healthyProducts = getListProduct()
        val favoriteProduct = IngredientRepository(context).getAllFavoriteProducts()

        if (healthyProducts != null) {
            for (product in healthyProducts) {
                if ( product.kilocalories * value / 100.0 < targetKilo &&
                    product.proteins * value / 100.0  < targetProtein &&
                    product.fats * value / 100.0  < targetFat &&
                    product.carbohydrates * value / 100.0  < targetCarb && !product.exception){
                    products.add(product)
                }
            }

        }

        if (favoriteProduct != null) {
            for (product in favoriteProduct) {
                if ( product.kilocalories * value / 100.0  < targetKilo &&
                    product.proteins * value / 100.0  < targetProtein &&
                    product.fats * value / 100.0  < targetFat &&
                    product.carbohydrates * value / 100.0  < targetCarb && !product.exception){
                    products.add(product)
                }
            }

        }

        return products
    }
}