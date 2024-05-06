package vika.app.healthy_lifestyle.base.data.mapper.sport

import vika.app.healthy_lifestyle.base.data.entity.sport.ActivismEntity
import vika.app.healthy_lifestyle.bean.sport.Activism

interface ActivismMapper {
    fun toActivismEntity(activism: Activism): ActivismEntity
    fun toActivism(activismEntity: ActivismEntity): Activism
    fun toActivismList(activismEntities: List<ActivismEntity>): List<Activism>
}

class DefaultActivismMapper: ActivismMapper {
    override fun toActivismEntity(activism: Activism): ActivismEntity {
        return ActivismEntity(
            activism.id,
            activism.name,
            activism.value,
            activism.date
        )
    }

    override fun toActivism(activismEntity: ActivismEntity): Activism {
        return Activism(
            id = activismEntity.id,
            name = activismEntity.name,
            value = activismEntity.value,
            date = activismEntity.date
        )
    }

    override fun toActivismList(activismEntities: List<ActivismEntity>): List<Activism> {
        val activisms = mutableListOf<Activism>()
        for (activismEntity in activismEntities){
            activisms.add(toActivism(activismEntity))
        }
        return activisms
    }
}