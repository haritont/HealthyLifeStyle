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
            val physicalExercises = PhysicalExerciseRepository(context).getPhysicalExerciseByTarget(value, kilo, weight)

            return Pair(physicalExercises?.shuffled()?.take(1)!![0], value)
        }
        return Pair(null, 0.0)
    }

    fun getProducts(count: Int): List<Ingredient>? {
        return getListProduct()?.shuffled()?.take(count)
    }

    fun getSports(count: Int): List<PhysicalExercise>? {
        val recommendSports = mutableListOf<PhysicalExercise>()
        val physicalExercises = PhysicalExerciseRepository(context).getAllPhysicalExercises()
        val plan = MealPlanManager().getSportPlan(target)

        for (physicalExercise in physicalExercises) {
            if (physicalExercise.type.type in plan!!.types) {
                recommendSports.add(physicalExercise)
            }
        }
        return recommendSports?.shuffled()?.take(count)
    }

    private fun getListProduct(): List<Ingredient>? {
        val types = listOf("Фрукт", "Рыба", "Крупа", "Овощ", "Мясо", "Молочное", "Яйцо")
        val products = mutableListOf<Ingredient>()
        for (type in types) {
            val productList = IngredientRepository(context).getAllProductByType(type)
            if (productList != null) {
                products.addAll(productList)
            }
        }
        return products
    }

    fun getMarkTopKilo(ingredient: Ingredient): Double {
        val kilocalories = ingredient.kilocalories

        if (kilocalories > meal.kiloTarget) {
            return 1.0
        }
        return 0.0
    }

    fun getMarkKilo(ingredient: Ingredient): Double {
        val kilocalories = ingredient.kilocalories

        progressKilo = "%.1f".format(kilocalories).plus(" ккал")
        if (kilocalories >= meal.kiloTarget * 0.95 && kilocalories <=  meal.kiloTarget * 1.05) {
            return 1.0
        } else if (kilocalories >= meal.kiloTarget * 0.9 && kilocalories <= meal.kiloTarget * 1.1) {
            return 0.5
        }else if (kilocalories < meal.kiloTarget * 0.90){
            return -1.0
        }
        return 0.0
    }

    fun getMarkProtein(ingredient: Ingredient): Double {
        val protein = ingredient.proteins

        progressProtein = "%.1f".format(protein).plus(" б")
        if (protein >= meal.proteinTarget * 0.95 && protein <= meal.proteinTarget * 1.05) {
            return 1.0
        } else if (protein >= meal.proteinTarget * 0.90 && protein <= meal.proteinTarget * 1.1) {
            return 0.5
        }else if (protein < meal.proteinTarget * 0.90){
            return -1.0
        }
        return 0.0
    }

    fun getMarkFat(ingredient: Ingredient): Double {
        val fats = ingredient.fats

        progressFat = "%.1f".format(fats).plus(" ж")
        if (fats >= meal.fatTarget * 0.95 && fats <= meal.fatTarget * 1.05) {
            return 1.0
        } else if (fats >= meal.fatTarget * 0.90 && fats <= meal.fatTarget * 1.1) {
            return 0.5
        }else if (fats < meal.fatTarget * 0.90){
            return -1.0
        }
        return 0.0
    }

    fun getMarkCarb(ingredient: Ingredient): Double {
        val carb = ingredient.carbohydrates

        progressCarb = "%.1f".format(carb).plus(" у")
        if (carb >= meal.carbTarget * 0.95 && carb <= meal.carbTarget * 1.05) {
            return 1.0
        } else if (carb >= meal.carbTarget * 0.90 && carb <= meal.carbTarget * 1.1) {
            return 0.5
        } else if (carb < meal.carbTarget * 0.90){
            return -1.0
        }
        return 0.0
    }

    fun getTarget() {
        targetKilo = "ккал: ".plus("%.1f".format(meal.kiloTarget))
        targetProtein = "белки: ".plus("%.1f".format(meal.proteinTarget))
        targetFat = "жиры: ".plus("%.1f".format(meal.fatTarget))
        targetCarb = "углеводы: ".plus("%.1f".format(meal.carbTarget))
    }

    fun getMarkProduct(product: Ingredient, value: Double): Int {
        val kilocalories = product.kilocalories * value / 100
        val protein = product.proteins * value / 100
        val fats = product.fats * value / 100
        val carb = product.carbohydrates * value / 100

        if (kilocalories > meal.kiloTarget) {
            return -1
        }
        if (protein > meal.proteinTarget) {
            return -1
        }
        if (fats > meal.fatTarget) {
            return -1
        }
        if (carb > meal.carbTarget) {
            return -1
        }
        return 0
    }

    fun getReplaceProduct(currentProduct: List<ProductMark>): List<ProductMark> {
        val products = mutableListOf<ProductMark>()

        var kilocalories = 0.0
        var protein = 0.0
        var fats = 0.0
        var carb = 0.0

        val replacedProduct = mutableListOf<ProductMark>()

        for (product in currentProduct) {
            if (product.mark == 0) {
                if (kilocalories + product.kilocalories * product.value / 100.0 < meal.kiloTarget &&
                    protein + product.proteins * product.value / 100.0 < meal.proteinTarget &&
                    fats + product.fats * product.value / 100.0  < meal.fatTarget &&
                    carb + product.carbohydrates * product.value / 100.0 < meal.carbTarget) {
                    products.add(product)
                    kilocalories += product.kilocalories * product.value / 100.0
                    protein += product.proteins * product.value / 100.0
                    fats += product.fats * product.value / 100.0
                    carb += product.carbohydrates * product.value / 100.0
                }
                else {
                    var value = product.value - 10
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
                if (product.favorite) {
                    var value = product.value - 10
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
                } else {
                    replacedProduct.add(product)
                }
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