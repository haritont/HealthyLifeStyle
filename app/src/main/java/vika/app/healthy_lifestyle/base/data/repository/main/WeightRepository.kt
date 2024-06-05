package vika.app.healthy_lifestyle.base.data.repository.main

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.main.WeightDao
import vika.app.healthy_lifestyle.base.data.database.main.WeightDatabase
import vika.app.healthy_lifestyle.base.data.mapper.main.DefaultWeightMapper
import vika.app.healthy_lifestyle.base.data.mapper.main.WeightMapper
import vika.app.healthy_lifestyle.bean.main.Weight
import vika.app.healthy_lifestyle.calculations.DateToday

class WeightRepository (context: Context){
    private val weightDao: WeightDao
    private val weightDatabase: WeightDatabase = WeightDatabase.getInstance(context)
    private val weightMapper: WeightMapper

    init {
        weightDao = weightDatabase.weightDao()
        weightMapper = DefaultWeightMapper()
    }

    fun insertWeight(weight: Weight) = runBlocking{
        weightDao.insert(weightMapper.toWeightEntity(weight))
    }

    fun getAllWeights(): List<Weight> = runBlocking{
        weightMapper.toWeightList(weightDao.getAll())
    }

    fun getLastEntry(): Weight? = runBlocking {
        weightDao.getLastEntry()?.let { weightMapper.toWeight(it) }
    }

    fun getToday(): Weight? = runBlocking{
        weightDao.getByDate(DateToday().getToday())?.let{ weightMapper.toWeight(it) }
    }

    fun getByDate(date: String): Weight? = runBlocking {
        weightDao.getByDate(DateToday().getToday())?.let{ weightMapper.toWeight(it) }
    }
}