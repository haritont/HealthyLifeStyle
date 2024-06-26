package vika.app.healthy_lifestyle.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.ProfileActivity
import vika.app.healthy_lifestyle.base.data.repository.main.WeightRepository
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.bean.main.Weight
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.server.api.DefaultApiServiceRepository
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.DatePickerWithDialog
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import kotlin.math.pow

class RegistrationActivity: ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                val context = LocalContext.current

                val nameState = remember { mutableStateOf("") }
                val heightState = remember { mutableStateOf("") }
                val weightState = remember { mutableStateOf("") }
                var birthDate = DateToday().getToday()
                val targetState = remember { mutableStateOf(0) }
                val activityRateState = remember { mutableStateOf(1.0) }
                val genderState = remember { mutableStateOf(0) }
                var loginState by remember { mutableStateOf("") }
                var passwordState by remember { mutableStateOf("") }

                LazyColumn(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    item {
                        Text(
                            text = "Заполните свои данные",
                            fontWeight = FontWeight.Bold
                        )

                        TextFieldLogin(loginState) { newLogin ->
                            loginState = newLogin
                        }

                        TextFieldPassword(passwordState) { newPassword ->
                            passwordState = newPassword
                        }

                        TextFieldBlue(
                            value = nameState.value,
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
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = context.getString(R.string.birth_date))
                        DatePickerWithDialog(
                            currentDate = birthDate,
                            getCurrentTime =
                            { currentTime ->
                                birthDate = currentTime
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                TextFieldBlue(
                                    value = heightState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.height),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { heightName ->
                                        heightState.value = heightName
                                    },
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
                                    value = weightState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.weight),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { weightName ->
                                        weightState.value = weightName
                                    },
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

                        if (weightState.value.isNotEmpty() && heightState.value.isNotEmpty()) {
                            val imt =
                                weightState.value.toDouble() / (heightState.value.toDouble() / 100.0).pow(
                                    2
                                )
                            val explanation = when {
                                imt < 18.5 -> "Недостаток веса"
                                imt < 25.0 -> "Нормальный вес"
                                imt < 30.0 -> "Предожирение"
                                imt < 35.0 -> "1 степень ожирения"
                                imt < 40.0 -> "2 степень ожирения"
                                else -> "3 степень ожирения"
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Ваш ИМТ = %.2f (%s)".format(imt, explanation),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        val targets = listOf("Набрать вес", "Поддержать вес", "Снизить вес")
                        val selectedTarget = remember { mutableStateOf(targets[targetState.value]) }

                        Column {
                            Text(text = "Цель")
                            Column(Modifier.selectableGroup()) {
                                targets.forEach { text ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (text == selectedTarget.value),
                                            onClick = {
                                                selectedTarget.value = text
                                                targetState.value = targets.indexOf(text)
                                            }
                                        )
                                        Text(text = text)
                                    }
                                }
                            }
                        }

                        val genders = listOf("Женский", "Мужской")
                        val (selectedGender, onGenderSelected) = remember { mutableStateOf(genders[genderState.value]) }
                        Column {
                            Text(text = context.getString(R.string.gender))
                            Row(Modifier.selectableGroup()) {
                                genders.forEach { text ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    )
                                    {
                                        RadioButton(
                                            selected = (text == selectedGender),
                                            onClick = {
                                                onGenderSelected(text)
                                                genderState.value = genders.indexOf(text)
                                            }
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

                        val selectedActivity =
                            remember { mutableStateOf(activities[activityRateState.value]) }

                        Column {
                            Text(text = "Активность")
                            Column(Modifier.selectableGroup()) {
                                activities.forEach { (key, value) ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (value == selectedActivity.value),
                                            onClick = {
                                                selectedActivity.value = value
                                                activityRateState.value = key
                                            }
                                        )
                                        Text(text = value)
                                    }
                                }
                            }
                        }

                        ButtonBlue(text = "Сохранить данные") {
                            if (heightState.value.toDoubleOrNull() != null && weightState.value.toDoubleOrNull() != null) {
                                val personalData = PersonalData(
                                    genderId = genderState.value + 1,
                                    height = heightState.value.toDouble(),
                                    weight = weightState.value.toDouble(),
                                    birthDate = birthDate,
                                    activityRate = activityRateState.value,
                                    name = nameState.value,
                                    target = targetState.value
                                )
                                ProfileActivity().insertPersonalData(
                                    context,
                                    personalData
                                )
                                WeightRepository(context).insertWeight(
                                    Weight(
                                        date = DateToday().getToday(),
                                        value = weightState.value.toDouble()
                                    )
                                )
                                val token = DefaultApiServiceRepository().registration(
                                    loginState,
                                    passwordState,
                                    personalData
                                )
                                when (token) {
                                    null -> {
                                        showToast("Ваши данные не сохранены! Работа продолжится в офлайн режиме")
                                        setToken(context, "local_token")
                                        startActivity(
                                            Intent(
                                                this@RegistrationActivity,
                                                LoadingActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                    "error" -> {
                                        showToast("Ошибка регистрации. Такой логин уже существует")
                                    }
                                    else -> {
                                        showToast("Ваши данные успешно сохранены!")
                                        setToken(context, token)
                                        startActivity(
                                            Intent(
                                                this@RegistrationActivity,
                                                LoadingActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private fun setToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}