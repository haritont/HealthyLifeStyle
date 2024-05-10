package vika.app.healthy_lifestyle.activity.sport

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.ActivismRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.PhysicalExerciseRepository
import vika.app.healthy_lifestyle.bean.sport.Activism
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.navigation.Navigation

class SportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Navigation()
            }
        }
    }

    fun toMoreTraining(name: String){

    }

    fun toMorePhysicalExercise(name: String){

    }

    fun addNewPhysicalExercise(context: Context, name: String, met: Double, type: String, training: Boolean){
        val exists = PhysicalExerciseRepository(context).isPhysicalExerciseExists(name)

        if (exists) {
            PhysicalExerciseRepository(context).insertPhysicalExercise(
                PhysicalExercise(
                    name = name,
                    met = met,
                    type = type,
                    training = training
                )
            )
        }
    }

    fun getAllTrainings(context: Context): List<PhysicalExercise> {
        return PhysicalExerciseRepository(context).getAllTrainings()
    }

    fun add(context: Context, name: String, value: Double, date: String, option: String){
        ActivismRepository(context).insertActivism(
            Activism(
                name = name,
                value = value,
                date = date
            )
        )

        val weight = PersonalDataRepository(context).getWeight()
        val record = RecordRepository(context).getRecordByDate(date)
        val physicalExercise = PhysicalExerciseRepository(context).getPhysicalExerciseByName(name)

        RecordRepository(context).updateBurnedKilocalories(
            date,
            record!!.burnedKilocalories + (weight * (value / 60.0) * physicalExercise.met)
        )
    }

    fun getAllPhysicalExercises(context: Context): List<PhysicalExercise> {
        return PhysicalExerciseRepository(context).getAllPhysicalExercises()
    }

    fun updateFavorite(context: Context, name: String, favorite: Boolean){
        PhysicalExerciseRepository(context).updatePhysicalExerciseFavorite(name, favorite)
    }

    fun updateException(context: Context, name: String, exception: Boolean){
        PhysicalExerciseRepository(context).updatePhysicalExerciseException(name, exception)
    }

    fun getProgressSteps(context: Context): Int {
        return RecordRepository(context).progressSteps(DateToday().getToday())
    }

    fun saveProgressSteps(context: Context, stepValue: Int) {
        RecordRepository(context).updateProgressStepsRecord(DateToday().getToday(), stepValue)
    }

}