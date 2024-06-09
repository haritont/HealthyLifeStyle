package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.main.TypeEntity

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(type: TypeEntity)

    @Query("SELECT type FROM Type WHERE isProduct = 1")
    suspend fun getAllTypeByProduct(): List<String>?

    @Query("SELECT type FROM Type WHERE isProduct = 0")
    suspend fun getAllTypeByPhys(): List<String>?
}