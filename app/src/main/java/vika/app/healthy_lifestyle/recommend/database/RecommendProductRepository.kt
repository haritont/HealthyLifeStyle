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

    fun getRecommendProduct(name: String): RecommendProduct = runBlocking{
        recommendProductMapper.toRecommendProduct(recommendProductDao.getRecommendProduct(name))
    }

    fun updateMarkRecommendProduct(name: String, mark: Double, trigger: Boolean) = runBlocking{
        val product = getRecommendProduct(name)
        if (mark == 0.0 && trigger) {
            recommendProductDao.updateMark(name, product.mark - 5.0)
        }

        if (mark == 1.0 && trigger) {
            recommendProductDao.updateMark(name, product.mark + 1.0)
        }

        if (mark == 0.0 && !trigger) {
            recommendProductDao.updateMark(name, product.mark + 5.0)
        }

        if (mark == 1.0 && !trigger) {
            recommendProductDao.updateMark(name, product.mark - 1.0)
        }
    }

    fun getRecommendProductList(mark: Double, target: Int, meal: Int): List<RecommendProduct> = runBlocking{
        recommendProductMapper.toRecommendProductList(recommendProductDao.getRecommendProductList(mark, target, meal))
    }
}