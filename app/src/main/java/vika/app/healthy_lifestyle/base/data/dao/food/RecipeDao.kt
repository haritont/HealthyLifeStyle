package vika.app.healthy_lifestyle.base.data.dao.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.food.RecipeEntity

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity)

    @Query("SELECT * FROM Recipe WHERE idDish = :idDish")
    suspend fun getRecipesForDish(idDish: Long): List<RecipeEntity>
    @Query("DELETE FROM Recipe WHERE idDish = :idDish")
    suspend fun deleteAll(idDish: Long)
}