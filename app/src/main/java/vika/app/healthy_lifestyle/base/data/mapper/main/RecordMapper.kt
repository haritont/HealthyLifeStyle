package vika.app.healthy_lifestyle.base.data.mapper.main

import vika.app.healthy_lifestyle.base.data.entity.main.RecordEntity
import vika.app.healthy_lifestyle.bean.main.Record

interface RecordMapper {
    fun toRecordEntity(record: Record): RecordEntity
    fun toRecord(recordEntity: RecordEntity): Record
}

class DefaultRecordMapper: RecordMapper {
    override fun toRecordEntity(record: Record): RecordEntity {
      return RecordEntity(
          record.id,
          record.date,
          record.targetKilocalories,
          record.targetProteins,
          record.targetFats,
          record.targetCarbohydrates,
          record.targetWater,
          record.targetSteps,
          record.progressKilocalories,
          record.progressProteins,
          record.progressFats,
          record.progressCarbohydrates,
          record.progressWater,
          record.progressSteps,
          record.burnedKilocalories
      )
    }

    override fun toRecord(recordEntity: RecordEntity): Record {
        return Record(
            recordEntity.id,
            recordEntity.date,
            recordEntity.targetKilocalories,
            recordEntity.targetProteins,
            recordEntity.targetFats,
            recordEntity.targetCarbohydrates,
            recordEntity.targetWater,
            recordEntity.targetSteps,
            recordEntity.progressKilocalories,
            recordEntity.progressProteins,
            recordEntity.progressFats,
            recordEntity.progressCarbohydrates,
            recordEntity.progressWater,
            recordEntity.progressSteps,
            recordEntity.burnedKilocalories
        )
    }
}