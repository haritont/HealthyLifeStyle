package vika.app.healthy_lifestyle.base.data.mapper.mood

import vika.app.healthy_lifestyle.base.data.entity.mood.HabitEntity
import vika.app.healthy_lifestyle.bean.mood.Habit

interface HabitMapper {
    fun toHabit(habitEntity: HabitEntity): Habit
    fun toHabitList(habitEntityList: List<HabitEntity>): List<Habit>
}
class DefaultHabitMapper: HabitMapper{
    override fun toHabit(habitEntity: HabitEntity): Habit {
        return Habit(
            habitEntity.id,
            habitEntity.name,
            habitEntity.image.toString()
        )
    }

    override fun toHabitList(habitEntityList: List<HabitEntity>): List<Habit> {
        val habits = mutableListOf<Habit>()
        for (habitEntity in habitEntityList){
            habits.add(toHabit(habitEntity))
        }
        return habits
    }
}