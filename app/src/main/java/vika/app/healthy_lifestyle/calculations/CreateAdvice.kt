package vika.app.healthy_lifestyle.calculations

import android.content.Context
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.recommend.RecommendSystem

class CreateAdvice {
    fun getAdvice():String{
        return "Совет скоро появится"
    }

    fun getProductAdvice(context: Context, data: PersonalData, meal: String): String {
        val advice = "Попробуйте на ".plus(meal.lowercase()).plus(" : ")

        val products = RecommendSystem(context, data.target, MealCalc().getIndexMeal(meal)).getProducts(1)

        return advice.plus(products[0].product)
    }

    fun getSportAdvice(context: Context, data: PersonalData): String{
        val advice = "Попробуйте заняться: "
        val physicalExercise = RecommendSystem(context, data.target, 0).getSports(1)
        return advice.plus(physicalExercise[0].name)
    }
}