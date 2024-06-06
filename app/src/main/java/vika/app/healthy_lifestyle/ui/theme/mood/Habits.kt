package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.mood.MoodActivity
import vika.app.healthy_lifestyle.bean.mood.Habit
import vika.app.healthy_lifestyle.bean.mood.HabitRecord
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue


@Composable
fun Habits(
    habitList: List<Habit>?
) {
    var openDialogAddHabit by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Пищевые привычки",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            color = Black
        )
        ImageButton(
            icon = R.drawable.add
        ) {
            openDialogAddHabit = true
        }
    }

    if (habitList != null) {
        LazyRow {
            items(habitList) { item ->
                HabitCard(
                    item
                )
            }
        }
    }

    if (openDialogAddHabit) {
        val nameState = remember { mutableStateOf("") }
        val options = listOf(
            "Без типа",
            "Напиток",
            "Фрукт",
            "Сладкое",
            "Приправа",
            "Алкоголь",
            "Дичь",
            "Рыба",
            "Орех",
            "Ягода",
            "Вода",
            "Овощ",
            "Мучное",
            "Зелень",
            "Соус",
            "Уксус",
            "Мясо",
            "Субпродукт",
            "Сыр",
            "Боб",
            "Крупа",
            "Гриб",
            "Молочное",
            "Масло",
            "Яйцо"
        )
        val typeState = remember { mutableStateOf(options[0]) }
        var checked by remember { mutableStateOf(false) }

        Dialog(
            onDismissRequest = {
                openDialogAddHabit = !openDialogAddHabit
            }
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                )
                {
                    TextFieldBlue(
                        value = nameState.value,
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.input_name),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { newLogin -> nameState.value = newLogin },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.mood),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    )

                    Text(
                        text = "Какой тип продукта отслеживать?",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Dropdown(
                        options,
                        options[0]
                    ) {
                        currentOption ->
                        typeState.value = currentOption
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { checked = it }
                        )
                        Text(
                            text = "Хочу добавить это продукт в рацион",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            openDialogAddHabit = false
                            MoodActivity().insertHabit(
                                context,
                                nameState.value,
                                typeState.value,
                                checked
                            )
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Ок")
                    }
                    TextButton(
                        onClick = {
                            openDialogAddHabit = false
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Отмена")
                    }
                }
            }
        }
    }
}

@Composable
fun HabitCard(
    habit: Habit
) {
    val context = LocalContext.current

    val habitRecord = MoodActivity().getHabitRecord(context, habit.id, true)
    var isTrack by remember { mutableStateOf(false) }
    var trackingDays by remember { mutableStateOf("Количество дней: 1") }
    if (habitRecord != null) {
        isTrack = habitRecord.tracking
        trackingDays = "Количество дней: ".plus(
            DateToday().getDistanceDays(habitRecord.dateStart, DateToday().getToday()).toString()
        )
    }

    Surface(
        color = BlueUltraLight,
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
                text = if (isTrack) trackingDays else "",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painterResource(
                    if (!isTrack) R.drawable.start
                    else R.drawable.stop
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        if (isTrack) {
                            habitRecord!!.dateEnd = DateToday().getToday()
                            MoodActivity().insertHabitRecord(context, habitRecord)
                        } else {
                            MoodActivity().insertHabitRecord(
                                context,
                                HabitRecord(
                                    idHabit = habit.id,
                                    tracking = true,
                                    dateStart = DateToday().getToday()
                                )
                            )
                        }
                        isTrack = !isTrack
                    }
            )
        }
    }
}

