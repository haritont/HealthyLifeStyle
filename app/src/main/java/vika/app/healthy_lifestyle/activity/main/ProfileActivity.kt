package vika.app.healthy_lifestyle.activity.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import vika.app.healthy_lifestyle.base.data.repository.main.PersonalDataRepository
import vika.app.healthy_lifestyle.bean.main.PersonalData
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme

class ProfileActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme{
            }
        }
    }

    fun getPersonalData(context: Context): PersonalData {
       return PersonalDataRepository(context).getPersonalData();
    }

    fun insertPersonalData(context: Context, personalData: PersonalData) {
        PersonalDataRepository(context).insertPersonalData(personalData)
    }
}