package vika.app.healthy_lifestyle.base.data.repository.food

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.food.NutritionDao
import vika.app.healthy_lifestyle.base.data.database.food.NutritionDatabase
import vika.app.healthy_lifestyle.base.data.mapper.food.DefaultNutritionMapper
import vika.app.healthy_lifestyle.base.data.mapper.food.NutritionMapper
import vika.app.healthy_lifestyle.bean.food.Nutrition

class NutritionRepository (context: Context){
    private val nutritionDao: NutritionDao
    private val nutritionDatabase: NutritionDatabase = NutritionDatabase.getInstance(context)
    private val nutritionMapper: NutritionMapper

    init {
        nutritionDao = nutritionDatabase.nutritionDao()
        nutritionMapper = DefaultNutritionMapper()
    }

    fun insertNutrition(nutrition: Nutrition) = runBlocking{
        nutritionDao.insert(nutritionMapper.toNutritionEntity(nutrition))
    }

    fun getNutritionByDate(date: String): List<Nutrition> = runBlocking{
        nutritionMapper.toNutritionList(nutritionDao.getNutritionByDate(date))
    }

    fun getAllNutrition(): List<Nutrition> = runBlocking{
        nutritionMapper.toNutritionList(nutritionDao.getAllNutrition())
    }

    fun deleteNutrition(name: String, value: Double, date: String) = runBlocking{
        nutritionDao.delete(name, value, date)
    }
}