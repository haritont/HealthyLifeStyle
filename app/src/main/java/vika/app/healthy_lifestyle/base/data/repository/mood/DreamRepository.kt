package vika.app.healthy_lifestyle.base.data.repository.mood

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.mood.DreamDao
import vika.app.healthy_lifestyle.base.data.database.mood.DreamDatabase
import vika.app.healthy_lifestyle.base.data.mapper.mood.DefaultDreamMapper
import vika.app.healthy_lifestyle.base.data.mapper.mood.DreamMapper
import vika.app.healthy_lifestyle.bean.mood.Dream

class DreamRepository (context: Context){

    private val dreamDao: DreamDao
    private val dreamDatabase: DreamDatabase = DreamDatabase.getInstance(context)
    private val dreamMapper: DreamMapper

    init {
        dreamDao = dreamDatabase.dreamDao()
        dreamMapper = DefaultDreamMapper()
    }

    fun saveDream(dream: Dream) = runBlocking {
        dreamDao.insert(dreamMapper.toDreamEntity(dream))
    }

    fun getDream(today: String): Dream = runBlocking{
        dreamMapper.toDream(dreamDao.getDream(today))
    }

    fun getHour(today: String): Int = runBlocking{
        getDream(today).hour
    }

    fun getMinute(today: String): Int = runBlocking{
        getDream(today).minute
    }
}