package vika.app.healthy_lifestyle.base.data.repository.sport

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.sport.TrainingDao
import vika.app.healthy_lifestyle.base.data.database.sport.TrainingDatabase
import vika.app.healthy_lifestyle.base.data.mapper.sport.DefaultTrainingMapper
import vika.app.healthy_lifestyle.base.data.mapper.sport.TrainingMapper
import vika.app.healthy_lifestyle.bean.sport.Training

class TrainingRepository (context: Context){
    private val trainingDao: TrainingDao
    private val trainingDatabase: TrainingDatabase = TrainingDatabase.getInstance(context)
    private val trainingMapper: TrainingMapper

    init {
        trainingDao = trainingDatabase.trainingDao()
        trainingMapper = DefaultTrainingMapper()
    }

    fun insertTraining(recipe: Training) = runBlocking{
        trainingDao.insert(trainingMapper.toTrainingEntity(recipe))
    }

    fun getTraining(idName: Long): List<Training> = runBlocking{
        trainingMapper.toTrainingList(trainingDao.getTraining(idName))
    }

    fun deleteAllTraining(idPhysicalExercise: Long)  = runBlocking{
        trainingDao.deleteAll(idPhysicalExercise)
    }
}