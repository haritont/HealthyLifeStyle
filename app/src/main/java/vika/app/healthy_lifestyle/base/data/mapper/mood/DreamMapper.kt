package vika.app.healthy_lifestyle.base.data.mapper.mood

import vika.app.healthy_lifestyle.base.data.entity.mood.DreamEntity
import vika.app.healthy_lifestyle.bean.mood.Dream

interface DreamMapper {
    fun toDreamEntity(dream: Dream): DreamEntity
    fun toDream(dreamEntity: DreamEntity): Dream
}
class DefaultDreamMapper:DreamMapper{
    override fun toDreamEntity(dream: Dream): DreamEntity {
        return DreamEntity(
            dream.id,
            dream.date,
            dream.hour,
            dream.minute
        )
    }

    override fun toDream(dreamEntity: DreamEntity): Dream {
        return Dream(
            dreamEntity.id,
            dreamEntity.date,
            dreamEntity.hour,
            dreamEntity.minute
        )
    }

}