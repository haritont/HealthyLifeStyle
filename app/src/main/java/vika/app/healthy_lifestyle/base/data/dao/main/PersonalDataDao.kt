package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import vika.app.healthy_lifestyle.base.data.entity.main.PersonalDataEntity
import vika.app.healthy_lifestyle.base.data.entity.main.WeightEntity

@Dao
interface PersonalDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personalDataEntity: PersonalDataEntity)

    @Update
    suspend fun updateWeight(personalDataEntity: PersonalDataEntity)

    @Query("SELECT * FROM PersonalData ORDER BY id DESC LIMIT 1")
    suspend fun getLastEntry(): PersonalDataEntity?
}