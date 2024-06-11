package vika.app.healthy_lifestyle.base.data.dao.mood

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.mood.HabitRecordEntity

@Dao
interface HabitRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habitRecordEntity: HabitRecordEntity)

    @Query("SELECT * FROM HabitRecord WHERE idHabit =:idHabit AND tracking =:tracking")
    suspend fun getRecordByIdHabit(idHabit: Long, tracking: Boolean): HabitRecordEntity?
    @Query("SELECT * FROM HabitRecord WHERE idHabit =:idHabit")
    suspend fun getLastHabitRecord(idHabit: Long): HabitRecordEntity?
}