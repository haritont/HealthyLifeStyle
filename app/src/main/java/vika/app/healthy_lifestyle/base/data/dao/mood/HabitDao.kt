package vika.app.healthy_lifestyle.base.data.dao.mood

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.mood.HabitEntity

@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    suspend fun getAll(): List<HabitEntity>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitEntity: HabitEntity)
    @Query("SELECT * fROM Habit WHERE product =:product")
    suspend fun getByProduct(product: String): HabitEntity?
    @Query("SELECT * fROM Habit WHERE id =:idHabit")
    suspend fun getById(idHabit: Long):HabitEntity
    @Delete
    suspend fun delete(habitEntity: HabitEntity)
    @Query("SELECT * FROM Habit WHERE name =:name")
    suspend fun getByName(name: String): HabitEntity?
}