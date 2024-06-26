package vika.app.healthy_lifestyle.activity.food

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.food.IngredientRepository
import vika.app.healthy_lifestyle.base.data.repository.food.NutritionRepository
import vika.app.healthy_lifestyle.base.data.repository.food.RecipeRepository
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.base.data.repository.main.RecordRepository
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.HabitRecordRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.HabitRepository
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.food.Nutrition
import vika.app.healthy_lifestyle.bean.food.Recipe
import vika.app.healthy_lifestyle.bean.main.Record
import vika.app.healthy_lifestyle.bean.main.Type
import vika.app.healthy_lifestyle.bean.mood.HabitRecord
import vika.app.healthy_lifestyle.calculation.CreateAdvice
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.calculation.MealCalc
import vika.app.healthy_lifestyle.calculation.PersonalTarget
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.navigation.Navigation

class FoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Navigation()
            }
        }
    }

    fun getAllProducts(context: Context):List<Ingredient>? {
        return IngredientRepository(context).getAllProduct()
    }

    fun addNewDish(
        context: Context, name: String, type: String,
        selectListIngredient: MutableList<ItemText>
    ) {
        val exists = IngredientRepository(context).isIngredientExists(name)
        if (!exists) {
            var kilocalories = 0.0
            var proteins = 0.0
            var fats = 0.0
            var carbohydrates = 0.0

            for (item in selectListIngredient) {
                val ingredient = IngredientRepository(context).getIngredientByName(item.title)

                kilocalories += ingredient.kilocalories * item.value / 100
                proteins += ingredient.proteins * item.value / 100
                fats += ingredient.fats * item.value / 100
                carbohydrates += ingredient.carbohydrates * item.value / 100
            }

            var typeNew = TypeRepository(context).getByName(type)
            if (typeNew == null){
                typeNew = Type(
                    type = type,
                    isProduct = true
                )
                TypeRepository(context).insert(typeNew)
            }
            IngredientRepository(context).insertIngredient(
                Ingredient(
                    name = name,
                    kilocalories = kilocalories,
                    proteins = proteins,
                    fats = fats,
                    carbohydrates = carbohydrates,
                    type = typeNew,
                    isDish = true
                )
            )

            val dishId = IngredientRepository(context).getIngredientByName(name).id
            for (item in selectListIngredient) {
                val ingredient = IngredientRepository(context).getIngredientByName(item.title)

                RecipeRepository(context).insertRecipe(
                    Recipe(
                        idDish = dishId,
                        idIngredient = ingredient.id,
                        valueIngredient = item.value
                    )
                )
            }
        }
    }

    fun updateIngredient(
        context: Context, id: Long, name: String, kilocalories: Double,
        proteins: Double, fats: Double, carbohydrates: Double, type: String,
        favorite: Boolean, exception: Boolean
    ) {
        var typeNew = TypeRepository(context).getByName(type)
        if (typeNew == null){
            typeNew = Type(
                type = type,
                isProduct = true
            )
            TypeRepository(context).insert(typeNew)
        }
        IngredientRepository(context).insertIngredient(
            Ingredient(
                id = id,
                name = name,
                kilocalories = kilocalories,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates,
                type = typeNew,
                favorite = favorite,
                exception = exception
            )
        )
    }

    fun getIngredient(context: Context, name: String): Ingredient {
        return IngredientRepository(context).getIngredientByName(name)
    }

    fun getIngredient(context: Context, id: Long): Ingredient {
        return IngredientRepository(context).getIngredientById(id)
    }

    fun addNewIngredient(
        context: Context, name: String, kilocalories: Double,
        proteins: Double, fats: Double, carbohydrates: Double, type: String
    ) {
        var typeNew = TypeRepository(context).getByName(type)
        if (typeNew == null){
            typeNew = Type(
                type = type,
                isProduct = true
            )
            TypeRepository(context).insert(typeNew)
        }
        val exists = IngredientRepository(context).isIngredientExists(name)
        if (!exists) {
            IngredientRepository(context).insertIngredient(
                Ingredient(
                    name = name,
                    kilocalories = kilocalories,
                    proteins = proteins,
                    fats = fats,
                    carbohydrates = carbohydrates,
                    type = typeNew
                )
            )
        }
    }

    fun addIngredient(context: Context, name: String, value: Double, date: String, meal: String) {
        NutritionRepository(context).insertNutrition(
            Nutrition(
                token = "",
                name = name,
                value = value,
                date = date,
                meal = meal
            )
        )

        val ingredient = IngredientRepository(context).getIngredientByName(name)
        addKPFC(
            context,
            (ingredient.kilocalories * value / 100.0).toString(),
            (ingredient.proteins * value / 100.0).toString(),
            (ingredient.fats * value / 100.0).toString(),
            (ingredient.carbohydrates * value / 100.0).toString(),
            date
        )

        val habit = HabitRepository(context).getByProduct(ingredient.type.type)
        if (habit != null && !habit.isPositive) {
            val record = HabitRecordRepository(context).getRecordByIdHabit(habit.id, true)
            if (record != null) {
                record.tracking = false
                record.dateEnd = DateToday().getToday()
                HabitRecordRepository(context).insertHabitRecord(
                    record
                )
                HabitRecordRepository(context).insertHabitRecord(
                    HabitRecord(
                        idHabit = record.idHabit,
                        tracking = true,
                        dateStart = DateToday().getToday(),
                    )
                )
            }
        }

        if ((ingredient.type.type == "Вода" || ingredient.type.type == "Напиток")) {
            RecordRepository(context).updateProgressWaterRecord(
                DateToday().getToday(),
                value.toInt() + RecordRepository(context).progressWater(DateToday().getToday())
            )
        }
    }

    fun updateFavoriteIngredient(context: Context, name: String, favorite: Boolean) {
        IngredientRepository(context).updateIngredientFavorite(name, favorite)
    }

    fun updateExceptionIngredient(context: Context, name: String, exception: Boolean) {
        IngredientRepository(context).updateIngredientException(name, exception)
    }

    fun getAdvice(context: Context): String {
        val personalData = PersonalDataRepository(context).getPersonalData()!!
        return CreateAdvice().getProductAdvice(context, personalData, MealCalc().getCurrentMeal())
    }

    fun addKPFC(
        context: Context, kilocaloriesState: String, proteinsState: String,
        fatsState: String, carbohydratesState: String, date: String
    ) {
        val record = RecordRepository(context).getRecordByDate(date)
        if (record != null) {
            RecordRepository(context).updateProgressFoodRecord(
                date,
                kilocaloriesState.toDouble() + record.progressKilocalories,
                proteinsState.toDouble() + record.progressProteins,
                fatsState.toDouble() + record.progressFats,
                carbohydratesState.toDouble() + record.progressCarbohydrates
            )
        } else {
            val target = PersonalTarget()
            target.count(PersonalDataRepository(this).getPersonalData()!!, context)
            RecordRepository(context).insertRecord(
                Record(
                    date = date, targetKilocalories = target.kilocalories,
                    targetProteins = target.proteins, targetFats = target.fats,
                    targetCarbohydrates = target.carbohydrates, targetWater = target.water,
                    progressKilocalories = kilocaloriesState.toDouble(),
                    progressProteins = proteinsState.toDouble(),
                    progressCarbohydrates = carbohydratesState.toDouble(),
                    progressFats = fatsState.toDouble()
                )
            )
        }
    }

    fun getLastNutrition(context: Context): List<Nutrition> {
        return NutritionRepository(context).getNutritionByDate(DateToday().getToday())
    }

    fun deleteNutrition(context: Context, name: String, value: Double, date: String) {
        NutritionRepository(context).deleteNutrition(name, value, date)
        val record = RecordRepository(context).getRecordByDate(date)
        val ingredient = getIngredient(context, name)
        RecordRepository(context).updateProgressFoodRecord(
            date,
            record!!.progressKilocalories - ingredient.kilocalories / 100 * value,
            record.progressProteins - ingredient.proteins / 100 * value,
            record.progressFats - ingredient.fats / 100 * value,
            record.progressCarbohydrates - ingredient.carbohydrates / 100 * value
        )
    }

    fun getDish(context: Context, title: String): Ingredient {
        return IngredientRepository(context).getIngredientByName(title)
    }

    fun getRecipe(context: Context, idDish: Long): List<Recipe> {
        return RecipeRepository(context).getRecipesForDish(idDish)
    }

    fun updateDish(
        context: Context,
        id: Long,
        name: String,
        kilocalories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double,
        type: String,
        favorite: Boolean,
        exception: Boolean,
        selectListIngredient: MutableList<ItemText>
    ) {
        var typeNew = TypeRepository(context).getByName(type)
        if (typeNew == null){
            typeNew = Type(
                type = type,
                isProduct = true
            )
            TypeRepository(context).insert(typeNew)
        }
        IngredientRepository(context).insertIngredient(
            Ingredient(
                id = id,
                name = name,
                kilocalories = kilocalories,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates,
                type = typeNew,
                favorite = favorite,
                exception = exception,
                isDish = true
            )
        )

        RecipeRepository(context).deleteAllRecipe(id)
        for (item in selectListIngredient) {
            val ingredient = IngredientRepository(context).getIngredientByName(item.title)
            RecipeRepository(context).insertRecipe(
                Recipe(
                    idDish = id,
                    idIngredient = ingredient.id,
                    valueIngredient = item.value
                )
            )
        }
    }
}