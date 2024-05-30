package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.base.data.repository.mood.EmotionRecordRepository
import vika.app.healthy_lifestyle.bean.mood.Emotion
import vika.app.healthy_lifestyle.bean.mood.EmotionRecord
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.general.emojiMap

@Composable
fun Emotions (
    emotionList: List<Emotion>
) {
    val context = LocalContext.current
    Text(
        text = "Эмоции",
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Bold,
        color = Black
    )
    LazyRow (
        verticalAlignment = Alignment.CenterVertically
    ){
        items(emotionList) { item ->
            var isChose by remember {
                mutableStateOf(
                EmotionRecordRepository(context).getByIdAndDate(item.id, DateToday().getToday()) != null
                )
            }
            Surface(
                modifier = Modifier
                    .padding(10.dp)
                    .border(
                        width = if (isChose) 3.dp else 0.dp,
                        color = if (isChose) { if (item.isPositive) Green else Red} else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                emojiMap[item.name]?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.clickable
                        {
                            isChose = !isChose
                            EmotionRecordRepository(context).insertEmotionRecord(
                                EmotionRecord(
                                    date = DateToday().getToday(),
                                    idEmotion = item.id
                                )
                            )
                        }

                    )
                }
            }
        }
    }
}