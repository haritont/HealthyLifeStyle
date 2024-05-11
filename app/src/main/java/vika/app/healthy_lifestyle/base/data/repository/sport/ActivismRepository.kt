package vika.app.healthy_lifestyle.base.data.repository.sport

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.sport.ActivismDao
import vika.app.healthy_lifestyle.base.data.database.sport.ActivismDatabase
import vika.app.healthy_lifestyle.base.data.mapper.sport.ActivismMapper
import vika.app.healthy_lifestyle.base.data.mapper.sport.DefaultActivismMapper
import vika.app.healthy_lifestyle.bean.sport.Activism

class ActivismRepository (context: Context){
    private val activismDao: ActivismDao
    private val activismDatabase: ActivismDatabase = ActivismDatabase.getInstance(context)
    private val activismMapper: ActivismMapper

    init {
        activismDao = activismDatabase.activismDao()
        activismMapper = DefaultActivismMapper()
    }

    fun insertActivism(activism: Activism) = runBlocking{
        activismDao.insert(activismMapper.toActivismEntity(activism))
    }

    fun getActivismByDate(date: String): List<Activism> = runBlocking{
        activismMapper.toActivismList(activismDao.getActivismByDate(date))
    }

    fun deleteActivism(name: String, value: Double, date: String) = runBlocking{
        activismDao.delete(name, value, date)
    }
}