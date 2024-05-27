package vika.app.healthy_lifestyle.base.data.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Barcode")
data class BarcodeEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var code: String = "",
    var idIngredient: Long = 0
)