package vika.app.healthy_lifestyle.recommend.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecommendProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: RecommendProductEntity)

    @Query("UPDATE RecommendProduct SET mark =:mark WHERE product =:name")
    suspend fun updateMark(name: String, mark: Double)

    @Query("SELECT * FROM RecommendProduct WHERE target =:target AND meal =:meal AND mark >=:mark")
    suspend fun getRecommendProductList(mark: Double, target: Int, meal: Int): List<RecommendProductEntity>

    @Query("SELECT * FROM RecommendProduct WHERE product =:name")
    suspend fun getRecommendProduct(name: String): RecommendProductEntity
}