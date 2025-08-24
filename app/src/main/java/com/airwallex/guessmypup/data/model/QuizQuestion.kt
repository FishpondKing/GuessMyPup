package com.airwallex.guessmypup.data.model

data class QuizQuestion(
    val dogBreed: DogBreed,
    val options: List<String>,
    val correctAnswer: String
)