package vika.app.healthy_lifestyle.server.api

import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.mood.Emotion
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise

interface ApiServiceRepository {

    fun getAllIngredients(): List<Ingredient>

    fun getAllPhysicalExercise(): List<PhysicalExercise>

    fun getAllHabits(): List<Habit>

    fun getAllEmotions(): List<Emotion>
}