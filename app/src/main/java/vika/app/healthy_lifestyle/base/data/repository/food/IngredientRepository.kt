package vika.app.healthy_lifestyle.base.data.repository.food

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.food.IngredientDao
import vika.app.healthy_lifestyle.base.data.database.food.IngredientDatabase
import vika.app.healthy_lifestyle.base.data.mapper.food.DefaultIngredientMapper
import vika.app.healthy_lifestyle.base.data.mapper.food.IngredientMapper
import vika.app.healthy_lifestyle.bean.food.Ingredient

class IngredientRepository(context: Context) {
    private val ingredientDao: IngredientDao
    private val ingredientDatabase: IngredientDatabase = IngredientDatabase.getInstance(context)
    private val ingredientMapper: IngredientMapper

    init {
        ingredientDao = ingredientDatabase.ingredientDao()
        ingredientMapper = DefaultIngredientMapper()
    }

    fun updateIngredientException(name: String, exception: Boolean) = runBlocking{
        ingredientDao.updateException(name, exception)
    }

    fun updateIngredientFavorite(name: String, favorite: Boolean) = runBlocking{
        ingredientDao.updateFavorite(name, favorite)
    }

    fun insertIngredient(ingredient: Ingredient) = runBlocking{
        ingredientDao.insert(ingredientMapper.toIngredientEntity(ingredient))
    }

    fun getIngredientName(ingredientId: Long): String = runBlocking{
        ingredientDao.getName(ingredientId)
    }

    fun getAllIngredients(): List<Ingredient> = runBlocking{
        ingredientMapper.toIngredientList(ingredientDao.getAllIngredients())
    }

    fun getAllDishes(): List<Ingredient> = runBlocking{
        ingredientMapper.toIngredientList(ingredientDao.getAllDishes())
    }

    fun getIngredientByName(name: String): Ingredient = runBlocking{
        ingredientMapper.toIngredient(ingredientDao.getByName(name))
    }

    fun getIngredientById(id: Long): Ingredient = runBlocking{
        ingredientMapper.toIngredient(ingredientDao.getById(id))
    }

    fun getIngredientRowCount(): Int = runBlocking{
        ingredientDao.getRowCount()
    }

    fun getNamesIngredients(): List<String> = runBlocking {
        ingredientDao.getAllNames()
    }

    fun isIngredientExists(nameIngredient: String): Boolean = runBlocking{
        ingredientDao.isIngredientExists(nameIngredient) != 0
    }
}
