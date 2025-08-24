package com.airwallex.guessmypup.data.model

data class DogBreed(
    val id: String,
    val name: String,
    val imageUrls: List<String> = emptyList(),
)