package com.airwallex.guessmypup.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class GuessMyPupDestination {
    @Serializable
    data object Welcome : GuessMyPupDestination()
    
    @Serializable
    data object Quiz : GuessMyPupDestination()
    
    @Serializable
    data object BreedLibrary : GuessMyPupDestination()
}