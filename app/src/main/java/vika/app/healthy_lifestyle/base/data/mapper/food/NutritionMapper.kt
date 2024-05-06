package vika.app.healthy_lifestyle.base.data.mapper.food

import vika.app.healthy_lifestyle.base.data.entity.food.NutritionEntity
import vika.app.healthy_lifestyle.bean.food.Nutrition

interface NutritionMapper {
    fun toNutritionEntity(nutrition: Nutrition): NutritionEntity
    fun toNutrition(nutritionEntity: NutritionEntity): Nutrition
    fun toNutritionList(nutritionEntities: List<NutritionEntity>): List<Nutrition>
}

class DefaultNutritionMapper: NutritionMapper {
    override fun toNutritionEntity(nutrition: Nutrition): NutritionEntity {
       return NutritionEntity(
           nutrition.id,
           nutrition.name,
           nutrition.value,
           nutrition.date,
           nutrition.meal
       )
    }

    override fun toNutrition(nutritionEntity: NutritionEntity): Nutrition {
        return Nutrition(
            id = nutritionEntity.id,
            name = nutritionEntity.name,
            value = nutritionEntity.value,
            date = nutritionEntity.date,
            meal = nutritionEntity.meal
        )
    }

    override fun toNutritionList(nutritionEntities: List<NutritionEntity>): List<Nutrition> {
        val nutritions = mutableListOf<Nutrition>()
        for (nutritionEntity in nutritionEntities){
            nutritions.add(toNutrition(nutritionEntity))
        }
        return nutritions
    }
}