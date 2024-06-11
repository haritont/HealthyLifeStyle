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
    fun getAllHabits(): List<Habit>? = runBlocking{
        habitDao.getAll()?.let { habitMapper.toHabitList(it) }
    }

    fun insertHabit(habit: Habit) = runBlocking{
        habitDao.insert(habitMapper.toHabitEntity(habit))
    }

    fun getByProduct(product: String): Habit? = runBlocking{
        habitDao.getByProduct(product)?.let { habitMapper.toHabit(it) }
    }

    fun getById(idHabit: Long): Habit = runBlocking{
        habitMapper.toHabit(habitDao.getById(idHabit))
    }

    fun delete(habit: Habit) = runBlocking{
        habitDao.delete(habitMapper.toHabitEntity(habit))
    }
}