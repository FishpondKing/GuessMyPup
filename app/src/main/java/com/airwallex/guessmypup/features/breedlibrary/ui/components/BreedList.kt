package com.airwallex.guessmypup.features.breedlibrary.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.ui.theme.WarmGray

@Composable
fun BreedList(
    breeds: List<DogBreed>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Discover ${breeds.size} amazing dog breeds! 🐾",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = WarmGray,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        items(breeds) { breed ->
            BreedCard(breed = breed)
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}