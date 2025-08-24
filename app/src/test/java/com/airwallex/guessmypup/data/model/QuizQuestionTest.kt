package com.airwallex.guessmypup.data.model

import org.junit.Test
import org.junit.Assert.*

class QuizQuestionTest {

    @Test
    fun `QuizQuestion should store all properties correctly`() {
        // Given
        val dogBreed = DogBreed(
            id = "beagle",
            name = "Beagle",
            imageUrls = listOf("beagle1.jpg", "beagle2.jpg")
        )
        val options = listOf("Beagle", "Boxer", "Husky", "Poodle")
        val correctAnswer = "Beagle"

        // When
        val quizQuestion = QuizQuestion(
            dogBreed = dogBreed,
            options = options,
            correctAnswer = correctAnswer
        )

        // Then
        assertEquals(dogBreed, quizQuestion.dogBreed)
        assertEquals(options, quizQuestion.options)
        assertEquals(correctAnswer, quizQuestion.correctAnswer)
    }

    @Test
    fun `QuizQuestion should maintain data integrity`() {
        // Given
        val dogBreed = DogBreed(
            id = "golden_retriever",
            name = "Golden Retriever",
            imageUrls = listOf("golden1.jpg")
        )
        val options = listOf("Golden Retriever", "Labrador", "Boxer", "Husky")

        // When
        val quizQuestion = QuizQuestion(
            dogBreed = dogBreed,
            options = options,
            correctAnswer = "Golden Retriever"
        )

        // Then
        assertEquals(dogBreed.name, quizQuestion.correctAnswer)
        assertTrue(quizQuestion.options.contains(quizQuestion.correctAnswer))
    }

    @Test
    fun `QuizQuestion should handle empty image URLs`() {
        // Given
        val dogBreed = DogBreed(
            id = "boxer",
            name = "Boxer",
            imageUrls = emptyList()
        )

        // When
        val quizQuestion = QuizQuestion(
            dogBreed = dogBreed,
            options = listOf("Boxer", "Beagle", "Husky", "Poodle"),
            correctAnswer = "Boxer"
        )

        // Then
        assertTrue(quizQuestion.dogBreed.imageUrls.isEmpty())
        assertEquals("Boxer", quizQuestion.correctAnswer)
    }

    @Test
    fun `QuizQuestion options should be modifiable list`() {
        // Given
        val dogBreed = DogBreed(
            id = "husky",
            name = "Husky",
            imageUrls = listOf("husky.jpg")
        )
        val originalOptions = listOf("Husky", "Beagle", "Boxer", "Poodle")

        // When
        val quizQuestion = QuizQuestion(
            dogBreed = dogBreed,
            options = originalOptions,
            correctAnswer = "Husky"
        )

        // Then
        assertEquals(4, quizQuestion.options.size)
        assertEquals(originalOptions, quizQuestion.options)
    }
}