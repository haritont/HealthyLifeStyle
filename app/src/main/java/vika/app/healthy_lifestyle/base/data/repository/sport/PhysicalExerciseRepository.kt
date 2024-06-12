package vika.app.healthy_lifestyle.base.data.repository.sport

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.sport.PhysicalExerciseDao
import vika.app.healthy_lifestyle.base.data.database.sport.PhysicalExerciseDatabase
import vika.app.healthy_lifestyle.base.data.mapper.sport.DefaultPhysicalExerciseMapper
import vika.app.healthy_lifestyle.base.data.mapper.sport.PhysicalExerciseMapper
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise

class PhysicalExerciseRepository(context: Context) {
    private val physicalExerciseDao: PhysicalExerciseDao
    private val physicalExerciseDatabase: PhysicalExerciseDatabase = PhysicalExerciseDatabase.getInstance(context)
    private val physicalExerciseMapper: PhysicalExerciseMapper
    private val appContext: Context = context

    init {
        physicalExerciseDao = physicalExerciseDatabase.physicalExerciseDao()
        physicalExerciseMapper = DefaultPhysicalExerciseMapper()
    }

    fun insertPhysicalExercise(physicalExercise: PhysicalExercise) = runBlocking{
        physicalExerciseDao.insert(physicalExerciseMapper.toPhysicalExerciseEntity(physicalExercise))
    }

    fun getPhysicalExerciseByName(name: String): PhysicalExercise = runBlocking{
        physicalExerciseMapper.toPhysicalExercise(physicalExerciseDao.getPhysicalExerciseByName(name), appContext)
    }

    fun getAllPhysicalExercises(): List<PhysicalExercise> = runBlocking{
        physicalExerciseMapper.toPhysicalExerciseList(physicalExerciseDao.getPhysicalExercise(), appContext)
    }

    fun getPhysicalExerciseById(id: Long): PhysicalExercise = runBlocking{
        physicalExerciseMapper.toPhysicalExercise(physicalExerciseDao.getPhysicalExerciseById(id), appContext)
    }

    fun isPhysicalExerciseExists(namePhysicalExercise: String): Boolean  = runBlocking{
        physicalExerciseDao.isPhysicalExerciseExists(namePhysicalExercise) != 0
    }

    fun updatePhysicalExerciseFavorite(name: String, favorite: Boolean) = runBlocking{
        physicalExerciseDao.updateFavorite(name, favorite)
    }

    fun updatePhysicalExerciseException(name: String, exception: Boolean) = runBlocking{
        physicalExerciseDao.updateException(name, exception)
    }

    fun getAll(): List<PhysicalExercise>? = runBlocking{
        physicalExerciseDao.getAll()?.let { physicalExerciseMapper.toPhysicalExerciseList(it, appContext) }
    }

    fun getPhysicalExerciseByTarget(value: Double, kilo: Double, weight: Double):  List<PhysicalExercise>? = runBlocking {
        physicalExerciseDao.getPhysicalExerciseByTarget(value, kilo, weight)
            ?.let { physicalExerciseMapper.toPhysicalExerciseList(it, appContext) }
    }
}