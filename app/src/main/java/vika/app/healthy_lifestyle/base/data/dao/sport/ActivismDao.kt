package vika.app.healthy_lifestyle.base.data.dao.sport

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.sport.ActivismEntity

@Dao
interface ActivismDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activism: ActivismEntity)

    @Query("SELECT * FROM Activism WHERE date = :date")
    suspend fun getActivismByDate(date: String): List<ActivismEntity>
}