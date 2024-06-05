package vika.app.healthy_lifestyle.base.data.dao.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.food.IngredientEntity

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient: IngredientEntity)

    @Query("SELECT name FROM Ingredient WHERE id = :ingredientId")
    suspend fun getName(ingredientId: Long): String

    @Query("SELECT * FROM Ingredient WHERE isDish = 0")
    suspend fun getAllIngredients(): List<IngredientEntity>?

    @Query("SELECT * FROM Ingredient WHERE name = :name")
    suspend fun getByName(name: String): IngredientEntity

    @Query("SELECT * FROM Ingredient WHERE id = :id")
    suspend fun getById(id: Long): IngredientEntity

    @Query("SELECT COUNT(*) FROM Ingredient")
    suspend fun getRowCount(): Int

    @Query("SELECT name FROM Ingredient")
    suspend fun getAllNames(): List<String>

    @Query("SELECT COUNT(*) FROM Ingredient WHERE name = :name")
    suspend fun isIngredientExists(name: String): Int

    @Query("UPDATE Ingredient SET exception = :exception WHERE name = :name")
    suspend fun updateException(name: String, exception: Boolean)

    @Query("UPDATE Ingredient SET favorite = :favorite WHERE name = :name")
    suspend fun updateFavorite(name: String, favorite: Boolean)
   @Query("SELECT * FROM Ingredient")
    suspend fun getAll(): List<IngredientEntity>?
    @Query("UPDATE Ingredient SET exception = :exception WHERE type = :type")
    suspend fun updateIngredientExceptionByType(type: String, exception: Boolean)
    @Query("SELECT * FROM Ingredient WHERE kilocalories * :value / 100 < :targetKilo AND " +
            "proteins * :value / 100 < :targetProtein AND fats * :value / 100 < :targetFat AND " +
            "carbohydrates * :value / 100 < :targetCarb AND exception = 0"
    )
    suspend fun getIngredientByValueTarget(value: Double, targetKilo: Double, targetProtein: Double,
                                           targetFat: Double, targetCarb: Double): List<IngredientEntity>
}