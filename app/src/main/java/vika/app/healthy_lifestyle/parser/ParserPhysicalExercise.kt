package vika.app.healthy_lifestyle.parser

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.bean.main.Type
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import java.io.IOException

class ParserPhysicalExercise {
    fun fromExcel(context: Context): List<PhysicalExercise> {
        val physicalExercises = mutableListOf<PhysicalExercise>()
        try {
            val file = context.resources.openRawResource(R.raw.physical_exercise)
            val workbook = XSSFWorkbook(file)
            val sheet = workbook.getSheetAt(0)
            var isFirstRow = true

            for (row in sheet) {
                if (!isFirstRow) {
                    val cell = row.getCell(0)
                    if (cell != null && cell.toString().isNotEmpty()) {
                        var type = TypeRepository(context).getByName(row.getCell(2).toString())
                        if (type == null) {
                            type = Type(0L, row.getCell(2).toString(), true)
                            TypeRepository(context).insert(type)
                        }
                        val physicalExercise = PhysicalExercise(
                            id = 0L,
                            name = row.getCell(0).toString(),
                            met = row.getCell(1).toString().toDouble(),
                            type = type,
                            training = false
                        )
                        physicalExercises.add(physicalExercise)
                    }
                } else {
                    isFirstRow = false
                }
            }
            workbook.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return physicalExercises
    }
}