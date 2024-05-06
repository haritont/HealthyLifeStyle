package vika.app.healthy_lifestyle.base.data.mapper.main

import vika.app.healthy_lifestyle.base.data.entity.main.PersonalDataEntity
import vika.app.healthy_lifestyle.bean.main.PersonalData

interface PersonalDataMapper {
    fun toPersonalDataEntity(personalData: PersonalData): PersonalDataEntity
    fun toPersonalData(personalDataEntity: PersonalDataEntity): PersonalData
}

class DefaultPersonalDataMapper : PersonalDataMapper {
    override fun toPersonalDataEntity(personalData: PersonalData): PersonalDataEntity {
        return PersonalDataEntity(
            id = personalData.id,
            genderId = personalData.genderId,
            height = personalData.height,
            weight = personalData.weight,
            birthDate = personalData.birthDate,
            activityRate = personalData.activityRate,
            name = personalData.name
        )
    }

    override fun toPersonalData(personalDataEntity: PersonalDataEntity): PersonalData {
        return PersonalData(
            id = personalDataEntity.id,
            genderId = personalDataEntity.genderId,
            height = personalDataEntity.height,
            weight = personalDataEntity.weight,
            birthDate = personalDataEntity.birthDate,
            activityRate = personalDataEntity.activityRate,
            name = personalDataEntity.name,
            target = personalDataEntity.target
        )
    }
}
