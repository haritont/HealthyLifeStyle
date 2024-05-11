package vika.app.healthy_lifestyle.base.data.repository.food

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.food.RecipeDao
import vika.app.healthy_lifestyle.base.data.database.food.RecipeDatabase
import vika.app.healthy_lifestyle.base.data.mapper.food.DefaultRecipeMapper
import vika.app.healthy_lifestyle.base.data.mapper.food.RecipeMapper
import vika.app.healthy_lifestyle.bean.food.Recipe

class RecipeRepository (context: Context){
    private val recipeDao: RecipeDao
    private val recipeDatabase: RecipeDatabase = RecipeDatabase.getInstance(context)
    private val recipeMapper: RecipeMapper

    init {
        recipeDao = recipeDatabase.recipeDao()
        recipeMapper = DefaultRecipeMapper()
    }

    fun insertRecipe(recipe: Recipe) = runBlocking{
        recipeDao.insert(recipeMapper.toRecipeEntity(recipe))
    }

    fun getRecipesForDish(idDish: Long): List<Recipe> = runBlocking{
        recipeMapper.toRecipeList(recipeDao.getRecipesForDish(idDish))
    }

    fun deleteAllRecipe(idDish: Long) = runBlocking{
        recipeDao.deleteAll(idDish)
    }
}