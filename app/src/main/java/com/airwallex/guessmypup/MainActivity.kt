package com.airwallex.guessmypup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.airwallex.guessmypup.navigation.GuessMyPupNavHost
import com.airwallex.guessmypup.ui.theme.GuessMyPupTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuessMyPupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GuessMyPupApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GuessMyPupApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    GuessMyPupNavHost(
        navController = navController,
        modifier = modifier
    )
}