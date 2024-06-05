package vika.app.healthy_lifestyle.activity.statistics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.main.WeightRepository
import vika.app.healthy_lifestyle.bean.main.Record
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.navigation.Navigation

class StatisticsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Navigation()
            }
        }
    }

    fun getActivism(dates: List<String>): MutableList<Float> {
        val records = getRecords(dates)
        val activism = mutableListOf<Float>()

        for (record in records) {
            activism.add(record.burnedKilocalories.toFloat())
        }

        return activism
    }

    fun getCarbohydrates(dates: List<String>): MutableList<Float> {
        val records = getRecords(dates)
        val carbohydrates = mutableListOf<Float>()

        for (record in records) {
            carbohydrates.add(record.progressCarbohydrates.toFloat())
        }

        return carbohydrates
    }

    fun getFats(dates: List<String>): MutableList<Float> {
        val records = getRecords(dates)
        val fats = mutableListOf<Float>()

        for (record in records) {
            fats.add(record.progressFats.toFloat())
        }

        return fats
    }

    fun getProteins(dates: List<String>): MutableList<Float> {
        val records = getRecords(dates)
        val proteins = mutableListOf<Float>()

        for (record in records) {
            proteins.add(record.progressProteins.toFloat())
        }

        return proteins
    }

    fun getWaters(dates: List<String>): MutableList<Float> {
        val records = getRecords(dates)
        val waters = mutableListOf<Float>()

        for (record in records) {
            waters.add(record.progressWater.toFloat())
        }

        return waters
    }

    fun getKilocalories(dates: List<String>): MutableList<Float> {
        val records = getRecords(dates)
        val kilocalories = mutableListOf<Float>()

        for (record in records) {
            kilocalories.add(record.progressKilocalories.toFloat())
        }

        return kilocalories
    }

    fun getWeights(dates: List<String>): MutableList<Float> {
        val weights = mutableListOf<Float>()
        var lastWeight = PersonalDataRepository(this).getWeight()
        for (date in dates) {
            val weight = WeightRepository(this).getByDate(date)
            if (weight != null) {
                weights.add(weight.value.toFloat())
                lastWeight = weight.value
            } else {
                weights.add(lastWeight.toFloat())
            }
        }
        return weights
    }

    private fun getRecords(dates: List<String>): MutableList<Record> {
        val records = mutableListOf<Record>()

        for (date in dates) {
            val record = RecordRepository(this).getRecordByDate(date)
            if (record == null) {
                records.add(
                    Record(
                        date = date
                    )
                )
            } else {
                records.add(record)
            }
        }
        return records
    }
}