package com.airwallex.guessmypup.features.breedlibrary.viewmodel

import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.data.model.QuizQuestion
import com.airwallex.guessmypup.data.repository.DogBreedRepository
import com.airwallex.guessmypup.features.breedlibrary.domain.BreedLibraryUiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import app.cash.turbine.test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedLibraryViewModelTest {

    private lateinit var viewModel: BreedLibraryViewModel
    private val mockRepository = mockk<DogBreedRepository>()
    private val testDispatcher = UnconfinedTestDispatcher()

    private val sampleBreeds = listOf(
        DogBreed(
            id = "beagle",
            name = "Beagle",
            imageUrls = listOf("beagle1.jpg", "beagle2.jpg")
        ),
        DogBreed(
            id = "boxer",
            name = "Boxer",
            imageUrls = listOf("boxer1.jpg", "boxer2.jpg")
        ),
        DogBreed(
            id = "husky",
            name = "Husky",
            imageUrls = listOf("husky1.jpg", "husky2.jpg")
        )
    )

    private val sampleQuestions = listOf(
        QuizQuestion(
            dogBreed = sampleBreeds[0],
            options = listOf("Beagle", "Boxer", "Husky", "Poodle"),
            correctAnswer = "Beagle"
        ),
        QuizQuestion(
            dogBreed = sampleBreeds[1],
            options = listOf("Beagle", "Boxer", "Husky", "Poodle"),
            correctAnswer = "Boxer"
        ),
        QuizQuestion(
            dogBreed = sampleBreeds[2],
            options = listOf("Beagle", "Boxer", "Husky", "Poodle"),
            correctAnswer = "Husky"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should be Loading then Loaded on successful breed loading`() = runTest {
        // Given
        coEvery { mockRepository.generateQuizQuestions(20) } returns Result.success(sampleQuestions)

        // When
        viewModel = BreedLibraryViewModel(mockRepository)

        // Then
        viewModel.uiState.test {
            val loadedState = awaitItem() as BreedLibraryUiState.Loaded
            assertEquals(3, loadedState.breeds.size)
            assertEquals("Beagle", loadedState.breeds[0].name)
            assertEquals("Boxer", loadedState.breeds[1].name)
            assertEquals("Husky", loadedState.breeds[2].name)
        }
    }

    @Test
    fun `initial state should be Loading then Error on failed breed loading`() = runTest {
        // Given
        coEvery { mockRepository.generateQuizQuestions(20) } returns Result.failure(Exception("Network error"))

        // When
        viewModel = BreedLibraryViewModel(mockRepository)

        // Then
        viewModel.uiState.test {
            val errorState = awaitItem() as BreedLibraryUiState.Error
            assertTrue(errorState.message.contains("Failed to load dog breeds"))
        }
    }

    @Test
    fun `loadBreeds should reload breeds from repository`() = runTest {
        // Given
        coEvery { mockRepository.generateQuizQuestions(20) } returns Result.success(sampleQuestions)
        viewModel = BreedLibraryViewModel(mockRepository)

        // When
        viewModel.loadBreeds()

        // Then
        coVerify(exactly = 2) { mockRepository.generateQuizQuestions(20) } // Initial load + manual reload
    }

    @Test
    fun `loadBreeds should extract unique breeds from quiz questions`() = runTest {
        // Given - Create questions with duplicate breeds
        val questionsWithDuplicates = listOf(
            QuizQuestion(
                dogBreed = sampleBreeds[0], // Beagle
                options = listOf("Beagle", "Boxer"),
                correctAnswer = "Beagle"
            ),
            QuizQuestion(
                dogBreed = sampleBreeds[0], // Beagle again
                options = listOf("Beagle", "Boxer"),
                correctAnswer = "Beagle"
            ),
            QuizQuestion(
                dogBreed = sampleBreeds[1], // Boxer
                options = listOf("Beagle", "Boxer"),
                correctAnswer = "Boxer"
            )
        )
        
        coEvery { mockRepository.generateQuizQuestions(20) } returns Result.success(questionsWithDuplicates)

        // When
        viewModel = BreedLibraryViewModel(mockRepository)

        // Then
        viewModel.uiState.test {
            val loadedState = awaitItem() as BreedLibraryUiState.Loaded
            assertEquals(2, loadedState.breeds.size) // Should have unique breeds only
            
            val breedIds = loadedState.breeds.map { it.id }
            assertTrue(breedIds.contains("beagle"))
            assertTrue(breedIds.contains("boxer"))
        }
    }

    @Test
    fun `loadBreeds should handle empty result gracefully`() = runTest {
        // Given
        coEvery { mockRepository.generateQuizQuestions(20) } returns Result.success(emptyList())

        // When
        viewModel = BreedLibraryViewModel(mockRepository)

        // Then
        viewModel.uiState.test {
            val loadedState = awaitItem() as BreedLibraryUiState.Loaded
            assertEquals(0, loadedState.breeds.size)
        }
    }

    @Test
    fun `viewModel should request 20 questions to get maximum breed variety`() = runTest {
        // Given
        coEvery { mockRepository.generateQuizQuestions(20) } returns Result.success(sampleQuestions)

        // When
        viewModel = BreedLibraryViewModel(mockRepository)

        // Then
        coVerify { mockRepository.generateQuizQuestions(20) }
    }
}