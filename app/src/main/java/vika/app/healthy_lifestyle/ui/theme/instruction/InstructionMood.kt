package vika.app.healthy_lifestyle.ui.theme.instruction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Black

@Composable
fun InstructionMood (
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit
) {
    var openDialog by remember { mutableStateOf(isOpen) }

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        onOpenChange(openDialog)
        onDispose { }
    }

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
                onOpenChange(openDialog)
            }
        ) {
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Как ввести сон?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Если вы знаете, сколько часов и минут спали, то введите эти значения и сохраните",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.dream_input),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Если вы знаете, во сколько легли спать и проснулись, то воспользуйтесь кнопкой",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.dream_calc),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(
                            text = "Как пользоваться трекерами пищевых привычек?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Чтобы создать трекер нажмите",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.habit_add),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Введите название привычки и выберете тип продукта, который хотите отслеживать." +
                                    "Если вы хотите добавить этот тип продуктов в свой рацион, то отметьте это.",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.habit_create),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Чтобы включить трекер нажмите",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.habit_play),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Чтобы отключить трекер нажмите",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.habit_stop),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Если трекер включен, то продукты этого типа помечаются соответсвенно",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.habit_list),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(
                            text = "Как эмоциями?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Выберите те эмоции, которые испытывали в течение дня. " +
                                    "Индикатор покажет, какие эмоции вы больше испытывайте: " +
                                    "положительные или отрицательные",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.emo),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}