package vika.app.healthy_lifestyle.base.data.mapper.sport

import android.content.Context
import vika.app.healthy_lifestyle.base.data.entity.sport.PhysicalExerciseEntity
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.bean.main.Type
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise

interface PhysicalExerciseMapper {
    fun toPhysicalExercise(physicalExerciseEntity: PhysicalExerciseEntity, context: Context): PhysicalExercise
    fun toPhysicalExerciseEntity(physicalExercise: PhysicalExercise): PhysicalExerciseEntity
    fun toPhysicalExerciseList(physicalExerciseEntities: List<PhysicalExerciseEntity>, context: Context): List<PhysicalExercise>
}

class DefaultPhysicalExerciseMapper: PhysicalExerciseMapper {
    override fun toPhysicalExercise(physicalExerciseEntity: PhysicalExerciseEntity, context: Context): PhysicalExercise {
        var type = TypeRepository(context).getByName(physicalExerciseEntity.type)
        if (type == null){
            type = Type(
                type = physicalExerciseEntity.type,
                isProduct = false
            )
            TypeRepository(context).insert(type)
        }
       return PhysicalExercise(
           physicalExerciseEntity.id,
           physicalExerciseEntity.name,
           physicalExerciseEntity.met,
           type,
           physicalExerciseEntity.training
       )
    }

    override fun toPhysicalExerciseEntity(physicalExercise: PhysicalExercise): PhysicalExerciseEntity {
        return PhysicalExerciseEntity(
            physicalExercise.id,
            physicalExercise.name,
            physicalExercise.met,
            physicalExercise.type.type,
            physicalExercise.training
        )
    }

    override fun toPhysicalExerciseList(physicalExerciseEntities: List<PhysicalExerciseEntity>, context: Context): List<PhysicalExercise> {
        val physicalExercises = mutableListOf<PhysicalExercise>()
        for (physicalExerciseEntity in physicalExerciseEntities){
            physicalExercises.add(toPhysicalExercise(physicalExerciseEntity, context))
        }
        return physicalExercises
    }

}