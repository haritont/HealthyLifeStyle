package vika.app.healthy_lifestyle.base.data.dao.sport

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.sport.TrainingEntity

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: TrainingEntity)

    @Query("SELECT * FROM Training WHERE idName = :idName")
    suspend fun getTraining(idName: Long): List<TrainingEntity>
}