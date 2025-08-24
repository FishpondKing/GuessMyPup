package com.airwallex.guessmypup.features.quiz.viewmodel

import app.cash.turbine.test
import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.data.model.QuizQuestion
import com.airwallex.guessmypup.data.repository.DogBreedRepository
import com.airwallex.guessmypup.features.quiz.domain.AnswerSelectedEventData
import com.airwallex.guessmypup.features.quiz.domain.QuizEvent
import com.airwallex.guessmypup.features.quiz.domain.QuizScreenUiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel
    private val mockRepository = mockk<DogBreedRepository>()
    private val testDispatcher = UnconfinedTestDispatcher()

    private val sampleQuestions = listOf(
        QuizQuestion(
            dogBreed = DogBreed(
                id = "beagle",
                name = "Beagle",
                imageUrls = listOf("beagle.jpg")
            ),
            options = listOf("Beagle", "Boxer", "Husky", "Poodle"),
            correctAnswer = "Beagle"
        ),
        QuizQuestion(
            dogBreed = DogBreed(
                id = "boxer",
                name = "Boxer",
                imageUrls = listOf("boxer.jpg")
            ),
            options = listOf("Beagle", "Boxer", "Husky", "Poodle"),
            correctAnswer = "Boxer"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { mockRepository.generateQuizQuestions(any()) } returns Result.success(sampleQuestions)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should be Loading then Playing on successful quiz generation`() = runTest {
        // When
        viewModel = QuizViewModel(mockRepository)

        // Then
        viewModel.uiState.test {
            val playingState = awaitItem() as QuizScreenUiState.Playing
            assertEquals(sampleQuestions, playingState.questions)
            assertEquals(0, playingState.currentQuestionIndex)
            assertEquals(0, playingState.score)
            assertFalse(playingState.isAnswerSelected)
        }
    }

    @Test
    fun `initial state should be Loading then Error on failed quiz generation`() = runTest {
        // Given
        coEvery { mockRepository.generateQuizQuestions(any()) } returns Result.failure(Exception("Network error"))

        // When
        viewModel = QuizViewModel(mockRepository)

        // Then
        viewModel.uiState.test {
            val errorState = awaitItem() as QuizScreenUiState.Error
            assertTrue(errorState.message.contains("Failed to load quiz questions"))
        }
    }

    @Test
    fun `selectAnswer with correct answer should update score and emit event`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        viewModel.uiState.test {
            skipItems(1) // Skip initial state
            
            // When
            viewModel.selectAnswer("Beagle")
            
            // Then
            val updatedState = awaitItem() as QuizScreenUiState.Playing
            assertTrue(updatedState.isAnswerSelected)
            assertEquals(1, updatedState.score)
        }

        // Check event emission
        viewModel.events.test {
            val event = awaitItem() as QuizEvent.AnswerSelected
            assertEquals("Beagle", event.data.selectAnswer)
            assertTrue(event.data.isCorrect)
            assertEquals(1, event.data.currentQuestion)
            assertEquals(2, event.data.totalQuestions)
        }
    }

    @Test
    fun `selectAnswer with wrong answer should not update score but still emit event`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        viewModel.uiState.test {
            skipItems(1) // Skip initial state
            
            // When
            viewModel.selectAnswer("Boxer") // Wrong answer for first question
            
            // Then
            val updatedState = awaitItem() as QuizScreenUiState.Playing
            assertTrue(updatedState.isAnswerSelected)
            assertEquals(0, updatedState.score) // Score unchanged
        }

        viewModel.events.test {
            val event = awaitItem() as QuizEvent.AnswerSelected
            assertEquals("Boxer", event.data.selectAnswer)
            assertFalse(event.data.isCorrect)
        }
    }

    @Test
    fun `selectAnswer should be ignored if answer already selected`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        viewModel.uiState.test {
            skipItems(1) // Skip initial state
            
            // When - Select first answer
            viewModel.selectAnswer("Beagle")
            awaitItem() // Wait for state update
            
            // Then - Try to select again
            viewModel.selectAnswer("Boxer")
            expectNoEvents() // Should not emit another state change
        }
    }

    @Test
    fun `nextQuestion should advance to next question`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        viewModel.uiState.test {
            skipItems(1) // Skip initial state
            
            viewModel.selectAnswer("Beagle")
            awaitItem() // Wait for answer selection
            
            // When
            viewModel.nextQuestion()
            
            // Then
            val nextState = awaitItem() as QuizScreenUiState.Playing
            assertEquals(1, nextState.currentQuestionIndex)
            assertFalse(nextState.isAnswerSelected)
            assertEquals("Boxer", nextState.currentQuestion?.correctAnswer)
        }
    }

    @Test
    fun `nextQuestion on last question should transition to Award state`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        viewModel.uiState.test {
            skipItems(1) // Skip initial state
            
            // Answer first question
            viewModel.selectAnswer("Beagle")
            awaitItem()
            viewModel.nextQuestion()
            awaitItem()
            
            // Answer second question
            viewModel.selectAnswer("Boxer")
            awaitItem()
            
            // When - Move past last question
            viewModel.nextQuestion()
            
            // Then
            val awardState = awaitItem() as QuizScreenUiState.Award
            assertEquals(2, awardState.score) // Both answers were correct
            assertEquals(2, awardState.totalQuestions)
        }
    }

    @Test
    fun `restartQuiz should start new quiz with same number of questions`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        // When
        viewModel.restartQuiz()
        
        // Then
        coVerify { mockRepository.generateQuizQuestions(10) } // DEFAULT_NUMBER_OF_QUESTIONS
    }

    @Test
    fun `startNewQuiz should use custom number of questions`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        // When
        viewModel.startNewQuiz(5)
        
        // Then
        coVerify { mockRepository.generateQuizQuestions(5) }
    }

    @Test
    fun `currentQuestion should return correct question based on index`() = runTest {
        // Given
        viewModel = QuizViewModel(mockRepository)
        
        viewModel.uiState.test {
            val playingState = awaitItem() as QuizScreenUiState.Playing
            
            // Then
            assertEquals("Beagle", playingState.currentQuestion?.correctAnswer)
            assertEquals(1, playingState.currentQuestionNumber)
            assertEquals(2, playingState.totalQuestions)
        }
    }
}