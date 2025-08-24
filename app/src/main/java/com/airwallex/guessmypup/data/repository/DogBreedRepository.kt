package com.airwallex.guessmypup.data.repository

import com.airwallex.guessmypup.data.model.QuizQuestion

interface DogBreedRepository {
    suspend fun generateQuizQuestions(numberOfQuestions: Int): Result<List<QuizQuestion>>
}