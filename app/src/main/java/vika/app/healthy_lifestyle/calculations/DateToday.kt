package vika.app.healthy_lifestyle.calculations

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateToday {
    fun getToday(): String {
        val currentDate =
            LocalDate.now()
        return formatDate(currentDate)
    }

    fun getDistanceDays(startDate: String, endDate: String): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)
        val days = ChronoUnit.DAYS.between(start, end)
        return days + 1
    }

    fun getYesterday(): String {
        val yesterday =
            LocalDate.now().minusDays(1)
        return formatDate(yesterday)
    }

    fun addDay(inputDate: String): String {
        val date =
            LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1)
        return formatDate(date)
    }

    fun subtractDay(inputDate: String): String {
        val date =
            LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).minusDays(1)
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

    private fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(formatter)
    }

    fun formatDateDDMM(date: String): String {
        val dateLoc = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formatter = DateTimeFormatter.ofPattern("dd-MM")
        return dateLoc.format(formatter)
    }

    fun formatDateDDMMYYYY(date: String): String {
        val dateLoc = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return dateLoc.format(formatter)
    }

    fun getCurrentHour(): Int{
        val currentTime = LocalDateTime.now()
        return currentTime.hour
    }

    fun getCurrentMinute(): Int{
        val currentTime = LocalDateTime.now()
        return currentTime.minute
    }
}
