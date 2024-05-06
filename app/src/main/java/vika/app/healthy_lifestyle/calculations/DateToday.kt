package vika.app.healthy_lifestyle.calculations

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateToday {
    fun getToday(): String {
        val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return formatDate(currentDate)
    }

    fun getYesterday(): String {
        val yesterday = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().minusDays(1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return formatDate(yesterday)
    }

    fun addDay(inputDate: String): String {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return formatDate(date)
    }

    fun subtractDay(inputDate: String): String {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).minusDays(1)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return formatDate(date)
    }

    fun getWeek(): List<String>{
        val week = mutableListOf<String>()
        var currentDay = getToday()
        for (index in 1..7){
            week.add(currentDay)
            currentDay = subtractDay(currentDay)
        }
        return week
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateDDMM(date: String): String {
        val dateLoc = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formatter = DateTimeFormatter.ofPattern("dd-MM")
        return dateLoc.format(formatter)
    }
}
