package vika.app.healthy_lifestyle.bean

data class Item(
    val title: String,
    val emoji: String,
    val favorite: Boolean,
    val exception: Boolean
)

data class ItemText(
    val title: String,
    val value: Double
)
