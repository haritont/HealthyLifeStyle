package vika.app.healthy_lifestyle.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Healthy_LifestyleTheme
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

class AuthorizationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Healthy_LifestyleTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextEntrance()
                    val loginState = remember { mutableStateOf("") }
                    val passwordState = remember { mutableStateOf("") }

                    TextFieldLogin(loginState.value) { newLogin ->
                        loginState.value = newLogin
                    }

                    TextFieldPassword(passwordState.value) { newPassword ->
                        passwordState.value = newPassword
                    }

                    ButtonBlue(
                        stringResource(id = R.string.login_to_account),
                        onClick = { authorization(loginState.value, passwordState.value) }
                    )
                }
            }
        }
    }

    @Composable
    fun TextFieldLogin(login: String, onValueChange: (String) -> Unit) {
        TextFieldBlue(
            value = login,
            onValueChange = { newLogin -> onValueChange(newLogin) },
            label = {
                Text(stringResource(id = R.string.login))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Image(
                    painterResource(R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        )
    }

    @Composable
    fun TextFieldPassword(password: String, onValueChange: (String) -> Unit) {
        TextFieldBlue(
            value = password,
            label = {
                Text(stringResource(id = R.string.password))
            },
            onValueChange = { newPassword -> onValueChange(newPassword) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Image(
                    painterResource(R.drawable.password),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        )
    }

    @Composable
    fun TextEntrance() {
        Text(
            text = stringResource(id = R.string.entrance),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }

    private fun authorization(login: String, password: String) = runBlocking {
//        val token = DefaultApiServiceRepository().authorization(login, password)
//        if (token == ""){
//            showToast("Ошибка авторизации")
//        }
//        else {
//            setToken(token)
//            Log.d("AuthorizationActivity", "Token saved to database")
//        }
    }

    private fun setToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    private fun getToken(context: Context) : String? {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}