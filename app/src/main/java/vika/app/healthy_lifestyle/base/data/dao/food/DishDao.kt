package vika.app.healthy_lifestyle.base.data.dao.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.food.DishEntity

@Dao
interface DishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: DishEntity)

    @Query("SELECT * FROM Dish")
    suspend fun getAll(): List<DishEntity>

    @Query("SELECT * FROM Dish WHERE name = :name")
    suspend fun getByName(name: String): DishEntity

    @Query("SELECT COUNT(*) FROM Dish WHERE name = :name")
    suspend fun isDishExist(name: String): Int

    @Query("SELECT COUNT(*) FROM Dish")
    suspend fun getRowCount(): Int

    @Query("UPDATE Dish SET exception = :exception WHERE name = :name")
    suspend fun updateException(name: String, exception: Boolean)

    @Query("UPDATE Dish SET favorite = :favorite WHERE name = :name")
    suspend fun updateFavorite(name: String, favorite: Boolean)
}