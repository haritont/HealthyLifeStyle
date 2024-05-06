package vika.app.healthy_lifestyle.base.data.mapper.sport

import vika.app.healthy_lifestyle.base.data.entity.sport.TrainingEntity
import vika.app.healthy_lifestyle.bean.sport.Training

interface TrainingMapper {
    fun toTrainingEntity(training: Training): TrainingEntity
    fun toTraining(trainingEntity: TrainingEntity): Training
    fun toTrainingList(trainingEntities: List<TrainingEntity>): List<Training>
}

class DefaultTrainingMapper: TrainingMapper {
    override fun toTrainingEntity(training: Training): TrainingEntity {
        return TrainingEntity(
            training.id,
            training.idName,
            training.idPhysicalExercise,
            training.valuePhysicalExercise
        )
    }

    override fun toTraining(trainingEntity: TrainingEntity): Training {
        return Training(
            trainingEntity.id,
            trainingEntity.idName,
            trainingEntity.idPhysicalExercise,
            trainingEntity.valuePhysicalExercise
        )
    }

    override fun toTrainingList(trainingEntities: List<TrainingEntity>): List<Training> {
        val trainings = mutableListOf<Training>()
        for (trainingEntity in trainingEntities){
            trainings.add(toTraining(trainingEntity))
        }
        return trainings
    }
}