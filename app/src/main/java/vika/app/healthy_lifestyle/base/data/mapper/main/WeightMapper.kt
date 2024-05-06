package vika.app.healthy_lifestyle.base.data.mapper.main

import vika.app.healthy_lifestyle.base.data.entity.main.WeightEntity
import vika.app.healthy_lifestyle.bean.main.Weight

interface WeightMapper {
    fun toWeightEntity(weight: Weight): WeightEntity
    fun toWeight(weightEntity: WeightEntity): Weight
    fun toWeightList(weightEntities: List<WeightEntity>): List<Weight>
}

class DefaultWeightMapper : WeightMapper {
    override fun toWeightEntity(weight: Weight): WeightEntity {
        return WeightEntity(
            weight.id,
            weight.date,
            weight.value
        )
    }

    override fun toWeight(weightEntity: WeightEntity): Weight {
        return Weight(
            weightEntity.id,
            weightEntity.date,
            weightEntity.value
        )
    }

    override fun toWeightList(weightEntities: List<WeightEntity>): List<Weight> {
        val weights = mutableListOf<Weight>()
        for (weightEntity in weightEntities){
            weights.add(toWeight(weightEntity))
        }
        return weights
    }
}