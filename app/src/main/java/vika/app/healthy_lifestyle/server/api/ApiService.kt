package vika.app.healthy_lifestyle.server.api

import retrofit2.http.GET
import retrofit2.http.Query
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.bean.mood.Emotion
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise

interface ApiService {
    @GET("/getAllIngredients")
    suspend fun getAllIngredients(): List<Ingredient>

    @GET("/getAllPhysicalExercise")
    suspend fun getAllPhysicalExercise(): List<PhysicalExercise>

    @GET("/getAllHabits")
    suspend fun getAllHabits(): List<Habit>

    @GET("/getAllEmotions")
    suspend fun getAllEmotions(): List<Emotion>

    @GET("/authorization")
    suspend fun authorization(@Query("login") login: String, @Query("password") password: String): String

    @GET("/getPersonalData")
    suspend fun getPersonalData(@Query("token") token: String): PersonalData

    @GET("/registration")
    suspend fun registration(
        @Query("login") login: String, @Query("password") password: String,
        @Query("id") id: Long, @Query("token") token: String,
        @Query("genderId") genderId: Int, @Query("height") height: Double,
        @Query("weight") weight: Double, @Query("birthDate") birthDate: String,
        @Query("activityRate") activityRate: Double, @Query("name") name: String,
        @Query("target") target: Int
    ): String

}