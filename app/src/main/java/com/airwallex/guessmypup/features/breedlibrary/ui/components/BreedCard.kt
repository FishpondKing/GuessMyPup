package com.airwallex.guessmypup.features.breedlibrary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.ui.theme.*

@Composable
fun BreedCard(
    breed: DogBreed,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ) {
                if (breed.imageUrls.isNotEmpty()) {
                    AsyncImage(
                        model = breed.imageUrls.first(),
                        contentDescription = "${breed.name} image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LightBeige),
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
                
                // Breed name overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = breed.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
            
            // Content section
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // ID chip
                Card(
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PlayfulBlue.copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = "ID: ${breed.id}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = PlayfulBlue,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Image count
                if (breed.imageUrls.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📸",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${breed.imageUrls.size} images available",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = WarmGray
                            )
                        )
                    }
                }
            }
        }
    }
}