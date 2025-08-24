package com.airwallex.guessmypup.features.welcome.ui.components

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

@Composable
fun WelcomeHeroSection(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Hero dog image placeholder
        Card(
            modifier = Modifier
                .size(200.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(100.dp)
                ),
            shape = RoundedCornerShape(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🐕",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 80.sp
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // App title
        Text(
            text = "Guess My Pup",
            style = MaterialTheme.typography.displayLarge.copy(
                color = PlayfulBlue,
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Test your knowledge of dog breeds!",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = WarmGray
            ),
            textAlign = TextAlign.Center
        )
    }
}