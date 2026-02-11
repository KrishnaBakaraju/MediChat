package com.medichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.medichat.data.AppContainer
import com.medichat.ui.MediChatApp
import com.medichat.ui.theme.MediChatTheme

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(applicationContext)

        setContent {
            MediChatTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MediChatApp(appContainer = appContainer)
                }
            }
        }
    }
}
