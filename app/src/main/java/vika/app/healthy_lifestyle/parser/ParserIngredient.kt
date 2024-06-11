package vika.app.healthy_lifestyle.parser

import android.content.Context
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.main.Type
import java.io.FileInputStream
import java.io.IOException

class ParserIngredient {
    fun fromExcel(context: Context): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        try {
            val file = FileInputStream("raw/ingredient.xlsx")
            val workbook = XSSFWorkbook(file)
            val sheet = workbook.getSheetAt(0)
            var isFirstRow = true

            for (row in sheet) {
                if (!isFirstRow) {
                    val cell = row.getCell(0)
                    if (cell != null && cell.toString().isNotEmpty()) {
                        var type = TypeRepository(context).getByName(row.getCell(5).toString())
                        if (type == null) {
                            type = Type(0L, row.getCell(5).toString(), true)
                            TypeRepository(context).insert(type)
                        }
                        val ingredient = Ingredient(
                            id = 0L,
                            name = row.getCell(0).toString(),
                            kilocalories = row.getCell(1).toString().toDouble(),
                            proteins = row.getCell(2).toString().toDouble(),
                            fats = row.getCell(3).toString().toDouble(),
                            carbohydrates = row.getCell(4).toString().toDouble(),
                            type = type,
                            isDish = false
                        )
                        ingredients.add(ingredient)
                    }
                } else {
                    isFirstRow = false
                }
            }
            workbook.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ingredients
    }
}