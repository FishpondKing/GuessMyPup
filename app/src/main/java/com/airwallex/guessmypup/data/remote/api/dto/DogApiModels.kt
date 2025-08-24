package com.airwallex.guessmypup.data.remote.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

@Serializable
data class BreedImagesResponse(
    val message: List<String>,
    val status: String
)