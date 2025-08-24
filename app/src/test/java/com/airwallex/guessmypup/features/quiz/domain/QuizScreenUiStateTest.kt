package com.airwallex.guessmypup.features.quiz.domain

import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.data.model.QuizQuestion
import org.junit.Test
import org.junit.Assert.*

class QuizScreenUiStateTest {

    private val sampleQuestion = QuizQuestion(
        dogBreed = DogBreed(
            id = "beagle",
            name = "Beagle",
            imageUrls = listOf("beagle1.jpg", "beagle2.jpg")
        ),
        options = listOf("Beagle", "Boxer", "Husky", "Poodle"),
        correctAnswer = "Beagle"
    )

    private val sampleQuestions = listOf(sampleQuestion)

    @Test
    fun `Playing state should calculate current question number correctly`() {
        // Given
        val playingState = QuizScreenUiState.Playing(
            questions = sampleQuestions,
            currentQuestionIndex = 0,
            score = 0,
            isAnswerSelected = false
        )

        // Then
        assertEquals(1, playingState.currentQuestionNumber) // 1-indexed
    }

    @Test
    fun `Playing state should return current question correctly`() {
        // Given
        val playingState = QuizScreenUiState.Playing(
            questions = sampleQuestions,
            currentQuestionIndex = 0,
            score = 0,
            isAnswerSelected = false
        )

        // Then
        assertEquals(sampleQuestion, playingState.currentQuestion)
        assertEquals("Beagle", playingState.currentQuestion?.correctAnswer)
    }

    @Test
    fun `Playing state should return null for current question when index is out of bounds`() {
        // Given
        val playingState = QuizScreenUiState.Playing(
            questions = sampleQuestions,
            currentQuestionIndex = 5, // Out of bounds
            score = 0,
            isAnswerSelected = false
        )

        // Then
        assertNull(playingState.currentQuestion)
    }

    @Test
    fun `Playing state should return correct total questions`() {
        // Given
        val multipleQuestions = listOf(sampleQuestion, sampleQuestion, sampleQuestion)
        val playingState = QuizScreenUiState.Playing(
            questions = multipleQuestions,
            currentQuestionIndex = 1,
            score = 2,
            isAnswerSelected = true
        )

        // Then
        assertEquals(3, playingState.totalQuestions)
    }

    @Test
    fun `Award state should store score and total questions correctly`() {
        // Given
        val awardState = QuizScreenUiState.Award(
            score = 8,
            totalQuestions = 10
        )

        // Then
        assertEquals(8, awardState.score)
        assertEquals(10, awardState.totalQuestions)
    }

    @Test
    fun `Error state should store error message correctly`() {
        // Given
        val errorMessage = "Network connection failed"
        val errorState = QuizScreenUiState.Error(errorMessage)

        // Then
        assertEquals(errorMessage, errorState.message)
    }

    @Test
    fun `Playing state copy should maintain immutability`() {
        // Given
        val originalState = QuizScreenUiState.Playing(
            questions = sampleQuestions,
            currentQuestionIndex = 0,
            score = 0,
            isAnswerSelected = false
        )

        // When
        val copiedState = originalState.copy(score = 1, isAnswerSelected = true)

        // Then
        assertEquals(0, originalState.score) // Original unchanged
        assertFalse(originalState.isAnswerSelected) // Original unchanged
        
        assertEquals(1, copiedState.score) // Copy changed
        assertTrue(copiedState.isAnswerSelected) // Copy changed
        
        // Other fields remain the same
        assertEquals(originalState.questions, copiedState.questions)
        assertEquals(originalState.currentQuestionIndex, copiedState.currentQuestionIndex)
    }
}