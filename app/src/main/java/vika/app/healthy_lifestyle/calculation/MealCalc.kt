package vika.app.healthy_lifestyle.calculation

import java.time.LocalTime

class MealCalc {

    fun getCurrentMeal(): String{
        return when (LocalTime.now().hour) {
            in 7..9 -> {
                "Завтрак"
            }

            in 12..14 -> {
                "Обед"
            }

            in 17..19 -> {
                "Ужин"
            }

            else -> "Перекус"
        }
    }

    fun getIndexMeal(meal: String): Int{
        return when (meal) {
            "Завтрак" -> {
                0
            }

            "Обед" -> {
                1
            }

            "Ужин" -> {
                2
            }

            else -> 3
        }
    }
}