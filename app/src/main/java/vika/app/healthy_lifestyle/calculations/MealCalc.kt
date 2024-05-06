package vika.app.healthy_lifestyle.calculations

import android.os.Build
import java.time.LocalTime

class MealCalc {

    fun getCurrentMeal(): String{
        val currentHour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now().hour
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        return when (currentHour) {
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

    fun getIndexCurrentMeal(): Int{
        return when (getCurrentMeal()) {
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