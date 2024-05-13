package vika.app.healthy_lifestyle.recommend.database

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.recommend.RecommendProduct

class RecommendProductRepository (context: Context){
    private val recommendProductDao: RecommendProductDao
    private val recommendProductDatabase: RecommendProductDatabase = RecommendProductDatabase.getInstance(context)
    private val recommendProductMapper: RecommendProductMapper

    init {
        recommendProductDao = recommendProductDatabase.recommendProductDao()
        recommendProductMapper = DefaultRecommendProductMapper()
    }

    fun insertRecommendProduct(recommendProduct: RecommendProduct) = runBlocking{
        recommendProductDao.insert(recommendProductMapper.toRecommendProductEntity(recommendProduct))
    }

    fun updateMarkRecommendProduct(name: String, mark: Double) = runBlocking{
        recommendProductDao.updateMark(name, mark)
    }

    fun getRecommendProductList(mark: Double): List<RecommendProduct> = runBlocking{
        recommendProductMapper.toRecommendProductList(recommendProductDao.getRecommendProductList(mark))
    }
}