package vika.app.healthy_lifestyle.base.data.repository.mood

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.mood.EmotionRecordDao
import vika.app.healthy_lifestyle.base.data.database.mood.EmotionRecordDatabase
import vika.app.healthy_lifestyle.base.data.mapper.mood.DefaultEmotionRecordMapper
import vika.app.healthy_lifestyle.base.data.mapper.mood.EmotionRecordMapper
import vika.app.healthy_lifestyle.bean.mood.EmotionRecord

class EmotionRecordRepository (context: Context){
    private val emotionRecordDao: EmotionRecordDao
    private val emotionRecordDatabase: EmotionRecordDatabase = EmotionRecordDatabase.getInstance(context)
    private val emotionRecordMapper: EmotionRecordMapper

    init {
        emotionRecordDao = emotionRecordDatabase.emotionRecordDao()
        emotionRecordMapper = DefaultEmotionRecordMapper()
    }

    fun insertEmotionRecord(emotionRecord: EmotionRecord) = runBlocking{
        emotionRecordDao.insert(emotionRecordMapper.toEmotionRecordEntity(emotionRecord))
    }

    fun getByIdAndDate(idEmotion: Long, date: String): EmotionRecord? = runBlocking{
        emotionRecordDao.getByIdAndDate(idEmotion, date)
            ?.let { emotionRecordMapper.toEmotionRecord(it) }
    }

    fun getAllByDate(date:String): List<EmotionRecord>? = runBlocking {
        emotionRecordDao.getAllByDate(date)?.let { emotionRecordMapper.toEmotionRecordList(it) }
    }

    fun deleteEmotionRecord(emotionRecord: EmotionRecord) = runBlocking{
        emotionRecordDao.delete(emotionRecord)
    }
}