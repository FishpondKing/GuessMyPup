package com.airwallex.guessmypup.data.di

import com.airwallex.guessmypup.data.repository.DogBreedRepository
import com.airwallex.guessmypup.data.repository.DogBreedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    
    @Binds
    @Singleton
    abstract fun bindDogBreedRepository(
        dogBreedRepositoryImpl: DogBreedRepositoryImpl
    ): DogBreedRepository
}