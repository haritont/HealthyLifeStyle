package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.base.data.repository.main.NotificationsRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.main.Notification

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val notificationsRepository = NotificationsRepository(context)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Настройка уведомлений",
                fontWeight = FontWeight.Bold
            )

            Notification(
                title = "Завтрак",
                hour = notificationsRepository.getNotificationsHour("Завтрак"),
                minute = notificationsRepository.getNotificationsMinute("Завтрак")
            )

            Notification(
                title = "Обед",
                hour = notificationsRepository.getNotificationsHour("Обед"),
                minute = notificationsRepository.getNotificationsMinute("Обед")
            )

            Notification(
                title = "Ужин",
                hour = notificationsRepository.getNotificationsHour("Ужин"),
                minute = notificationsRepository.getNotificationsMinute("Ужин")
            )

            Notification(
                title = "Перекус",
                hour = notificationsRepository.getNotificationsHour("Перекус"),
                minute = notificationsRepository.getNotificationsMinute("Перекус")
            )


            val record = RecordRepository(context).getRecordByDate(DateToday().getToday())!!
            val kilocaloriesState =
                remember { mutableStateOf(record.targetKilocalories.toString()) }
            val proteinsState = remember { mutableStateOf(record.targetProteins.toString()) }
            val fatsState = remember { mutableStateOf(record.targetFats.toString()) }
            val carbohydratesState =
                remember { mutableStateOf(record.targetCarbohydrates.toString()) }

            Text(
                text = "Настроить цели по КБЖУ",
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    TextFieldBlue(
                        value = kilocaloriesState.value,
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.kilocalories),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { newLogin -> kilocaloriesState.value = newLogin },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.kilocalories),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    TextFieldBlue(
                        value = proteinsState.value,
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.proteins),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { newLogin -> proteinsState.value = newLogin },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.proteins),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    TextFieldBlue(
                        value = fatsState.value,
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.fats),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { newLogin -> fatsState.value = newLogin },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.fats),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    )
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    TextFieldBlue(
                        value = carbohydratesState.value,
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.carbohydrates),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { newLogin -> carbohydratesState.value = newLogin },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.carbohydrates),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    )
                }
            }
            ButtonBlue(text = "Сохранить") {
                RecordRepository(context).updateTargetByDate(
                    kilocaloriesState.value.toDouble(),
                    proteinsState.value.toDouble(),
                    fatsState.value.toDouble(),
                    carbohydratesState.value.toDouble(),
                    DateToday().getToday()
                )
                val sharedPreferences: SharedPreferences = context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("kilocalories", kilocaloriesState.value)
                editor.putString("proteins", proteinsState.value)
                editor.putString("fats", fatsState.value)
                editor.putString("carbs", carbohydratesState.value)
                editor.apply()
            }

            val breakfastState = remember { mutableStateOf(30.toString()) }
            val lunchState = remember { mutableStateOf(30.toString()) }
            val dinnerState = remember { mutableStateOf(20.toString()) }
            val snackState = remember { mutableStateOf(20.toString()) }
        Text(
            text = "Настроить цели на приемы пищи в %",
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                TextFieldBlue(
                    value = breakfastState.value,
                    label = {
                        Text(
                            "Завтрак",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> breakfastState.value = newLogin },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Image(
                            painterResource(R.drawable.proteins),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                TextFieldBlue(
                    value = lunchState.value,
                    label = {
                        Text(
                            "Обед",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> lunchState.value = newLogin },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Image(
                            painterResource(R.drawable.dish),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                TextFieldBlue(
                    value = dinnerState.value,
                    label = {
                        Text(
                            "Ужин",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> dinnerState.value = newLogin },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Image(
                            painterResource(R.drawable.dish),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                )
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                TextFieldBlue(
                    value = snackState.value,
                    label = {
                        Text(
                           "Перекус",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onValueChange = { newLogin -> snackState.value = newLogin },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Image(
                            painterResource(R.drawable.ingredient),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                )
            }
        }
        ButtonBlue(text = "Сохранить") {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("breakfast", breakfastState.value)
            editor.putString("lunch", lunchState.value)
            editor.putString("dinner", dinnerState.value)
            editor.putString("snack", snackState.value)
            editor.apply()
        }
    }
    }
}