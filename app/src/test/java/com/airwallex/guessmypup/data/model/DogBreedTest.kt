package com.airwallex.guessmypup.data.model

import org.junit.Test
import org.junit.Assert.*

class DogBreedTest {

    @Test
    fun `DogBreed should store all properties correctly`() {
        // Given
        val id = "golden_retriever"
        val name = "Golden Retriever"
        val imageUrls = listOf("golden1.jpg", "golden2.jpg", "golden3.jpg")

        // When
        val dogBreed = DogBreed(
            id = id,
            name = name,
            imageUrls = imageUrls
        )

        // Then
        assertEquals(id, dogBreed.id)
        assertEquals(name, dogBreed.name)
        assertEquals(imageUrls, dogBreed.imageUrls)
    }

    @Test
    fun `DogBreed should handle empty image URLs`() {
        // Given
        val dogBreed = DogBreed(
            id = "beagle",
            name = "Beagle",
            imageUrls = emptyList()
        )

        // Then
        assertTrue(dogBreed.imageUrls.isEmpty())
        assertEquals("beagle", dogBreed.id)
        assertEquals("Beagle", dogBreed.name)
    }

    @Test
    fun `DogBreed should handle single image URL`() {
        // Given
        val dogBreed = DogBreed(
            id = "boxer",
            name = "Boxer",
            imageUrls = listOf("boxer_single.jpg")
        )

        // Then
        assertEquals(1, dogBreed.imageUrls.size)
        assertEquals("boxer_single.jpg", dogBreed.imageUrls.first())
    }

    @Test
    fun `DogBreed should handle multiple image URLs`() {
        // Given
        val imageUrls = listOf(
            "husky1.jpg",
            "husky2.jpg",
            "husky3.jpg",
            "husky4.jpg"
        )
        val dogBreed = DogBreed(
            id = "husky",
            name = "Husky",
            imageUrls = imageUrls
        )

        // Then
        assertEquals(4, dogBreed.imageUrls.size)
        assertEquals(imageUrls, dogBreed.imageUrls)
    }

    @Test
    fun `DogBreed should handle sub-breed IDs`() {
        // Given
        val dogBreed = DogBreed(
            id = "bulldog_french",
            name = "French Bulldog",
            imageUrls = listOf("french_bulldog.jpg")
        )

        // Then
        assertEquals("bulldog_french", dogBreed.id)
        assertEquals("French Bulldog", dogBreed.name)
        assertTrue(dogBreed.id.contains("_"))
    }

    @Test
    fun `DogBreed equality should work correctly`() {
        // Given
        val dogBreed1 = DogBreed(
            id = "poodle",
            name = "Poodle",
            imageUrls = listOf("poodle1.jpg")
        )
        val dogBreed2 = DogBreed(
            id = "poodle",
            name = "Poodle",
            imageUrls = listOf("poodle1.jpg")
        )
        val dogBreed3 = DogBreed(
            id = "labrador",
            name = "Labrador",
            imageUrls = listOf("labrador1.jpg")
        )

        // Then
        assertEquals(dogBreed1, dogBreed2)
        assertNotEquals(dogBreed1, dogBreed3)
    }
}