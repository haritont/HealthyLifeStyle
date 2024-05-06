package vika.app.healthy_lifestyle.base.data.dao.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.food.NutritionEntity

@Dao
interface NutritionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nutrition: NutritionEntity)

    @Query("SELECT * FROM Nutrition WHERE date = :date")
    suspend fun getNutritionByDate(date: String): List<NutritionEntity>

    @Query("SELECT * FROM Nutrition")
    suspend fun getAllNutrition(): List<NutritionEntity>
}