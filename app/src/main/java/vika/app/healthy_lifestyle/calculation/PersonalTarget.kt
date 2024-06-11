package vika.app.healthy_lifestyle.calculation

import android.content.Context
import android.content.SharedPreferences
import vika.app.healthy_lifestyle.bean.main.PersonalData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.round

class PersonalTarget(
    var kilocalories: Double = 0.0,
    var proteins: Double = 0.0,
    var fats: Double = 0.0,
    var carbohydrates: Double = 0.0,
    var water: Double = 0.0
) {
    fun count(personalData: PersonalData, context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        kilocalories = sharedPreferences.getString("kilocalories", null)?.toDouble() ?: 0.0
        proteins = sharedPreferences.getString("proteins", null)?.toDouble() ?: 0.0
        fats = sharedPreferences.getString("fats", null)?.toDouble() ?: 0.0
        carbohydrates = sharedPreferences.getString("carbs", null)?.toDouble() ?: 0.0

        val weight = personalData.weight
        val age = calculateAge(personalData.birthDate)
        val activityFactor = personalData.activityRate
        val target = personalData.target

        if (kilocalories == 0.0) {
            kilocalories = when {
                personalData.genderId == 1 && age in 18..30 ->
                    (0.062 * weight + 2.036) * 240 * activityFactor

                personalData.genderId == 1 && age in 31..60 ->
                    (0.034 * weight + 3.538) * 240 * activityFactor

                personalData.genderId == 1 && age > 60 ->
                    (0.038 * weight + 2.755) * 240 * activityFactor

                personalData.genderId == 2 && age in 18..30 ->
                    (0.063 * weight + 2.896) * 240 * activityFactor

                personalData.genderId == 2 && age in 31..60 ->
                    (0.048 * weight + 3.653) * 240 * activityFactor

                personalData.genderId == 2 && age > 60 ->
                    (0.049 * weight + 2.459) * 240 * activityFactor

                else -> 0.0
            }


            if (target == 0) {
                kilocalories += kilocalories * 0.2
            } else if (target == 2) {
                kilocalories -= kilocalories * 0.2
            }
        }

        if (proteins == 0.0) {
            proteins = round((kilocalories * 0.35) / 4.1)
        }
        if (fats == 0.0) {
            fats = round((kilocalories * 0.35) / 9.3)
        }
        if (carbohydrates == 0.0) {
            carbohydrates = round((kilocalories * 0.65) / 4.1)
        }
        kilocalories = round(kilocalories)

        water = (30 * weight) / 1000
    }

    private fun calculateAge(birthDate: String?): Int {
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthLocalDate = LocalDate.parse(birthDate, formatter)
        val currentDate = LocalDate.now()
        return birthLocalDate.until(currentDate, ChronoUnit.YEARS).toInt()
    }
}