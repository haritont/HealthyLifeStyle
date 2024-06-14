package vika.app.healthy_lifestyle.activity.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.bean.main.Record
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.calculation.PersonalTarget
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme

class ProfileActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme{
            }
        }
    }

    fun getPersonalData(context: Context): PersonalData {
       return PersonalDataRepository(context).getPersonalData()!!
    }

    fun insertPersonalData(context: Context, personalData: PersonalData) {
        PersonalDataRepository(context).insertPersonalData(personalData)
        val newTarget = PersonalTarget()
        newTarget.count(personalData, context)
        val record = RecordRepository(context).getRecordByDate(DateToday().getToday())
        if (record != null) {
            RecordRepository(this).insertRecord(
                Record(
                    id = record.id,
                    date = DateToday().getToday(),
                    targetKilocalories = newTarget.kilocalories,
                    targetProteins = newTarget.proteins,
                    targetFats = newTarget.fats,
                    targetCarbohydrates = newTarget.carbohydrates,
                    targetWater = newTarget.water,
                    progressKilocalories = record.progressKilocalories,
                    progressProteins = record.progressProteins,
                    progressFats = record.progressFats,
                    progressCarbohydrates = record.progressCarbohydrates,
                    progressWater = record.progressWater,
                    progressSteps = record.progressSteps,
                    burnedKilocalories = record.burnedKilocalories
                )
            )
        }
    }
}