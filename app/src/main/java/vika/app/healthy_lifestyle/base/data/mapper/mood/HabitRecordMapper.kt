package vika.app.healthy_lifestyle.base.data.mapper.mood

import vika.app.healthy_lifestyle.base.data.entity.mood.HabitRecordEntity
import vika.app.healthy_lifestyle.bean.mood.HabitRecord

interface HabitRecordMapper {
    fun toHabitRecord(habitRecordEntity: HabitRecordEntity): HabitRecord
    fun toHabitRecordEntity(habitRecord: HabitRecord): HabitRecordEntity
}
class DefaultHabitRecordMapper: HabitRecordMapper{
    override fun toHabitRecord(habitRecordEntity: HabitRecordEntity): HabitRecord {
        return HabitRecord(
            id = habitRecordEntity.id,
            idHabit = habitRecordEntity.idHabit,
            tracking = habitRecordEntity.tracking,
            dateStart = habitRecordEntity.dateStart,
            dateEnd = habitRecordEntity.dateEnd
        )
    }

    override fun toHabitRecordEntity(habitRecord: HabitRecord): HabitRecordEntity {
        return HabitRecordEntity(
            id = habitRecord.id,
            idHabit = habitRecord.idHabit,
            tracking = habitRecord.tracking,
            dateStart = habitRecord.dateStart,
            dateEnd = habitRecord.dateEnd
        )
    }
}