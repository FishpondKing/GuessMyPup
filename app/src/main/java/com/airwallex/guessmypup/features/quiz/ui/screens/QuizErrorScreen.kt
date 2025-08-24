package com.airwallex.guessmypup.features.quiz.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airwallex.guessmypup.ui.theme.DarkBrown
import com.airwallex.guessmypup.ui.theme.LightBeige
import com.airwallex.guessmypup.ui.theme.PlayfulBlue
import com.airwallex.guessmypup.ui.theme.WarmGray

@Composable
fun QuizErrorScreen(
    message: String,
    onRetry: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.Companion.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        LightBeige
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Error icon
            Box(
                modifier = Modifier.Companion.size(120.dp),
                contentAlignment = Alignment.Companion.Center
            ) {
                Text(
                    text = "💔",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 60.sp
                    )
                )
            }

            Spacer(modifier = Modifier.Companion.height(32.dp))

            // Error title
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Companion.Bold,
                    color = DarkBrown
                ),
                textAlign = TextAlign.Companion.Center
            )

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Error message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = WarmGray
                ),
                textAlign = TextAlign.Companion.Center
            )

            Spacer(modifier = Modifier.Companion.height(32.dp))

            // Retry button
            Button(
                onClick = onRetry,
                modifier = Modifier.Companion
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
                Text(
                    text = "Try Again 🔄",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Companion.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Back to home button
            OutlinedButton(
                onClick = onBackToHome,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .height(56.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
                border = null
            ) {
                Text(
                    text = "Back to Home 🏠",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Companion.Medium,
                        color = DarkBrown
                    )
                )
            }
        }
    }
}