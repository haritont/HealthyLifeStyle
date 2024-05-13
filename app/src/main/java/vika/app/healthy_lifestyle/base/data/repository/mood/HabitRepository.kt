package vika.app.healthy_lifestyle.base.data.repository.mood

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.mood.HabitDao
import vika.app.healthy_lifestyle.base.data.database.mood.HabitDatabase
import vika.app.healthy_lifestyle.base.data.mapper.mood.DefaultHabitMapper
import vika.app.healthy_lifestyle.base.data.mapper.mood.HabitMapper
import vika.app.healthy_lifestyle.bean.mood.Habit

class HabitRepository (context: Context){
    private val habitDao: HabitDao
    private val habitDatabase: HabitDatabase = HabitDatabase.getInstance(context)
    private val habitMapper: HabitMapper

    init {
        habitDao = habitDatabase.habitDao()
        habitMapper = DefaultHabitMapper()
    }
    fun getAllHabits(): List<Habit> = runBlocking{
        habitMapper.toHabitList(habitDao.getAll())
    }

    fun insertHabit(habit: Habit) = runBlocking{
        habitDao.insert(habitMapper.toHabitEntity(habit))
    }
}