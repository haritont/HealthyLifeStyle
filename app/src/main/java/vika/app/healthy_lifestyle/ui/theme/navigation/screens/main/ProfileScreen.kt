package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.ProfileActivity
import vika.app.healthy_lifestyle.ui.theme.general.DatePickerWithDialog
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    val personalData = ProfileActivity().getPersonalData(context)
    val nameState = remember { mutableStateOf("") }
    val heightState = remember { mutableStateOf("") }
    val weightState = remember { mutableStateOf("") }
    var birthDate = personalData.birthDate
    val targetState = remember { mutableStateOf(personalData.target) }
    val activityRateState = remember { mutableStateOf(personalData.activityRate) }
    val genderState = remember { mutableStateOf(personalData.genderId) }

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        item {
            TextFieldBlue(
                value = personalData.name,
                label = {
                    Text(
                        LocalContext.current.getString(R.string.name),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                onValueChange = { newName -> nameState.value = newName },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                leadingIcon = {
                    Image(
                        painterResource(R.drawable.login),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    TextFieldBlue(
                        value = personalData.height.toString(),
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.height),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { heightName -> heightState.value = heightName },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.height),
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
                        value = personalData.weight.toString(),
                        label = {
                            Text(
                                LocalContext.current.getString(R.string.weight),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onValueChange = { weightName -> weightState.value = weightName },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.weight),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    )
                }
            }

            Text(text = context.getString(R.string.birth_date))
            DatePickerWithDialog(
                currentDate = birthDate,
                getCurrentTime =
                { currentTime ->
                    birthDate = currentTime
                }
            )

            val targets = listOf("Набрать вес", "Поддержать вес", "Снизить вес")
            val (selectedTarget, onTargetSelected) = remember { mutableStateOf(targets[targetState.value - 1]) }
            Column {
                Text(text = context.getString(R.string.target))
                Column(Modifier.selectableGroup()) {
                    targets.forEach { text ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            RadioButton(
                                selected = (text == selectedTarget),
                                onClick = { onTargetSelected(text) }
                            )
                            Text(text = text)
                        }
                    }
                }
            }

            val genders = listOf("Женский", "Мужской")
            val (selectedGender, onGenderSelected) = remember { mutableStateOf(genders[genderState.value - 1]) }
            Column {
                Text(text = context.getString(R.string.gender))
                Column(Modifier.selectableGroup()) {
                    genders.forEach { text ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            RadioButton(
                                selected = (text == selectedGender),
                                onClick = { onGenderSelected(text) }
                            )
                            Text(text = text)
                        }
                    }
                }
            }

            val activities = mapOf(
                1.0 to "Низкая активность",
                1.3 to "Средняя активность",
                1.5 to "Высокая активность"
            )
            val (selectedActivity, onActivitySelected) = remember { mutableStateOf(activities.getValue(activityRateState.value)) }
            Column {
                Text(text = context.getString(R.string.activity))
                Column(Modifier.selectableGroup()) {
                    activities.values.forEach { text ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            RadioButton(
                                selected = (text == selectedActivity),
                                onClick = { onActivitySelected(text) }
                            )
                            Text(text = text)
                        }
                    }
                }
            }
        }
    }
}