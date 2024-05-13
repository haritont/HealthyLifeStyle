package vika.app.healthy_lifestyle.server.api

import retrofit2.http.GET
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.recommend.RecommendProduct

interface ApiService {
    @GET("/getAllIngredients")
    suspend fun getAllIngredients(): List<Ingredient>

    @GET("/getAllPhysicalExercise")
    suspend fun getAllPhysicalExercise(): List<PhysicalExercise>

    @GET("/getDataProduct")
    suspend fun getRecommendProductList(): List<RecommendProduct>
}