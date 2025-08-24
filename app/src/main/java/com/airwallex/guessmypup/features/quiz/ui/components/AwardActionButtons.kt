package com.airwallex.guessmypup.features.quiz.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.ui.theme.*

@Composable
fun AwardActionButtons(
    onPlayAgain: () -> Unit,
    onReviewBreeds: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Play Again button
        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PlayfulBlue
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🔄",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Play Again",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        
        // Review Breeds button
        OutlinedButton(
            onClick = onReviewBreeds,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(
                width = 2.dp
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = PlayfulGreen
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📖",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Review Breeds",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        
        // Back to Home button
        TextButton(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "🏠 Back to Home",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = WarmGray
                )
            )
        }
    }
}