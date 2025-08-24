package com.airwallex.guessmypup.features.quiz.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airwallex.guessmypup.ui.theme.*

data class AwardScoreDisplayData(
    val trophy: String,
    val message: String,
    val color: androidx.compose.ui.graphics.Color,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Int,
    val encouragement: String
)

@Composable
fun AwardScoreDisplay(
    data: AwardScoreDisplayData,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Trophy card
        Card(
            modifier = Modifier
                .size(180.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(90.dp)
                ),
            shape = RoundedCornerShape(90.dp),
            colors = CardDefaults.cardColors(
                containerColor = data.color.copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = data.trophy,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 80.sp
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Success message
        Text(
            text = data.message,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = data.color,
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Score display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(20.dp)
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You got",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = WarmGray
                    )
                )
                
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${data.score}",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = PlayfulBlue,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        text = "out of ${data.totalQuestions}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = DarkBrown
                        )
                    )
                }
                
                Text(
                    text = "(${data.percentage}% correct)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = WarmGray
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Encouraging message
        Text(
            text = data.encouragement,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = DarkBrown
            ),
            textAlign = TextAlign.Center
        )
    }
}