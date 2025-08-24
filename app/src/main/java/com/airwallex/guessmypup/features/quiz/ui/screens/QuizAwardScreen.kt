package com.airwallex.guessmypup.features.quiz.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.airwallex.guessmypup.features.quiz.ui.components.ConfettiOverlay
import com.airwallex.guessmypup.features.quiz.ui.components.AwardScoreDisplay
import com.airwallex.guessmypup.features.quiz.ui.components.AwardScoreDisplayData
import com.airwallex.guessmypup.features.quiz.ui.components.AwardActionButtons
import com.airwallex.guessmypup.ui.theme.*

@Composable
fun QuizAwardScreen(
    score: Int,
    totalQuestions: Int,
    onPlayAgain: () -> Unit,
    onReviewBreeds: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showContent by remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }
    
    val percentage = (score.toFloat() / totalQuestions.toFloat() * 100).toInt()
    
    // Calculate score display data
    val scoreDisplayData = remember(percentage, score, totalQuestions) {
        val (trophy, message, color) = when {
            percentage >= 90 -> Triple("🏆", "Outstanding!", CorrectGreen)
            percentage >= 70 -> Triple("🥇", "Great job!", PlayfulBlue)
            percentage >= 50 -> Triple("🥈", "Good work!", PlayfulGreen)
            else -> Triple("🥉", "Keep learning!", Warning)
        }
        
        val encouragement = when {
            percentage >= 90 -> "You're a true dog breed expert! 🐕‍🦺"
            percentage >= 70 -> "Impressive knowledge! Keep it up! 🐕"
            percentage >= 50 -> "Good progress! Practice makes perfect! 🐶"
            else -> "Every expert was once a beginner! 🐾"
        }
        
        AwardScoreDisplayData(
            trophy = trophy,
            message = message,
            color = color,
            score = score,
            totalQuestions = totalQuestions,
            percentage = percentage,
            encouragement = encouragement
        )
    }
    
    LaunchedEffect(Unit) {
        delay(300)
        showContent = true
        delay(500)
        showConfetti = true
    }
    
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
            
            AnimatedVisibility(
                visible = showContent,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) + fadeIn()
            ) {
                AwardScoreDisplay(
                    data = scoreDisplayData,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Action buttons
            AnimatedVisibility(
                visible = showContent,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) + fadeIn()
            ) {
                AwardActionButtons(
                    onPlayAgain = onPlayAgain,
                    onReviewBreeds = onReviewBreeds,
                    onBackToHome = onBackToHome
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Confetti animation
        AnimatedVisibility(
            visible = showConfetti && percentage >= 70,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            ConfettiOverlay()
        }
    }
}