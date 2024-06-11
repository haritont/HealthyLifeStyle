package vika.app.healthy_lifestyle.parser

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import vika.app.healthy_lifestyle.bean.mood.Habit
import java.io.FileInputStream
import java.io.IOException

class ParserHabit {
    fun fromExcel(context: Context): List<Habit> {
        val habits = mutableListOf<Habit>()
        try {
            val file = FileInputStream("raw/habit.xlsx")
            val workbook = XSSFWorkbook(file)
            val sheet = workbook.getSheetAt(0)
            var isFirstRow = true

            for (row in sheet) {
                if (!isFirstRow) {
                    val cell = row.getCell(0)
                    if (cell != null && cell.toString().isNotEmpty()) {
                        val habit = Habit(
                            id = 0L,
                            name = row.getCell(0).toString(),
                            product = row.getCell(1).toString(),
                            isPositive = if (row.getCell(2).toString() == "0") false else true
                        )
                        habits.add(habit)
                    }
                } else {
                    isFirstRow = false
                }
            }
            workbook.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return habits
    }
}