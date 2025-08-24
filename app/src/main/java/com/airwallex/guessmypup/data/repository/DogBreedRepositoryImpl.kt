package com.airwallex.guessmypup.data.repository

import com.airwallex.guessmypup.data.model.DogBreed
import com.airwallex.guessmypup.data.model.QuizQuestion
import com.airwallex.guessmypup.data.remote.api.DogApiService
import com.airwallex.guessmypup.data.mapper.DogBreedMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogBreedRepositoryImpl @Inject constructor(
    private val dogApiService: DogApiService
) : DogBreedRepository {

    override suspend fun generateQuizQuestions(numberOfQuestions: Int): Result<List<QuizQuestion>> {
        return try {
            val selectedBreeds = getRandomBreeds(numberOfQuestions).getOrThrow()
            val allBreedNames = selectedBreeds.map { it.name }

            val questions = selectedBreeds.map { correctBreed ->
                val wrongAnswers = allBreedNames
                    .filter { it != correctBreed.name }
                    .shuffled()
                    .take(3)

                val options = (wrongAnswers + correctBreed.name).shuffled()

                QuizQuestion(
                    dogBreed = correctBreed,
                    options = options,
                    correctAnswer = correctBreed.name
                )
            }

            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getRandomBreeds(count: Int): Result<List<DogBreed>> {
        return try {
            val allBreeds = getAllBreeds().getOrThrow()
            Result.success(allBreeds.shuffled().take(count))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getAllBreeds(): Result<List<DogBreed>> {
        return try {
            val response = dogApiService.getAllBreeds()
            if (response.isSuccessful && response.body()?.status == "success") {
                val breedsResponse = response.body()!!

                // Collect all breed images in parallel
                val breedImages = coroutineScope {
                    val imageRequests =
                        breedsResponse.message.entries.take(20).map { (breed, subBreeds) ->
                            async {
                                if (subBreeds.isNotEmpty()) {
                                    val subBreed = subBreeds.first()
                                    val images = fetchBreedImagesByPath(breed, subBreed)
                                    "${breed}_${subBreed}" to images
                                } else {
                                    val images = fetchBreedImagesByPath(breed)
                                    breed to images
                                }
                            }
                        }
                    imageRequests.map { it.await() }.toMap()
                }

                // Use mapper to convert DTO to domain models
                val breeds = DogBreedMapper.mapBreedResponseToBreeds(
                    breedsResponse = breedsResponse,
                    breedImages = breedImages
                )

                Result.success(breeds)
            } else {
                Result.failure(Exception("Failed to fetch breeds from API"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun fetchBreedImagesByPath(
        breed: String,
        subBreed: String? = null
    ): List<String> {
        return try {
            val response = if (subBreed != null) {
                dogApiService.getSubBreedImages(breed, subBreed)
            } else {
                dogApiService.getBreedImages(breed)
            }

            if (response.isSuccessful && response.body()?.status == "success") {
                response.body()?.message ?: emptyList()
            } else {
                emptyList()
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}