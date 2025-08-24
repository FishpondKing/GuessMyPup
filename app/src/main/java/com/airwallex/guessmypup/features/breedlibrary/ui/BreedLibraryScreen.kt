package com.airwallex.guessmypup.features.breedlibrary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.airwallex.guessmypup.features.breedlibrary.ui.components.LoadingContent
import com.airwallex.guessmypup.features.breedlibrary.ui.components.ErrorContent
import com.airwallex.guessmypup.features.breedlibrary.ui.components.BreedList
import com.airwallex.guessmypup.features.breedlibrary.domain.BreedLibraryUiState
import com.airwallex.guessmypup.ui.theme.*
import com.airwallex.guessmypup.features.breedlibrary.viewmodel.BreedLibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedLibraryScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BreedLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        LightBeige
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top app bar
            TopAppBar(
                title = {
                    Text(
                        text = "Dog Breed Library",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkBrown
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f)
                )
            )
            
            // Content
            when (val state = uiState) {
                is BreedLibraryUiState.Loading -> {
                    LoadingContent()
                }
                is BreedLibraryUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.loadBreeds() }
                    )
                }
                is BreedLibraryUiState.Loaded -> {
                    BreedList(breeds = state.breeds)
                }
            }
        }
    }
}