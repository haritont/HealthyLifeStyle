package vika.app.healthy_lifestyle.base.data.repository.main

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.main.RecordDao
import vika.app.healthy_lifestyle.base.data.database.main.RecordDatabase
import vika.app.healthy_lifestyle.base.data.mapper.main.DefaultRecordMapper
import vika.app.healthy_lifestyle.base.data.mapper.main.RecordMapper
import vika.app.healthy_lifestyle.bean.main.Record

class RecordRepository(context: Context) {
    private val recordDao: RecordDao
    private val recordDatabase: RecordDatabase
    private val recordMapper: RecordMapper

    init {
        recordDatabase = RecordDatabase.getInstance(context)
        recordDao = recordDatabase.recordDao()
        recordMapper = DefaultRecordMapper()
    }

    fun insertRecord(record: Record) = runBlocking{
        recordDao.insert(recordMapper.toRecordEntity(record))
    }

    fun getRecordByDate(date: String): Record?  = runBlocking{
        recordDao.getAllByDate(date)?.let { recordMapper.toRecord(it) }
    }

    fun updateProgressFoodRecord(date: String, kilocalories: Double, proteins: Double,
                                         fats: Double, carbohydrates: Double)  = runBlocking{
        recordDao.updateProgressFoodRecord(date, kilocalories, proteins, fats, carbohydrates)
    }

    fun updateProgressFoodRecord(date: String, kilocalories: Double)  = runBlocking{
        recordDao.updateProgressFoodRecord(date, kilocalories)
    }

    fun updateBurnedKilocalories(date: String, kilocalories: Double)  = runBlocking{
        recordDao.updateBurnedKilocalories(date, kilocalories)
    }

    fun updateProgressStepsRecord(date: String, steps: Int)  = runBlocking{
        recordDao.updateProgressStepsRecord(date, steps)
    }

    fun updateProgressWaterRecord(date: String, water: Int)  = runBlocking{
        recordDao.updateProgressWaterRecord(date, water)
    }

    fun progressKilocalories(today: String): Double {
        return getRecordByDate(today)!!.progressKilocalories
    }

    fun targetKilocalories(today: String): Double {
        return getRecordByDate(today)!!.targetKilocalories
    }

    fun progressProteins(today: String): Double {
        return getRecordByDate(today)!!.progressProteins
    }

    fun targetProteins(today: String): Double {
        return getRecordByDate(today)!!.targetProteins
    }

    fun progressFats(today: String): Double {
        return getRecordByDate(today)!!.progressFats
    }

    fun targetFats(today: String): Double {
        return getRecordByDate(today)!!.targetFats
    }

    fun progressCarbohydrates(today: String): Double {
        return getRecordByDate(today)!!.progressCarbohydrates
    }

    fun targetCarbohydrates(today: String): Double {
        return getRecordByDate(today)!!.targetCarbohydrates
    }

    fun progressWater(today: String): Int {
        return getRecordByDate(today)!!.progressWater
    }

    fun burnedKilocalories(today: String): Double {
        return getRecordByDate(today)!!.burnedKilocalories
    }

    fun progressSteps(today: String): Int {
        return getRecordByDate(today)!!.progressSteps
    }
}