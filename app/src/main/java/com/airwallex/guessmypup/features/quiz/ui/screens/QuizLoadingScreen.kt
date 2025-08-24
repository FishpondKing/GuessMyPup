package com.airwallex.guessmypup.features.quiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.ui.theme.*

@Composable
fun QuizLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        LightBeige
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Loading animation
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                color = PlayfulBlue,
                strokeWidth = 6.dp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Loading text
            Text(
                text = "Loading dog breeds...",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = DarkBrown
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Getting the cutest pups ready for you! 🐾",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = WarmGray
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}