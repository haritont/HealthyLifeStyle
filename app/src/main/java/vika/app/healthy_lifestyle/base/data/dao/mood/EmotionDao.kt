package vika.app.healthy_lifestyle.base.data.dao.mood

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.mood.EmotionEntity

@Dao
interface EmotionDao {
    @Query("SELECT * FROM Emotion")
    suspend fun getAll(): List<EmotionEntity>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emotionEntity: EmotionEntity)
    @Query("SELECT * fROM Emotion WHERE name =:name")
    suspend fun getByName(name: String): EmotionEntity
}