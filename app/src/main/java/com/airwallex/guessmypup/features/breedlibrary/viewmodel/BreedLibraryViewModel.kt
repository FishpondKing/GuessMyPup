package com.airwallex.guessmypup.features.breedlibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airwallex.guessmypup.data.repository.DogBreedRepository
import com.airwallex.guessmypup.features.breedlibrary.domain.BreedLibraryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedLibraryViewModel @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<BreedLibraryUiState>(BreedLibraryUiState.Loading)
    val uiState: StateFlow<BreedLibraryUiState> = _uiState.asStateFlow()
    
    init {
        loadBreeds()
    }
    
    fun loadBreeds() {
        viewModelScope.launch {
            _uiState.value = BreedLibraryUiState.Loading
            
            // Since we only have generateQuizQuestions available, let's use that
            // and extract the breeds from the questions
            val result = dogBreedRepository.generateQuizQuestions(20) // Get more breeds
            if (result.isSuccess) {
                val questions = result.getOrThrow()
                val breeds = questions.map { it.dogBreed }.distinctBy { it.id }
                _uiState.value = BreedLibraryUiState.Loaded(breeds)
            } else {
                _uiState.value = BreedLibraryUiState.Error(
                    "Failed to load dog breeds. Please check your internet connection and try again."
                )
            }
        }
    }
}