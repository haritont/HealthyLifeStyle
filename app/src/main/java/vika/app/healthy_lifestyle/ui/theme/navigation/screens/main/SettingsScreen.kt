package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import vika.app.healthy_lifestyle.base.data.repository.main.NotificationsRepository
import vika.app.healthy_lifestyle.ui.theme.main.Notification

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val notificationsRepository = NotificationsRepository(context)
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Notification(
                title = "Завтрак",
                hour = notificationsRepository.getNotificationsHour("Завтрак"),
                minute = notificationsRepository.getNotificationsMinute("Завтрак")
            )
            Notification(
                title = "Обед",
                hour = notificationsRepository.getNotificationsHour("Обед"),
                minute = notificationsRepository.getNotificationsMinute("Обед")
            )
            Notification(
                title = "Ужин",
                hour = notificationsRepository.getNotificationsHour("Ужин"),
                minute = notificationsRepository.getNotificationsMinute("Ужин")
            )
            Notification(
                title = "Перекус",
                hour = notificationsRepository.getNotificationsHour("Перекус"),
                minute = notificationsRepository.getNotificationsMinute("Перекус")
            )
        }
    }
}