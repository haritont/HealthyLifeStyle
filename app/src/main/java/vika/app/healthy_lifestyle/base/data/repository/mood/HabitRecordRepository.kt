package vika.app.healthy_lifestyle.base.data.repository.mood

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.mood.HabitRecordDao
import vika.app.healthy_lifestyle.base.data.database.mood.HabitRecordDatabase
import vika.app.healthy_lifestyle.base.data.mapper.mood.DefaultHabitRecordMapper
import vika.app.healthy_lifestyle.base.data.mapper.mood.HabitRecordMapper
import vika.app.healthy_lifestyle.bean.mood.HabitRecord

class HabitRecordRepository (context: Context){
    private val habitRecordDao: HabitRecordDao
    private val habitRecordDatabase: HabitRecordDatabase = HabitRecordDatabase.getInstance(context)
    private val habitRecordMapper: HabitRecordMapper

    init {
        habitRecordDao = habitRecordDatabase.habitRecordDao()
        habitRecordMapper = DefaultHabitRecordMapper()
    }
    fun getRecordByIdHabit(idHabit: Long, tracking: Boolean): HabitRecord? = runBlocking{
        habitRecordDao.getRecordByIdHabit(idHabit, tracking)
            ?.let { habitRecordMapper.toHabitRecord(it) }
    }

    fun insertHabitRecord(habitRecord: HabitRecord) = runBlocking{
        habitRecordDao.insert(habitRecordMapper.toHabitRecordEntity(habitRecord))
    }
}