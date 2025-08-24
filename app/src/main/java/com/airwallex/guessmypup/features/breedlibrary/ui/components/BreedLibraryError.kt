package com.airwallex.guessmypup.features.breedlibrary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.ui.theme.DarkBrown
import com.airwallex.guessmypup.ui.theme.PlayfulBlue
import com.airwallex.guessmypup.ui.theme.WarmGray

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            modifier = Modifier.Companion.padding(32.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "💔",
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.Companion.height(16.dp))

            Text(
                text = "Failed to load breeds",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Companion.Bold,
                    color = DarkBrown
                ),
                textAlign = TextAlign.Companion.Center
            )

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = WarmGray
                ),
                textAlign = TextAlign.Companion.Center
            )

            Spacer(modifier = Modifier.Companion.height(24.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PlayfulBlue
                )
            ) {
                Text("Try Again 🔄")
            }
        }
    }
}