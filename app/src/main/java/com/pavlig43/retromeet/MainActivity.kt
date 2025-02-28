package com.pavlig43.retromeet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavlig43.retromeet.navigation.NavigationHost
import com.pavlig43.retromeet.ui.theme.RetroMeetTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetroMeetTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
