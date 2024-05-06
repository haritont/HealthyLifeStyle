package vika.app.healthy_lifestyle.base.data.mapper.sport

import vika.app.healthy_lifestyle.base.data.entity.sport.PhysicalExerciseEntity
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise

interface PhysicalExerciseMapper {
    fun toPhysicalExercise(physicalExerciseEntity: PhysicalExerciseEntity): PhysicalExercise
    fun toPhysicalExerciseEntity(physicalExercise: PhysicalExercise): PhysicalExerciseEntity
    fun toPhysicalExerciseList(physicalExerciseEntities: List<PhysicalExerciseEntity>): List<PhysicalExercise>
}

class DefaultPhysicalExerciseMapper: PhysicalExerciseMapper {
    override fun toPhysicalExercise(physicalExerciseEntity: PhysicalExerciseEntity): PhysicalExercise {
       return PhysicalExercise(
           physicalExerciseEntity.id,
           physicalExerciseEntity.name,
           physicalExerciseEntity.met,
           physicalExerciseEntity.type,
           physicalExerciseEntity.training
       )
    }

    override fun toPhysicalExerciseEntity(physicalExercise: PhysicalExercise): PhysicalExerciseEntity {
        return PhysicalExerciseEntity(
            physicalExercise.id,
            physicalExercise.name,
            physicalExercise.met,
            physicalExercise.type,
            physicalExercise.training
        )
    }

    override fun toPhysicalExerciseList(physicalExerciseEntities: List<PhysicalExerciseEntity>): List<PhysicalExercise> {
        val physicalExercises = mutableListOf<PhysicalExercise>()
        for (physicalExerciseEntity in physicalExerciseEntities){
            physicalExercises.add(toPhysicalExercise(physicalExerciseEntity))
        }
        return physicalExercises
    }

}