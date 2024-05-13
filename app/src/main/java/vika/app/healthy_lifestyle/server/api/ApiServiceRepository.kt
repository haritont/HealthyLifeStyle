package vika.app.healthy_lifestyle.server.api

import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.recommend.RecommendProduct

interface ApiServiceRepository {

    fun getAllIngredients(): List<Ingredient>

    fun getAllPhysicalExercise(): List<PhysicalExercise>

    fun getRecommendProductList(): List<RecommendProduct>

    fun getAllHabits(): List<Habit>
}