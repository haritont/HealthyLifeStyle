package vika.app.healthy_lifestyle.server.api

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.main.PersonalData
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

    override fun authorization(login: String, password: String): String? = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.authorization(login, password)
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка аторизации. Код: ${e.message}")
            null
        }
    }

    override fun getPersonalData(token: String): PersonalData? = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.getPersonalData(token)
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка получения личных данных. Код: ${e.message}")
            null
        }
    }

    override fun registration(
        login: String,
        password: String,
        personalData: PersonalData,
    ): String? = runBlocking{
        try {
            val response = withContext(Dispatchers.IO) {
                ApiServiceHelper.apiService.registration(login, password, personalData)
            }
            response
        } catch (e: Exception) {
            Log.e("ConnectionError", "Ошибка регитрации. Код: ${e.message}")
            null
        }
    }
}