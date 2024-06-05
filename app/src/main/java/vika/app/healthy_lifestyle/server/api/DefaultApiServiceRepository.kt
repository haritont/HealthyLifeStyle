package vika.app.healthy_lifestyle.server.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.mood.Emotion
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise

class DefaultApiServiceRepository : ApiServiceRepository {

    override fun getAllIngredients(): List<Ingredient> = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.getAllIngredients()
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка получения списка всех блюд с сервера. Код: ${e.message}")
            emptyList()
        }
    }

    override fun getAllPhysicalExercise(): List<PhysicalExercise>  = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.getAllPhysicalExercise()
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка получения списка всех упражнений с сервера. Код: ${e.message}")
            emptyList()
        }
    }

    override fun getAllHabits(): List<Habit> = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.getAllHabits()
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка получения списка всех привычек с сервера. Код: ${e.message}")
            emptyList()
        }
    }

    override fun getAllEmotions(): List<Emotion> = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.getAllEmotions()
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка получения списка всех эмоций с сервера. Код: ${e.message}")
            emptyList()
        }
    }
}