package vika.app.healthy_lifestyle.calculations

import android.os.Build
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
    fun count(personalData: PersonalData) {
        val weight = personalData.weight ?: 0.0
        val age = calculateAge(personalData.birthDate)
        val activityFactor = personalData.activityRate ?: 1.0

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

        proteins = round((kilocalories * 0.3) / 4.1)
        fats = round((kilocalories * 0.3) / 9.3)
        carbohydrates = round((kilocalories * 0.4) / 4.1)
        kilocalories = round(kilocalories)

        water = (30 * weight) / 1000
    }

    private fun calculateAge(birthDate: String?): Int {
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val birthLocalDate = LocalDate.parse(birthDate, formatter)
        val currentDate = LocalDate.now()
        return birthLocalDate.until(currentDate, ChronoUnit.YEARS).toInt()
    }
}