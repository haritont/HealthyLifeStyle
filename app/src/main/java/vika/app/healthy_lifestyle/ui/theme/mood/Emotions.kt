package vika.app.healthy_lifestyle.ui.theme.mood

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.base.data.repository.mood.EmotionRecordRepository
import vika.app.healthy_lifestyle.base.data.repository.mood.EmotionRepository
import vika.app.healthy_lifestyle.bean.mood.Emotion
import vika.app.healthy_lifestyle.bean.mood.EmotionRecord
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.general.emojiMap

@Composable
fun Emotions(
    emotionList: List<Emotion>?
) {
    val context = LocalContext.current

    val emotionRecordList = remember { mutableStateListOf<EmotionRecord>() }
    val positive = remember { mutableStateOf(0) }
    val negative = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val records = EmotionRecordRepository(context).getAllByDate(DateToday().getToday())
        emotionRecordList.clear()
        records?.let { emotionRecordList.addAll(it) }

        var pos = 0
        var neg = 0
        for (emotionRecord in records!!) {
            if (EmotionRepository(context).getById(emotionRecord.idEmotion).isPositive) {
                pos++
            } else {
                neg++
            }
        }
        positive.value = pos
        negative.value = neg
    }

    val allEmotionCount = positive.value + negative.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = LocalContext.current.getString(R.string.emotion),
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            color = Black
        )

        if (allEmotionCount != 0) {
            LinearProgressIndicator(
                progress = positive.value.toFloat() / allEmotionCount.toFloat(),
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
            columns = GridCells.Fixed(6),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(emotionList) { item ->
                val record = EmotionRecordRepository(context).getByIdAndDate(item.id, DateToday().getToday())
                var isChose by remember {
                    mutableStateOf(record != null)
                }

                Surface(
                    modifier = Modifier.padding(10.dp)
                ) {
                    (emojiMap[item.name] ?: item.name).let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .background(
                                    color = if (isChose) { BlueLight } else Color.Transparent
                                )
                                .clickable {
                                isChose = !isChose
                                if (isChose) {
                                    if (item.isPositive) {
                                        positive.value++
                                    } else {
                                        negative.value++
                                    }

                                    EmotionRecordRepository(context).insertEmotionRecord(
                                        EmotionRecord(
                                            date = DateToday().getToday(),
                                            idEmotion = item.id
                                        )
                                    )
                                } else {
                                    if (item.isPositive) {
                                        positive.value--
                                    } else {
                                        negative.value--
                                    }

                                    EmotionRecordRepository(context).deleteEmotionRecord(record!!)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
