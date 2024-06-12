package vika.app.healthy_lifestyle.base.data.dao.sport

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.sport.PhysicalExerciseEntity

@Dao
interface PhysicalExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(physicalExercise: PhysicalExerciseEntity)

    @Query("SELECT * FROM PhysicalExercise WHERE name = :name")
    suspend fun getPhysicalExerciseByName(name: String): PhysicalExerciseEntity

    @Query("SELECT * FROM PhysicalExercise WHERE training = 1")
    suspend fun getPhysicalExerciseTraining(): List<PhysicalExerciseEntity>

    @Query("SELECT * FROM PhysicalExercise WHERE training = 0")
    suspend fun getPhysicalExercise(): List<PhysicalExerciseEntity>

    @Query("SELECT * FROM PhysicalExercise WHERE id = :id")
    suspend fun getPhysicalExerciseById(id: Long): PhysicalExerciseEntity

    @Query("SELECT name FROM PhysicalExercise")
    suspend fun getNamesPhysicalExercises():List<String>

    @Query("SELECT COUNT(*) FROM PhysicalExercise WHERE name = :namePhysicalExercise")
    suspend fun isPhysicalExerciseExists(namePhysicalExercise: String): Int

    @Query("UPDATE PhysicalExercise SET favorite = :favorite WHERE name = :name")
    suspend fun updateFavorite(name: String, favorite: Boolean)

    @Query("UPDATE PhysicalExercise SET exception = :exception WHERE name = :name")
    suspend fun updateException(name: String, exception: Boolean)
    @Query("SELECT * FROM PhysicalExercise")
    suspend fun getAll(): List<PhysicalExerciseEntity>?

    @Query("SELECT * FROM PhysicalExercise WHERE type =:type")
    suspend fun getAllByType(type: String): List<PhysicalExerciseEntity>
    @Query("SELECT * FROM PhysicalExercise WHERE met * :value * :weight <= :kilo AND exception = 0")
    suspend fun getPhysicalExerciseByTarget(value: Double, kilo: Double, weight: Double): List<PhysicalExerciseEntity>?
}