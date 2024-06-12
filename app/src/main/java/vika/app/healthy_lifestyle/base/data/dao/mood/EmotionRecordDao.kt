package vika.app.healthy_lifestyle.base.data.dao.mood

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.mood.EmotionRecordEntity

@Dao
interface EmotionRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emotionRecordEntity: EmotionRecordEntity)
    @Query("SELECT * FROM EmotionRecord WHERE idEmotion =:idEmotion AND date =:date")
    suspend fun getByIdAndDate(idEmotion: Long, date: String): EmotionRecordEntity?

    @Query("SELECT * FROM EmotionRecord WHERE date =:date")
    suspend fun getAllByDate(date: String): List<EmotionRecordEntity>?
    @Delete
    suspend fun delete(emotionRecord: EmotionRecordEntity)
}