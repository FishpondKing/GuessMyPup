package com.airwallex.guessmypup.data.mapper

import com.airwallex.guessmypup.data.remote.api.dto.BreedsResponse
import org.junit.Test
import org.junit.Assert.*

class DogBreedMapperTest {

    @Test
    fun `mapBreedResponseToBreeds should handle breeds with sub-breeds`() {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf(
                "bulldog" to listOf("boston", "english", "french"),
                "retriever" to listOf("chesapeake", "curly", "flatcoated", "golden")
            ),
            status = "success"
        )
        val breedImages = mapOf(
            "bulldog_boston" to listOf("image1.jpg", "image2.jpg"),
            "retriever_chesapeake" to listOf("image3.jpg", "image4.jpg")
        )

        // When
        val result = DogBreedMapper.mapBreedResponseToBreeds(breedsResponse, breedImages)

        // Then
        assertEquals(2, result.size)
        
        val bulldog = result[0]
        assertEquals("bulldog_boston", bulldog.id)
        assertEquals("Boston Bulldog", bulldog.name)
        assertEquals(listOf("image1.jpg", "image2.jpg"), bulldog.imageUrls)
        
        val retriever = result[1]
        assertEquals("retriever_chesapeake", retriever.id)
        assertEquals("Chesapeake Retriever", retriever.name)
        assertEquals(listOf("image3.jpg", "image4.jpg"), retriever.imageUrls)
    }

    @Test
    fun `mapBreedResponseToBreeds should handle breeds without sub-breeds`() {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf(
                "beagle" to emptyList(),
                "boxer" to emptyList()
            ),
            status = "success"
        )
        val breedImages = mapOf(
            "beagle" to listOf("beagle1.jpg", "beagle2.jpg"),
            "boxer" to listOf("boxer1.jpg", "boxer2.jpg")
        )

        // When
        val result = DogBreedMapper.mapBreedResponseToBreeds(breedsResponse, breedImages)

        // Then
        assertEquals(2, result.size)
        
        val beagle = result[0]
        assertEquals("beagle", beagle.id)
        assertEquals("Beagle", beagle.name)
        assertEquals(listOf("beagle1.jpg", "beagle2.jpg"), beagle.imageUrls)
        
        val boxer = result[1]
        assertEquals("boxer", boxer.id)
        assertEquals("Boxer", boxer.name)
        assertEquals(listOf("boxer1.jpg", "boxer2.jpg"), boxer.imageUrls)
    }

    @Test
    fun `mapBreedResponseToBreeds should handle multi-word breed names`() {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf("german shepherd" to emptyList()),
            status = "success"
        )
        val breedImages = mapOf("german shepherd" to listOf("gs1.jpg"))

        // When
        val result = DogBreedMapper.mapBreedResponseToBreeds(breedsResponse, breedImages)

        // Then
        assertEquals(1, result.size)
        val germanShepherd = result[0]
        assertEquals("german_shepherd", germanShepherd.id)
        assertEquals("German Shepherd", germanShepherd.name)
        assertEquals(listOf("gs1.jpg"), germanShepherd.imageUrls)
    }

    @Test
    fun `mapBreedResponseToBreeds should handle missing images`() {
        // Given
        val breedsResponse = BreedsResponse(
            message = mapOf("beagle" to emptyList()),
            status = "success"
        )
        val breedImages = emptyMap<String, List<String>>()

        // When
        val result = DogBreedMapper.mapBreedResponseToBreeds(breedsResponse, breedImages)

        // Then
        assertEquals(1, result.size)
        val beagle = result[0]
        assertEquals("beagle", beagle.id)
        assertEquals("Beagle", beagle.name)
        assertTrue(beagle.imageUrls.isEmpty())
    }

    @Test
    fun `mapBreedResponseToBreeds should limit to 20 breeds`() {
        // Given
        val breeds = (1..25).associate { "breed$it" to emptyList<String>() }
        val breedsResponse = BreedsResponse(message = breeds, status = "success")
        val breedImages = emptyMap<String, List<String>>()

        // When
        val result = DogBreedMapper.mapBreedResponseToBreeds(breedsResponse, breedImages)

        // Then
        assertEquals(20, result.size)
    }
}