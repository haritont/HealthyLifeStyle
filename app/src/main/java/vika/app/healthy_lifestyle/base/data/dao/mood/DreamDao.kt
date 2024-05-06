package vika.app.healthy_lifestyle.base.data.dao.mood

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.mood.DreamEntity

@Dao
interface DreamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dream: DreamEntity)

    @Query("SELECT * FROM Dream WHERE date = :today")
    suspend fun getDream(today: String): DreamEntity
}