package vika.app.healthy_lifestyle.base.data.repository.mood

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.mood.EmotionDao
import vika.app.healthy_lifestyle.base.data.database.mood.EmotionDatabase
import vika.app.healthy_lifestyle.base.data.mapper.mood.DefaultEmotionMapper
import vika.app.healthy_lifestyle.base.data.mapper.mood.EmotionMapper
import vika.app.healthy_lifestyle.bean.mood.Emotion

class EmotionRepository (context: Context){
    private val emotionDao: EmotionDao
    private val emotionDatabase: EmotionDatabase = EmotionDatabase.getInstance(context)
    private val emotionMapper: EmotionMapper

    init {
        emotionDao = emotionDatabase.emotionDao()
        emotionMapper = DefaultEmotionMapper()
    }
    fun getAllEmotions(): List<Emotion>? = runBlocking{
        emotionDao.getAll()?.let { emotionMapper.toEmotionList(it) }
    }

    fun insertEmotion(emotion: Emotion) = runBlocking{
        emotionDao.insert(emotionMapper.toEmotionEntity(emotion))
    }

    fun getByName(name: String): Emotion = runBlocking{
        emotionMapper.toEmotion(emotionDao.getByName(name))
    }
}