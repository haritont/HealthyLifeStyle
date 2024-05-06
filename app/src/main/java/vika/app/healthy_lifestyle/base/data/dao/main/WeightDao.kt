package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.main.WeightEntity

@Dao
interface WeightDao {
    @Insert
    suspend fun insert(vararg weight: WeightEntity)

    @Query("SELECT * FROM Weight")
    suspend fun getAll(): List<WeightEntity>

    @Query("SELECT * FROM Weight ORDER BY id DESC LIMIT 1")
    suspend fun getLastEntry(): WeightEntity?
}