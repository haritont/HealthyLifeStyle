package vika.app.healthy_lifestyle.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.MainActivity
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.DreamRepository
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.bean.main.Record
import vika.app.healthy_lifestyle.bean.mood.Dream
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.calculations.PersonalTarget
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme

class LoadingActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextWelcome()
                    CircularProgressIndicator()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {

//        val ingredients = DefaultApiServiceRepository().getAllIngredients()
//
//        for (ingredient in ingredients) {
//            IngredientRepository(this).insertIngredient(ingredient)
//        }
//
//        val physicalExercises = DefaultApiServiceRepository().getAllPhysicalExercise()
//
//        for (physicalExercise in physicalExercises) {
//            PhysicalExerciseRepository(this).insertPhysicalExercise(physicalExercise)
//        }
//
//        savePersonalData(
//            PersonalData(
//                genderId = 1,
//                height = 162.0,
//                weight = 45.3,
//                birthDate = "2002-04-18",
//                activityRate = 1.3,
//                name = "Виктория",
//                target = 1
//            )
//        )
//
//        WeightRepository(this).insertWeight(
//            Weight(
//                date = DateToday().getToday(),
//                value = 45.3
//            )
//        )

        if (getTodayDream(DateToday().getToday()) == null) {
            saveDream(
                Dream(
                    date = DateToday().getToday()
                )
            )
        }
        //insertNotifications()

        setRecordTarget()

        startActivity(Intent(this@LoadingActivity, MainActivity::class.java))
        finish()
    }

    private fun getTodayDream(today: String): Dream? {
        return DreamRepository(this).getDream(today)
    }

    private fun saveDream(dream: Dream) {
        DreamRepository(this).saveDream(dream)
    }

//    private fun insertNotifications(){
//        val notificationsRepository = NotificationsRepository(this)
//        notificationsRepository.insertNotifications(
//            Notification(
//                text="Завтрак", hour = 8, minute = 0
//            )
//        )
//
//        notificationsRepository.insertNotifications(
//            Notification(
//                text="Обед", hour = 13, minute = 0
//            )
//        )
//
//        notificationsRepository.insertNotifications(
//            Notification(
//                text="Перекус", hour = 15, minute = 30
//            )
//        )
//
//        notificationsRepository.insertNotifications(
//            Notification(
//                text="Ужин", hour = 18, minute = 0
//            )
//        )
//    }
//
//    private fun getCountNotifications(): Int {
//        return NotificationsRepository(this).getNotificationsRowCount()
//    }

//    private fun getPersonalDataServer(){
//        val token = getToken()
//        if (token != null) {
//            val personalData = DefaultApiServiceRepository().getPersonalData(token)
//            personalData?.token = token
//            savePersonalData(personalData!!)
//        } else {
//            showToast("Ошибка проверки данных")
//            Log.d("LoadingActivity", "Token is null")
//        }
//    }

//    private fun getIngredients() {
//        val ingredients = DefaultApiServiceRepository().getAllIngredients()
//        saveIngredients(ingredients)
//    }

    private fun setRecordTarget() {
        val today = DateToday().getToday()

        if (getTodayRecord(today) == null) {
            val target = PersonalTarget()
            target.count(getPersonalData())
            saveTodayRecordTarget(today, target)
        }
    }


    private fun saveTodayRecordTarget(today: String, target: PersonalTarget)  {
        RecordRepository(this).insertRecord(
            Record(
                date = today, targetKilocalories = target.kilocalories,
                targetProteins = target.proteins, targetFats = target.fats,
                targetCarbohydrates = target.carbohydrates, targetWater = target.water
            )
        )
    }

    private fun getTodayRecord(today: String): Record?  {
        return RecordRepository(this).getRecordByDate(today)
    }

    private fun getPersonalData(): PersonalData {
        return PersonalDataRepository(this).getPersonalData()
    }
//
//    private fun getCountPersonalData():Int  {
//        return getToken()?.let { PersonalDataRepository(this).getRowCountByToken(it) }!!
//    }
//
    private fun savePersonalData(personalData: PersonalData) {
        PersonalDataRepository(this).insertPersonalData(personalData)
    }
//
//
//    private fun saveIngredients(ingredients:List<Ingredient>)  {
//        for (ingredient in ingredients) {
//            IngredientRepository(this).insertIngredient(ingredient)
//        }
//    }
//
//    private fun getCountIngredients(): Int {
//        return IngredientRepository(this).getIngredientRowCount()
//    }

    @Composable
    fun TextWelcome() {
        Text(
            text = stringResource(id = R.string.welcome),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}
