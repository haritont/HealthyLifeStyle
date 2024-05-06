package vika.app.healthy_lifestyle.base.data.repository.main

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.main.PersonalDataDao
import vika.app.healthy_lifestyle.base.data.database.main.PersonalDataDatabase
import vika.app.healthy_lifestyle.base.data.mapper.main.DefaultPersonalDataMapper
import vika.app.healthy_lifestyle.base.data.mapper.main.PersonalDataMapper
import vika.app.healthy_lifestyle.bean.main.PersonalData

class PersonalDataRepository(context: Context) {
    private val personalDataDao: PersonalDataDao
    private val personalDataDatabase: PersonalDataDatabase =
        PersonalDataDatabase.getInstance(context)
    private val personalDataMapper: PersonalDataMapper

    init {
        personalDataDao = personalDataDatabase.personalDataDao()
        personalDataMapper = DefaultPersonalDataMapper()
    }

    fun insertPersonalData(personalDataEntity: PersonalData) = runBlocking {
        personalDataDao.insert(personalDataMapper.toPersonalDataEntity(personalDataEntity))
    }

    fun getPersonalData(): PersonalData = runBlocking {
        personalDataMapper.toPersonalData(personalDataDao.getLastEntry()!!)
    }

    fun getName(): String = runBlocking {
        getPersonalData().name
    }

    fun getWeight(): Double = runBlocking {
        getPersonalData().weight
    }

    fun getGender(): Int = runBlocking {
        getPersonalData().genderId
    }

    fun getRowCountByToken(): Int = runBlocking {
        if (personalDataDao.getLastEntry() != null) 1
        else 0
    }

    private fun updateWeight(personalData: PersonalData) = runBlocking {
        personalDataDao.updateWeight(personalDataMapper.toPersonalDataEntity(personalData))
    }

    fun updateWeight(weight: Double) = runBlocking {
        val data = getPersonalData()
        updateWeight(
            PersonalData(
                genderId = data.genderId,
                height = data.height,
                weight = weight,
                birthDate = data.birthDate,
                activityRate = data.activityRate,
                name = data.name,
                target = data.target
            )
        )
    }
}