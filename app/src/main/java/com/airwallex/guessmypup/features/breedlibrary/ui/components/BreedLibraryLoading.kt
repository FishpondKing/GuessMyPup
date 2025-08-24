package com.airwallex.guessmypup.features.breedlibrary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.ui.theme.DarkBrown
import com.airwallex.guessmypup.ui.theme.PlayfulBlue

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier.Companion
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.Companion.size(60.dp),
                color = PlayfulBlue,
                strokeWidth = 4.dp
            )

            Spacer(modifier = Modifier.Companion.height(24.dp))

            Text(
                text = "Loading breed library...",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = DarkBrown
                ),
                textAlign = TextAlign.Companion.Center
            )
        }
    }
}