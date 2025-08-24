package com.airwallex.guessmypup.features.breedlibrary.domain

import com.airwallex.guessmypup.data.model.DogBreed

sealed class BreedLibraryUiState {
    object Loading : BreedLibraryUiState()
    
    data class Error(
        val message: String
    ) : BreedLibraryUiState()
    
    data class Loaded(
        val breeds: List<DogBreed>
    ) : BreedLibraryUiState()
}