package vika.app.healthy_lifestyle.base.data.mapper.mood

import vika.app.healthy_lifestyle.base.data.entity.mood.EmotionEntity
import vika.app.healthy_lifestyle.bean.mood.Emotion


interface EmotionMapper {
    fun toEmotion(emotionEntity: EmotionEntity): Emotion
    fun toEmotionList(emotionEntityList: List<EmotionEntity>): List<Emotion>
    fun toEmotionEntity(emotion: Emotion): EmotionEntity
}
class DefaultEmotionMapper:EmotionMapper{
    override fun toEmotion(emotionEntity: EmotionEntity): Emotion {
        return Emotion(
            emotionEntity.id,
            emotionEntity.name,
            emotionEntity.isPositive
        )
    }

    override fun toEmotionEntity(emotion: Emotion): EmotionEntity {
        return EmotionEntity(
            emotion.id,
            emotion.name,
            emotion.isPositive
        )
    }

    override fun toEmotionList(emotionEntityList: List<EmotionEntity>): List<Emotion> {
        val emotions = mutableListOf<Emotion>()
        for (emotionEntity in emotionEntityList){
            emotions.add(toEmotion(emotionEntity))
        }
        return emotions
    }
}