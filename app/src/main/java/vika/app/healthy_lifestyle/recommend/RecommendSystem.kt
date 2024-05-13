package vika.app.healthy_lifestyle.recommend

import android.content.Context
import vika.app.healthy_lifestyle.recommend.database.RecommendProductRepository

class RecommendSystem(
    val context: Context,
    val target: Int,
    val meal: Int
) {
    private val minMark = 0.5

    fun getProducts(count: Int): List<RecommendProduct>{
        return getListProduct().shuffled().take(count)
    }
    private fun getListProduct(): List<RecommendProduct>{
        return RecommendProductRepository(context).getRecommendProductList(minMark, target, meal);
    }
}