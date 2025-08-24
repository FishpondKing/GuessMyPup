package com.airwallex.guessmypup.features.quiz.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.ui.theme.*

@Composable
fun AnswerOptionCard(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isWrong: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSmallScreen: Boolean = false,
    showFeedback: Boolean = false
) {
    val backgroundColor = when {
        showFeedback && isCorrect -> CorrectGreen.copy(alpha = 0.2f)
        showFeedback && isWrong -> ErrorRed.copy(alpha = 0.2f)
        isSelected -> PlayfulBlue.copy(alpha = 0.1f)
        else -> MaterialTheme.colorScheme.surface
    }
    
    val borderColor = when {
        showFeedback && isCorrect -> CorrectGreen
        showFeedback && isWrong -> ErrorRed
        isSelected -> PlayfulBlue
        else -> LightBeige
    }

    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isSmallScreen) 16.dp else 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = if (isSmallScreen) {
                    MaterialTheme.typography.bodyLarge.copy(
                        color = DarkBrown,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                } else {
                    MaterialTheme.typography.titleMedium.copy(
                        color = DarkBrown,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                modifier = Modifier.weight(1f)
            )
            
            if (showFeedback) {
                Text(
                    text = if (isCorrect) "✅" else if (isWrong) "❌" else "",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}