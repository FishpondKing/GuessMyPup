package com.airwallex.guessmypup.data.repository

import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.data.model.QuizQuestion
import com.airwallex.guessmypup.data.remote.api.DogApiService
import com.airwallex.guessmypup.data.remote.api.dto.BreedsResponse
import com.airwallex.guessmypup.data.remote.api.dto.BreedImagesResponse
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Response

class DogBreedRepositoryImplTest {

    private lateinit var repository: DogBreedRepositoryImpl
    private val mockApiService = mockk<DogApiService>()

    @Before
    fun setup() {
        repository = DogBreedRepositoryImpl(mockApiService)
    }

    @Test
    fun `generateQuizQuestions should return success with valid questions`() = runTest {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf(
                "beagle" to emptyList(),
                "boxer" to emptyList(),
                "husky" to emptyList(),
                "poodle" to emptyList(),
                "labrador" to emptyList()
            ),
            status = "success"
        )
        val imagesResponse = BreedImagesResponse(
            message = listOf("image1.jpg", "image2.jpg"),
            status = "success"
        )

        coEvery { mockApiService.getAllBreeds() } returns Response.success(breedsResponse)
        coEvery { mockApiService.getBreedImages(any()) } returns Response.success(imagesResponse)

        // When
        val result = repository.generateQuizQuestions(3)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        assertEquals(3, questions.size)
        
        questions.forEach { question ->
            assertNotNull(question.dogBreed)
            assertEquals(4, question.options.size)
            assertTrue(question.options.contains(question.correctAnswer))
            assertEquals(question.dogBreed.name, question.correctAnswer)
        }
    }

    @Test
    fun `generateQuizQuestions should handle API failure`() = runTest {
        // Given
        coEvery { mockApiService.getAllBreeds() } returns Response.error(404, mockk(relaxed = true))

        // When
        val result = repository.generateQuizQuestions(5)

        // Then
        assertTrue(result.isFailure)
        assertNotNull(result.exceptionOrNull())
    }

    @Test
    fun `generateQuizQuestions should handle breeds with sub-breeds`() = runTest {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf(
                "bulldog" to listOf("boston", "english"),
                "retriever" to listOf("golden", "labrador"),
                "beagle" to emptyList()
            ),
            status = "success"
        )
        val imagesResponse = BreedImagesResponse(
            message = listOf("image1.jpg"),
            status = "success"
        )

        coEvery { mockApiService.getAllBreeds() } returns Response.success(breedsResponse)
        coEvery { mockApiService.getSubBreedImages(any(), any()) } returns Response.success(imagesResponse)
        coEvery { mockApiService.getBreedImages(any()) } returns Response.success(imagesResponse)

        // When
        val result = repository.generateQuizQuestions(2)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        assertEquals(2, questions.size)
        
        // Verify sub-breed names are formatted correctly
        val breedNames = questions.map { it.dogBreed.name }
        assertTrue(breedNames.any { it.contains("Boston") || it.contains("Golden") })
    }

    @Test
    fun `generateQuizQuestions should handle insufficient breeds`() = runTest {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf("beagle" to emptyList()),
            status = "success"
        )
        val imagesResponse = BreedImagesResponse(
            message = listOf("image1.jpg"),
            status = "success"
        )

        coEvery { mockApiService.getAllBreeds() } returns Response.success(breedsResponse)
        coEvery { mockApiService.getBreedImages(any()) } returns Response.success(imagesResponse)

        // When
        val result = repository.generateQuizQuestions(5)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        assertEquals(1, questions.size) // Only 1 breed available
    }

    @Test
    fun `generateQuizQuestions should handle image fetch failure gracefully`() = runTest {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf(
                "beagle" to emptyList(),
                "boxer" to emptyList()
            ),
            status = "success"
        )

        coEvery { mockApiService.getAllBreeds() } returns Response.success(breedsResponse)
        coEvery { mockApiService.getBreedImages(any()) } returns Response.error(404, mockk(relaxed = true))

        // When
        val result = repository.generateQuizQuestions(2)

        // Then
        assertTrue(result.isSuccess)
        val questions = result.getOrNull()!!
        assertEquals(2, questions.size)
        
        // Images should be empty but questions should still be generated
        questions.forEach { question ->
            assertTrue(question.dogBreed.imageUrls.isEmpty())
        }
    }
}