package vika.app.healthy_lifestyle.activity.mood

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.mood.DreamRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.HabitRecordRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.HabitRepository
import vika.app.healthy_lifestyle.bean.mood.Dream
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.mood.HabitRecord
import vika.app.healthy_lifestyle.calculations.CreateAdvice
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.navigation.Navigation

class MoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Navigation()
            }
        }
    }

    fun addDream(hourState: Int, minuteState: Int){
        DreamRepository(this).saveDream(
            Dream(
                date = DateToday().getToday(),
                hour = hourState,
                minute = minuteState
            )
        )
    }

    fun getHour(): Int {
        return DreamRepository(this).getHour(DateToday().getToday())
    }

    fun getMinute(): Int {
        return DreamRepository(this).getMinute(DateToday().getToday())
    }

    fun getAdvice(): String {
        return CreateAdvice().getMoodAdvice()
    }

    fun getHabitList(context: Context): List<Habit> {
        return HabitRepository(context).getAllHabits()
    }

    fun getHabitRecord(context: Context, id: Long, tracking: Boolean): HabitRecord? {
        return HabitRecordRepository(context).getRecordByIdHabit(id, tracking)
    }

    fun insertHabitRecord(context: Context, habitRecord: HabitRecord) {
        HabitRecordRepository(context).insertHabitRecord(habitRecord)
    }

    fun insertHabit(context: Context, name: String, type: String) {
        HabitRepository(context).insertHabit(
            Habit(
                name = name,
                product = type,
                isPositive = false
            )
        )
    }
}