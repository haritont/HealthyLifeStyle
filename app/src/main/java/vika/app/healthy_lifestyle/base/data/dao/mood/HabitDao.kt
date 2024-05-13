package vika.app.healthy_lifestyle.base.data.dao.mood

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.mood.HabitEntity

@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    suspend fun getAll(): List<HabitEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitEntity: HabitEntity)
}