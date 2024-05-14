package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.mood.MoodActivity
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.mood.HabitRecord
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight

@Composable
fun Habits(
    habitList: List<Habit>
){
    LazyRow {
        items(habitList) { item ->
            HabitCard(
               item
            )
        }
    }
}

@Composable
fun HabitCard(
    habit: Habit
) {
    val context = LocalContext.current

    var habitRecord = MoodActivity().getHabitRecord(context, habit.id, true)
    var isTrack by remember { mutableStateOf(false)}
    var trackingDays by remember { mutableStateOf("1 день") }
    if (habitRecord != null){
        isTrack = habitRecord.tracking
        trackingDays = DateToday().getDistanceDays(habitRecord.dateStart, DateToday().getToday()).toString()
    }

    Surface(
        color = BlueLight,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(5.dp)
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = trackingDays,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))


            Button(onClick = {
                if (isTrack){
                    habitRecord!!.dateEnd = DateToday().getToday()
                    MoodActivity().insertHabitRecord(context, habitRecord)
                }
                else{
                    MoodActivity().insertHabitRecord(
                        context,
                        HabitRecord(idHabit = habit.id, tracking = true, dateStart = DateToday().getToday())
                    )
                }
                isTrack = !isTrack
            }) {
                Text(
                    if (!isTrack) "Начать отслеживание"
                    else "Прекратить отслеживание"
                )
            }
        }
    }
}

