package vika.app.healthy_lifestyle.activity.sport

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.ActivismRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.PhysicalExerciseRepository
import vika.app.healthy_lifestyle.base.data.repository.sport.TrainingRepository
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.bean.sport.Activism
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
import vika.app.healthy_lifestyle.bean.sport.Training
import vika.app.healthy_lifestyle.calculations.CreateAdvice
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

    fun getAll(context: Context): List<PhysicalExercise> {
        return PhysicalExerciseRepository(context).getAll()
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
        val steps = stepValue - getProgressSteps(context)
        RecordRepository(context).updateProgressStepsRecord(DateToday().getToday(), stepValue)
        val burned = RecordRepository(context).burnedKilocalories(DateToday().getToday())
        RecordRepository(context).updateBurnedKilocalories(
            DateToday().getToday(),
            burned + steps * 0.04
        )
    }

    fun getLastActivism(context: Context): List<Activism> {
        return ActivismRepository(context).getActivismByDate(DateToday().getToday())
    }

    fun deleteActivism(context: Context, title: String, value: Double, today: String) {
        ActivismRepository(context).deleteActivism(title, value, today)
    }

    fun getPhysicalExerciseByName(context: Context, title: String): PhysicalExercise {
        return PhysicalExerciseRepository(context).getPhysicalExerciseByName(title)
    }

    fun updatePhysicalExercise(
        context: Context, id: Long, name: String, met: Double,
        type: String, favorite: Boolean, exception: Boolean
    ) {
        PhysicalExerciseRepository(context).insertPhysicalExercise(
            PhysicalExercise(
                id = id,
                name = name,
                met = met,
                type = type,
                favorite = favorite,
                exception = exception
            )
        )
    }

    fun getAllPhysicalExercises(context: Context): List<PhysicalExercise>  {
        return PhysicalExerciseRepository(context).getAllPhysicalExercises()
    }

    fun getTraining(context: Context, id: Long): List<Training> {
        return TrainingRepository(context).getTraining(id)
    }

    fun getPhysicalExerciseById(context: Context, idPhysicalExercise: Long): PhysicalExercise {
        return PhysicalExerciseRepository(context).getPhysicalExerciseById(idPhysicalExercise)
    }

    fun updateTraining(context: Context, id: Long, name: String, met: Double,
                       type: String, favorite: Boolean, exception: Boolean,
                       listPhysicalExercise: List<ItemText>) {
        PhysicalExerciseRepository(context).insertPhysicalExercise(
            PhysicalExercise(
                id = id,
                name = name,
                met = met,
                type = type,
                favorite = favorite,
                exception = exception,
                training = true
            )
        )

        TrainingRepository(context).deleteAllTraining(id)
        for (item in listPhysicalExercise) {
            val physicalExercise = PhysicalExerciseRepository(context).getPhysicalExerciseByName(item.title)
            TrainingRepository(context).insertTraining(
                Training(
                    idName = id,
                    idPhysicalExercise = physicalExercise.id,
                    valuePhysicalExercise = item.value
                )
            )
        }
    }

    fun insertPhysicalExercise(context: Context, name: String, met: Double, type: String) {
        if (!PhysicalExerciseRepository(context).isPhysicalExerciseExists(name)) {
            PhysicalExerciseRepository(context).insertPhysicalExercise(
                PhysicalExercise(
                    name = name,
                    met = met,
                    type = type
                )
            )
        }
    }

    fun insertTraining(context: Context, name: String, met: Double,
                       type: String, listPhysicalExercise: List<ItemText>) {
        if (!PhysicalExerciseRepository(context).isPhysicalExerciseExists(name)) {
            PhysicalExerciseRepository(context).insertPhysicalExercise(
                PhysicalExercise(
                    name = name,
                    met = met,
                    type = type,
                    training = true
                )
            )

            val trainingId = PhysicalExerciseRepository(context).getPhysicalExerciseByName(name).id
            for (item in listPhysicalExercise){
                val physicalExerciseId = PhysicalExerciseRepository(context).getPhysicalExerciseByName(item.title).id
                TrainingRepository(context).insertTraining(
                    Training(
                        idName = trainingId,
                        idPhysicalExercise = physicalExerciseId,
                        valuePhysicalExercise = item.value
                    )
                )
            }
        }
    }

    fun getAdvice(context: Context): String {
        val personalData = PersonalDataRepository(context).getPersonalData()

        return CreateAdvice().getSportAdvice(context, personalData)
    }
}