package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.main.RecordEntity

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: RecordEntity)

    @Query("SELECT * FROM Record WHERE date = :date")
    suspend fun getAllByDate(date: String): RecordEntity?

    @Query("UPDATE Record SET progressKilocalories = :kilocalories, progressProteins = :proteins, " +
            "progressFats = :fats, progressCarbohydrates = :carbohydrates WHERE date = :date")
    suspend fun updateProgressFoodRecord(date: String, kilocalories: Double, proteins: Double,
                                   fats: Double, carbohydrates: Double)

    @Query("UPDATE Record SET progressKilocalories = :kilocalories WHERE date = :date")
    suspend fun updateProgressFoodRecord(date: String, kilocalories: Double)

    @Query("UPDATE Record SET progressSteps = :steps WHERE date = :date")
    suspend fun updateProgressStepsRecord(date: String, steps: Int)

    @Query("UPDATE Record SET progressWater = :water WHERE date = :date")
    suspend fun updateProgressWaterRecord(date: String, water: Int)

    @Query("UPDATE Record SET burnedKilocalories = :kilocalories WHERE date = :date")
    suspend fun updateBurnedKilocalories(date: String, kilocalories: Double)

    @Query("UPDATE Record SET targetKilocalories = :kilocalories WHERE date = :date")
    suspend fun updateTargetKilocalories(date: String, kilocalories: Double)

    @Query("UPDATE Record SET targetProteins = :proteins WHERE date = :date")
    suspend fun updateTargetProteins(date: String, proteins: Double)

    @Query("UPDATE Record SET targetFats = :fats WHERE date = :date")
    suspend fun updateTargetFats(date: String, fats: Double)

    @Query("UPDATE Record SET targetCarbohydrates = :carbohydrates WHERE date = :date")
    suspend fun updateTargetCarbohydrates(date: String, carbohydrates: Double)
}