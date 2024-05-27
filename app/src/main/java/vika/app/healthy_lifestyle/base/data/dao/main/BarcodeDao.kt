package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.main.BarcodeEntity

@Dao
interface BarcodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barcode: BarcodeEntity)

    @Query("SELECT * FROM Barcode WHERE code = :code")
    suspend fun getByCode(code: String): BarcodeEntity
}