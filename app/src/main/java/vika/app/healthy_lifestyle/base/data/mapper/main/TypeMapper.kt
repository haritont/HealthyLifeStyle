package vika.app.healthy_lifestyle.base.data.mapper.main

import vika.app.healthy_lifestyle.base.data.entity.main.TypeEntity
import vika.app.healthy_lifestyle.bean.main.Type

interface TypeMapper {
    fun toTypeEntity(type: Type): TypeEntity
    fun toType(typeEntity: TypeEntity): Type
    fun toTypeEntityList(types: List<Type>): List<TypeEntity>
    fun toTypeList(typeEntities: List<TypeEntity>): List<Type>
}

class DefaultTypeMapper :TypeMapper{
    override fun toTypeEntity(type: Type): TypeEntity {
        return TypeEntity(
            type.id,
            type.type,
            type.isProduct
        )
    }

    override fun toType(typeEntity: TypeEntity): Type {
        return Type(
            typeEntity.id,
            typeEntity.type,
            typeEntity.isProduct
        )
    }

    override fun toTypeEntityList(types: List<Type>): List<TypeEntity> {
        val typeEntities = mutableListOf<TypeEntity>()
        for (type in types){
            typeEntities.add(toTypeEntity(type))
        }
        return typeEntities
    }

    override fun toTypeList(typeEntities: List<TypeEntity>): List<Type> {
        val types = mutableListOf<Type>()
        for (type in typeEntities){
            types.add(toType(type))
        }
        return types
    }

}