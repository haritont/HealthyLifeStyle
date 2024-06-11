package vika.app.healthy_lifestyle.parser

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.bean.mood.Emotion
import java.io.IOException

class ParserEmotion {
    fun fromExcel(context: Context): List<Emotion> {
        val emotions = mutableListOf<Emotion>()
        try {
            val file = context.resources.openRawResource(R.raw.emotion)
            val workbook = XSSFWorkbook(file)
            val sheet = workbook.getSheetAt(0)
            var isFirstRow = true

            for (row in sheet) {
                if (!isFirstRow) {
                    val cell = row.getCell(0)
                    if (cell != null && cell.toString().isNotEmpty()) {
                        val emotion = Emotion(
                            id = 0L,
                            name = row.getCell(0).toString(),
                            isPositive = row.getCell(1).toString() != "0"
                        )
                        emotions.add(emotion)
                    }
                } else {
                    isFirstRow = false
                }
            }
            workbook.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return emotions
    }
}