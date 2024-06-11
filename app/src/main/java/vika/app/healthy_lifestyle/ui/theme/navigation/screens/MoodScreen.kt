package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import vika.app.healthy_lifestyle.activity.mood.MoodActivity
import vika.app.healthy_lifestyle.base.data.repository.mood.EmotionRepository
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.mood.Dream
import vika.app.healthy_lifestyle.ui.theme.mood.Emotions
import vika.app.healthy_lifestyle.ui.theme.mood.Habits


@Composable
fun MoodScreen (){
    val context = LocalContext.current
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Dream(MoodActivity().getHour(), MoodActivity().getMinute())

        Advice(value = MoodActivity().getAdvice())

        var habitList by remember { mutableStateOf(MoodActivity().getHabitList(context)) }

        Habits(
            habitList,
            { habit ->
                habitList = habitList!!.toMutableList().apply { remove(habit) }
                MoodActivity().deleteHabit(habit, context)
            },{ habit ->
                habitList = habitList!!.toMutableList().apply { add(habit) }
                MoodActivity().insertHabit(
                    context,
                    habit
                )
            }
        )

        var emotionList by remember { mutableStateOf( EmotionRepository(context).getAllEmotions())}
        Emotions(
            emotionList
        ) { emotion ->
            emotionList = emotionList!!.toMutableList().apply { add(emotion) }
        }
    }
}