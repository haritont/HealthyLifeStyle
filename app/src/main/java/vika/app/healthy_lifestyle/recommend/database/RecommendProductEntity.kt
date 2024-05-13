package vika.app.healthy_lifestyle.recommend.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecommendProduct")
class RecommendProductEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val target: Int,
    val meal: Int,
    val product: String,
    val mark: Double
)