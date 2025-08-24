package com.airwallex.guessmypup.features.quiz.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airwallex.guessmypup.ui.theme.*

data class FeedbackOverlayData(
    val isCorrect: Boolean,
    val currentQuestion: Int,
    val totalQuestions: Int
)

@Composable
fun FeedbackOverlay(
    data: FeedbackOverlayData,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier,
    isSmallScreen: Boolean = false
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val overlayHeight = screenHeight * 0.4f
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(overlayHeight)
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = if (isSmallScreen) 24.dp else 28.dp)
                .padding(vertical = if (isSmallScreen) 28.dp else 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Result feedback section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (isSmallScreen) 16.dp else 20.dp)
            ) {
                // Result feedback icon
                Card(
                    modifier = Modifier.size(if (isSmallScreen) 72.dp else 88.dp),
                    shape = RoundedCornerShape(50),
                    colors = CardDefaults.cardColors(
                        containerColor = if (data.isCorrect) CorrectGreen else ErrorRed
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (data.isCorrect) "🥳" else "🥹",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = if (isSmallScreen) 32.sp else 40.sp
                            )
                        )
                    }
                }
                
                // Result feedback text
                Text(
                    text = if (data.isCorrect) "Correct!" else "Oops! Try again next time",
                    style = if (isSmallScreen) {
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (data.isCorrect) CorrectGreen else ErrorRed
                        )
                    } else {
                        MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (data.isCorrect) CorrectGreen else ErrorRed
                        )
                    },
                    textAlign = TextAlign.Center
                )
                
                // Progress indicator text
                Text(
                    text = "Question ${data.currentQuestion} of ${data.totalQuestions}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = WarmGray,
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center
                )
            }
            
            // Next button
            Button(
                onClick = onNextQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isSmallScreen) 52.dp else 60.dp)
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(if (isSmallScreen) 26.dp else 30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlayfulGreen
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Text(
                    text = if (data.currentQuestion < data.totalQuestions) "Next Question 🐾" else "See Results 🏆",
                    style = if (isSmallScreen) {
                        MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
        }
    }
}