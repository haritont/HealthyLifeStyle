package vika.app.healthy_lifestyle.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.base.data.repository.food.IngredientRepository
import vika.app.healthy_lifestyle.base.data.repository.main.NotificationsRepository
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.base.data.repository.main.WeightRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.DreamRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.EmotionRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.HabitRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.PhysicalExerciseRepository
import vika.app.healthy_lifestyle.bean.main.Notification
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.bean.main.Record
import vika.app.healthy_lifestyle.bean.main.Weight
import vika.app.healthy_lifestyle.bean.mood.Dream
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.calculations.PersonalTarget
import vika.app.healthy_lifestyle.notification.createNotificationChannel
import vika.app.healthy_lifestyle.notification.scheduleNotification
import vika.app.healthy_lifestyle.server.api.DefaultApiServiceRepository
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme

class LoadingActivity : ComponentActivity() {
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                var isLoading by remember { mutableStateOf(true) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextWelcome()
                    if (isLoading) {
                        Text(text = "Загрузка данных...", modifier = Modifier.padding(top = 16.dp))
                    } else {
                        errorMessage?.let {
                            Text(text = it, modifier = Modifier.padding(top = 16.dp))
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    loadData(
                        onError = { message ->
                            errorMessage = message
                            isLoading = false
                            startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                            finish()
                        },
                        onSuccess = {
                            startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }

    private fun loadData(onError: (String) -> Unit, onSuccess: () -> Unit) {
        scope.launch {
            withContext(Dispatchers.IO) {
                if (WeightRepository(this@LoadingActivity).getToday() == null) {
                    WeightRepository(this@LoadingActivity).insertWeight(
                        Weight(
                            date = DateToday().getToday(),
                            value = getPersonalData().weight
                        )
                    )
                }

                if (getTodayDream(DateToday().getToday()) == null) {
                    saveDream(Dream(date = DateToday().getToday()))
                }

                insertNotifications()
                setRecordTarget()

                try {
                    val service = DefaultApiServiceRepository()

                    if (IngredientRepository(this@LoadingActivity).getAllProduct() == null) {
                        val ingredients = service.getAllIngredients()
                        for (ingredient in ingredients) {
                            IngredientRepository(this@LoadingActivity).insertIngredient(ingredient)
                            TypeRepository(this@LoadingActivity).insert(ingredient.type)
                        }
                    }

                    if (PhysicalExerciseRepository(this@LoadingActivity).getAll() == null) {
                        val physicalExercises = service.getAllPhysicalExercise()
                        for (physicalExercise in physicalExercises) {
                            PhysicalExerciseRepository(this@LoadingActivity).insertPhysicalExercise(physicalExercise)
                            TypeRepository(this@LoadingActivity).insert(physicalExercise.type)
                        }
                    }

                    if (HabitRepository(this@LoadingActivity).getAllHabits() == null) {
                        val habits = service.getAllHabits()
                        for (habit in habits) {
                            HabitRepository(this@LoadingActivity).insertHabit(habit)
                        }
                    }

                    if (EmotionRepository(this@LoadingActivity).getAllEmotions() == null) {
                        val emotions = service.getAllEmotions()
                        for (emotion in emotions) {
                            EmotionRepository(this@LoadingActivity).insertEmotion(emotion)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        onError("Сервер недоступен")
                    }
                    return@withContext
                }

                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    private fun getTodayDream(today: String): Dream? {
        return DreamRepository(this).getDream(today)
    }

    private fun saveDream(dream: Dream) {
        DreamRepository(this).saveDream(dream)
    }

    private fun insertNotifications() {
        val notificationsRepository = NotificationsRepository(this)

        if (notificationsRepository.getAllNotifications()!!.isEmpty()) {
            notificationsRepository.insertNotifications(
                Notification(text = "Завтрак", hour = 8, minute = 0)
            )
            notificationsRepository.insertNotifications(
                Notification(text = "Обед", hour = 13, minute = 0)
            )
            notificationsRepository.insertNotifications(
                Notification(text = "Перекус", hour = 15, minute = 30)
            )
            notificationsRepository.insertNotifications(
                Notification(text = "Ужин", hour = 18, minute = 0)
            )

            val notification = notificationsRepository.getAllNotifications()
            for (not in notification!!) {
                scheduleNotification(applicationContext, "Пора заполнить ".plus(not.text.lowercase()), not.hour, not.minute)
            }

            createNotificationChannel(this)
        }
    }

    private fun setRecordTarget() {
        val today = DateToday().getToday()

        if (getTodayRecord(today) == null) {
            val target = PersonalTarget()
            target.count(getPersonalData(), this@LoadingActivity)
            saveTodayRecordTarget(today, target)
        }
    }

    private fun saveTodayRecordTarget(today: String, target: PersonalTarget) {
        RecordRepository(this).insertRecord(
            Record(
                date = today, targetKilocalories = target.kilocalories,
                targetProteins = target.proteins, targetFats = target.fats,
                targetCarbohydrates = target.carbohydrates, targetWater = target.water
            )
        )
    }

    private fun getTodayRecord(today: String): Record? {
        return RecordRepository(this).getRecordByDate(today)
    }

    private fun getPersonalData(): PersonalData {
        return PersonalDataRepository(this).getPersonalData()!!
    }

    @Composable
    fun TextWelcome() {
        Text(
            text = "Добро пожаловать!",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}
