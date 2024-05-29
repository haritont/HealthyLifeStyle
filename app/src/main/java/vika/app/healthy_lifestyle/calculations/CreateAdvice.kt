package vika.app.healthy_lifestyle.calculations

import android.content.Context
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.recommend.RecommendSystem

class CreateAdvice {
    fun getMoodAdvice():List<String>{
        val moodList = mutableListOf(
            "Правильное вложение – это вклад в здоровье",
            "Если занимаешься спортом, жизнь кажется прекраснее",
            "В здоровом теле — здоровый дух",
            "Веселые люди быстрее выздоравливают и дольше живут",
            "Все здоровые люди любят жизнь",
            "Вся твоя еда должна быть твоим лекарством",
            "Движение — кладовая жизни",
            "Здоровье — мудрых гонорар…",
            "Сон — бальзам природы",
            "В мире нет ничего такого, из-за чего стоит портить нервы",
            "Здоровье так же заразительно, как и болезнь"
        )
        return moodList.shuffled().take(1)
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