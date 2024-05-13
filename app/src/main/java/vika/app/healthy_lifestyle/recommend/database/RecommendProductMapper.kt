package vika.app.healthy_lifestyle.recommend.database

import vika.app.healthy_lifestyle.recommend.RecommendProduct


interface RecommendProductMapper {
    fun toRecommendProductEntity(recommendProduct: RecommendProduct): RecommendProductEntity
    fun toRecommendProduct(recommendProductEntity: RecommendProductEntity): RecommendProduct
    fun toRecommendProductList(recommendProductEntityList: List<RecommendProductEntity>): List<RecommendProduct>
    fun toRecommendProductEntityList(recommendProductList: List<RecommendProduct>): List<RecommendProductEntity>
}

class DefaultRecommendProductMapper : RecommendProductMapper{
    override fun toRecommendProductEntity(recommendProduct: RecommendProduct): RecommendProductEntity {
       return RecommendProductEntity(
           id = 0,
           target = recommendProduct.target,
           meal = recommendProduct.meal,
           product = recommendProduct.product,
           mark = recommendProduct.mark
       )
    }

    override fun toRecommendProduct(recommendProductEntity: RecommendProductEntity): RecommendProduct {
        return RecommendProduct(
            target = recommendProductEntity.target,
            meal = recommendProductEntity.meal,
            product = recommendProductEntity.product,
            mark = recommendProductEntity.mark
        )
    }

    override fun toRecommendProductList(recommendProductEntityList: List<RecommendProductEntity>): List<RecommendProduct> {
        val recommendProductList = mutableListOf<RecommendProduct>()

        for (recommendProductEntity in recommendProductEntityList){
            recommendProductList.add(toRecommendProduct(recommendProductEntity))
        }

        return recommendProductList
    }

    override fun toRecommendProductEntityList(recommendProductList: List<RecommendProduct>): List<RecommendProductEntity> {
        val recommendProductListEntity = mutableListOf<RecommendProductEntity>()

        for (recommendProduct in recommendProductList){
            recommendProductListEntity.add(toRecommendProductEntity(recommendProduct))
        }

        return recommendProductListEntity
    }

}