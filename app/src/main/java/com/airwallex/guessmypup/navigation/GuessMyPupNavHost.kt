package com.airwallex.guessmypup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.airwallex.guessmypup.features.quiz.ui.QuizScreen
import com.airwallex.guessmypup.features.breedlibrary.ui.BreedLibraryScreen
import com.airwallex.guessmypup.features.welcome.ui.WelcomeScreen
import com.airwallex.guessmypup.features.quiz.viewmodel.QuizViewModel

@Composable
fun GuessMyPupNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = GuessMyPupDestination.Welcome,
        modifier = modifier
    ) {
        composable<GuessMyPupDestination.Welcome> {
            WelcomeScreen(
                onStartQuiz = {
                    navController.navigate(GuessMyPupDestination.Quiz)
                },
                onLearnBreeds = { 
                    navController.navigate(GuessMyPupDestination.BreedLibrary)
                }
            )
        }
        
        composable<GuessMyPupDestination.Quiz> {
            QuizScreen(
                onBackToHome = {
                    navController.navigate(GuessMyPupDestination.Welcome) {
                        popUpTo<GuessMyPupDestination.Welcome> { inclusive = true }
                    }
                },
                onReviewBreeds = { 
                    navController.navigate(GuessMyPupDestination.BreedLibrary)
                },
            )
        }
        
        composable<GuessMyPupDestination.BreedLibrary> {
            BreedLibraryScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}