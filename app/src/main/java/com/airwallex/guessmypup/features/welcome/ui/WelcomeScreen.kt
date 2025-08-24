package com.airwallex.guessmypup.features.welcome.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.features.welcome.ui.components.WelcomeHeroSection
import com.airwallex.guessmypup.features.welcome.ui.components.WelcomeActionButtons
import com.airwallex.guessmypup.ui.theme.*

@Composable
fun WelcomeScreen(
    onStartQuiz: () -> Unit,
    onLearnBreeds: () -> Unit,
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Hero section
            WelcomeHeroSection(
                modifier = Modifier.weight(1f)
            )
            
            // Action buttons
            WelcomeActionButtons(
                onStartQuiz = onStartQuiz,
                onLearnBreeds = onLearnBreeds
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}