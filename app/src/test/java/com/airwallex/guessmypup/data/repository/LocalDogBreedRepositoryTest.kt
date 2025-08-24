package com.airwallex.guessmypup.data.repository

import com.airwallex.guessmypup.data.model.QuizQuestion
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class LocalDogBreedRepositoryTest {

    private lateinit var repository: LocalDogBreedRepository

    @Before
    fun setup() {
        repository = LocalDogBreedRepository()
    }

    @Test
    fun `generateQuizQuestions should return requested number of questions`() = runTest {
        // When
        val result = repository.generateQuizQuestions(5)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        assertEquals(5, questions.size)
    }

    @Test
    fun `generateQuizQuestions should return valid quiz structure`() = runTest {
        // When
        val result = repository.generateQuizQuestions(3)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        
        questions.forEach { question ->
            // Each question should have a dog breed
            assertNotNull(question.dogBreed)
            assertNotNull(question.dogBreed.id)
            assertNotNull(question.dogBreed.name)
            assertFalse(question.dogBreed.imageUrls.isEmpty())
            
            // Each question should have 4 options
            assertEquals(4, question.options.size)
            
            // Correct answer should be in options
            assertTrue(question.options.contains(question.correctAnswer))
            assertEquals(question.dogBreed.name, question.correctAnswer)
        }
    }

    @Test
    fun `generateQuizQuestions should have different questions each time`() = runTest {
        // When
        val result1 = repository.generateQuizQuestions(3)
        val result2 = repository.generateQuizQuestions(3)

        // Then
        assertTrue(result1.isSuccess)
        assertTrue(result2.isSuccess)
        
        val questions1 = result1.getOrNull()!!
        val questions2 = result2.getOrNull()!!
        
        val breeds1 = questions1.map { it.dogBreed.name }.toSet()
        val breeds2 = questions2.map { it.dogBreed.name }.toSet()
        
        // Due to shuffling, results should potentially be different
        // (This test might occasionally fail due to randomness, but it's very unlikely)
        assertNotEquals(breeds1, breeds2)
    }

    @Test
    fun `generateQuizQuestions should handle request for more questions than available breeds`() = runTest {
        // When
        val result = repository.generateQuizQuestions(20)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        assertTrue(questions.size <= 10) // LocalDogBreedRepository has 10 breeds
    }

    @Test
    fun `generateQuizQuestions should have unique options for each question`() = runTest {
        // When
        val result = repository.generateQuizQuestions(4)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        
        questions.forEach { question ->
            assertEquals(4, question.options.distinct().size) // All options should be unique
        }
    }

    @Test
    fun `generateQuizQuestions should have correct answer matching dog breed name`() = runTest {
        // When
        val result = repository.generateQuizQuestions(5)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        
        questions.forEach { question ->
            assertEquals(question.dogBreed.name, question.correctAnswer)
        }
    }
}