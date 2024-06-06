package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.main.WeightEntity

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weight: WeightEntity)

    @Query("SELECT * FROM Weight")
    suspend fun getAll(): List<WeightEntity>

    @Query("SELECT * FROM Weight ORDER BY id DESC LIMIT 1")
    suspend fun getLastEntry(): WeightEntity?
    @Query("SELECT * FROM Weight WHERE date =:date")
    suspend fun getByDate(date: String): WeightEntity?

    @Query("UPDATE Weight SET value =:value WHERE date =:date")
    suspend fun updateWeightByDate(date: String, value: Double)
}