package com.airwallex.guessmypup.data.mapper

import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.data.remote.api.dto.BreedsResponse

object DogBreedMapper {
    
    fun mapBreedResponseToBreeds(
        breedsResponse: BreedsResponse,
        breedImages: Map<String, List<String>>
    ): List<DogBreed> {
        return breedsResponse.message.entries.take(20).map { (breed, subBreeds) ->
            if (subBreeds.isNotEmpty()) {
                // Handle breeds with sub-breeds - take first sub-breed
                val subBreed = subBreeds.first()
                val images = breedImages["${breed}_${subBreed}"] ?: emptyList()
                
                createDogBreed(
                    breed = breed,
                    subBreed = subBreed,
                    images = images
                )
            } else {
                // Handle breeds without sub-breeds
                val images = breedImages[breed] ?: emptyList()
                
                createDogBreed(
                    breed = breed,
                    subBreed = null,
                    images = images
                )
            }
        }
    }
    
    private fun createDogBreed(
        breed: String,
        subBreed: String? = null,
        images: List<String>
    ): DogBreed {
        return DogBreed(
            id = generateBreedId(breed, subBreed),
            name = formatBreedName(breed, subBreed),
            imageUrls = images
        )
    }
    
    private fun formatBreedName(breed: String, subBreed: String? = null): String {
        return if (subBreed != null) {
            "${subBreed.replaceFirstChar { it.uppercaseChar() }} ${breed.replaceFirstChar { it.uppercaseChar() }}"
        } else {
            breed.split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
        }
    }
    
    private fun generateBreedId(breed: String, subBreed: String? = null): String {
        return if (subBreed != null) {
            "${breed}_${subBreed}".replace(" ", "_")
        } else {
            breed.replace(" ", "_")
        }
    }
}