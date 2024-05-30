package vika.app.healthy_lifestyle.base.data.mapper.mood

import vika.app.healthy_lifestyle.base.data.entity.mood.EmotionRecordEntity
import vika.app.healthy_lifestyle.bean.mood.EmotionRecord

interface EmotionRecordMapper {
    fun toEmotionRecord(emotionRecordEntity: EmotionRecordEntity): EmotionRecord
    fun toEmotionRecordEntity(emotionRecord: EmotionRecord): EmotionRecordEntity
}
class DefaultEmotionRecordMapper:EmotionRecordMapper{
    override fun toEmotionRecord(emotionRecordEntity: EmotionRecordEntity): EmotionRecord {
        return EmotionRecord(
            emotionRecordEntity.id,
            emotionRecordEntity.date,
            emotionRecordEntity.idEmotion
        )
    }

    override fun toEmotionRecordEntity(emotionRecord: EmotionRecord): EmotionRecordEntity {
        return EmotionRecordEntity(
            emotionRecord.id,
            emotionRecord.date,
            emotionRecord.idEmotion
        )
    }

}