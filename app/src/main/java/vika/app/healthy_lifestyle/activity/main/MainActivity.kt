package vika.app.healthy_lifestyle.activity.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.main.WeightRepository
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Navigation()
            }
        }
    }

    fun saveWeight(value: Double, context: Context) {
        WeightRepository(context).updateWeightByDate(
            date = DateToday().getToday(),
            value = value
        )

        PersonalDataRepository(context).updateWeight(value)
    }

    fun getWeight(context: Context): Double {
        return WeightRepository(context).getLastEntry()!!.value
    }

    fun getCurrentValueWater(context: Context): Int{
        return RecordRepository(context).progressWater(DateToday().getToday())
    }

    fun onClickWater(context: Context, value: Int){
        RecordRepository(context).updateProgressWaterRecord(DateToday().getToday(),
            value + getCurrentValueWater(context))
    }

    fun getTargetKilocalories(context: Context):Double {
        return RecordRepository(context).targetKilocalories(DateToday().getToday())
    }
    fun getProgressKilocalories(context: Context):Double {
        return RecordRepository(context).progressKilocalories(DateToday().getToday())
    }
    fun getTargetProteins(context: Context):Double {
        return RecordRepository(context).targetProteins(DateToday().getToday())
    }
    fun getProgressProteins(context: Context):Double {
        return RecordRepository(context).progressProteins(DateToday().getToday())
    }

    fun getTargetFats(context: Context):Double {
        return RecordRepository(context).targetFats(DateToday().getToday())
    }
    fun getProgressFats(context: Context):Double {
        return RecordRepository(context).progressFats(DateToday().getToday())
    }

    fun getTargetCarbohydrates(context: Context):Double {
        return RecordRepository(context).targetCarbohydrates(DateToday().getToday())
    }
    fun getProgressCarbohydrates(context: Context):Double {
        return RecordRepository(context).progressCarbohydrates(DateToday().getToday())
    }

    fun getBurnedKilocalories(context: Context): Double {
        return RecordRepository(context).burnedKilocalories(DateToday().getToday())
    }

    fun getTargetValueWater(context: Context): Int {
        return RecordRepository(context).getRecordByDate(DateToday().getToday())!!.targetWater.toInt()
    }
}