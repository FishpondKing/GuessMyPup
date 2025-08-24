package com.airwallex.guessmypup.data.remote.api

import com.airwallex.guessmypup.data.remote.api.dto.BreedsResponse
import com.airwallex.guessmypup.data.remote.api.dto.BreedImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsResponse>
    
    @GET("breed/{breed}/images/random/3")
    suspend fun getBreedImages(@Path("breed") breed: String): Response<BreedImagesResponse>
    
    @GET("breed/{breed}/{subBreed}/images/random/3") 
    suspend fun getSubBreedImages(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): Response<BreedImagesResponse>
}