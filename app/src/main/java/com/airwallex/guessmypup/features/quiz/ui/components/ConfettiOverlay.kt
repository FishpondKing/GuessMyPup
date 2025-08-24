package com.airwallex.guessmypup.features.quiz.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ConfettiOverlay(
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(3000)
        visible = false
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Simple confetti representation using text
            val confettiItems = listOf("🎉", "🎊", "⭐", "✨", "🏆", "🥇", "🎈")
            
            confettiItems.forEachIndexed { index, emoji ->
                var offsetY by remember { mutableStateOf(0f) }
                
                LaunchedEffect(Unit) {
                    offsetY = 1000f
                }
                
                val animatedOffsetY by animateFloatAsState(
                    targetValue = offsetY,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 2000 + (index * 200),
                            easing = EaseOut
                        ),
                        repeatMode = RepeatMode.Restart
                    )
                )
                
                Text(
                    text = emoji,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .offset(
                            x = (50 + index * 40).dp,
                            y = animatedOffsetY.dp
                        )
                )
            }
        }
    }
}