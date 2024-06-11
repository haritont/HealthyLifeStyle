package vika.app.healthy_lifestyle.calculation

import android.content.Context
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.recommend.MealPlan
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

        val products =
            RecommendSystem(context, data.target, MealPlan()).getProducts(1)

        if (products!!.isNotEmpty()) {
            return advice.plus(products[0].name)
        }
        return "Совет скоро появится"
    }

    fun getSportAdvice(context: Context, data: PersonalData): String{
        val advice = "Попробуйте заняться: "
        val physicalExercise = RecommendSystem(context, data.target, MealPlan()).getSports(1)

        if (physicalExercise!!.isNotEmpty()) {
            return advice.plus(physicalExercise[0].name)
        }
        return "Совет скоро появится"
    }
}