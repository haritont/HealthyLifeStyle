package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
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
import vika.app.healthy_lifestyle.base.data.repository.mood.EmotionRepository
import vika.app.healthy_lifestyle.bean.mood.Emotion
import vika.app.healthy_lifestyle.bean.mood.EmotionRecord
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.general.emojiMap

@Composable
fun Emotions (
    emotionList: List<Emotion>?
) {
    val context = LocalContext.current

    val emotionRecordList = EmotionRecordRepository(context).getAllByDate(DateToday().getToday())
    var positive by remember { mutableStateOf(0) }
    var negative by remember { mutableStateOf(0) }
    if (!emotionRecordList.isNullOrEmpty()){
        for (emotionRecord in emotionRecordList){
            if (EmotionRepository(context).getById(emotionRecord.idEmotion).isPositive){
                positive++
            }
            else{
                negative++
            }
        }
    }

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Эмоции",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            color = Black
        )

        if (positive + negative != 0) {
            LinearProgressIndicator(
                progress = positive.toFloat() / (positive + negative).toFloat(),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(16.dp),
                color = Green,
                trackColor = Red
            )
        }
    }

    if (emotionList != null) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(emotionList) { item ->
                var record = EmotionRecordRepository(context).getByIdAndDate(item.id, DateToday().getToday())
                var isChose by remember {
                    mutableStateOf(
                        record != null
                    )
                }

                Surface(
                    modifier = Modifier
                        .padding(10.dp)
                        .border(
                            width = if (isChose) 3.dp else 0.dp,
                            color = if (isChose) {
                                if (item.isPositive) Green else Red
                            } else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    (if (emojiMap[item.name] != null) emojiMap[item.name] else item.name)?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.clickable
                            {
                                isChose = !isChose
                                if (isChose) {
                                    if (item.isPositive) {
                                        positive++
                                    } else {
                                        negative++
                                    }

                                    EmotionRecordRepository(context).insertEmotionRecord(
                                        EmotionRecord(
                                            date = DateToday().getToday(),
                                            idEmotion = item.id
                                        )
                                    )
                                    record = EmotionRecordRepository(context).getByIdAndDate(item.id, DateToday().getToday())
                                } else {
                                    if (item.isPositive) {
                                        positive--
                                    } else {
                                        negative--
                                    }

                                    EmotionRecordRepository(context).deleteEmotionRecord(
                                        record!!
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}