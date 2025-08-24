package com.airwallex.guessmypup.features.quiz.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.airwallex.guessmypup.features.quiz.domain.QuizScreenUiState
import com.airwallex.guessmypup.features.quiz.ui.screens.QuizAwardScreen
import com.airwallex.guessmypup.features.quiz.ui.screens.QuizPlayingScreen
import com.airwallex.guessmypup.features.quiz.viewmodel.QuizViewModel
import com.airwallex.guessmypup.features.quiz.ui.screens.QuizErrorScreen
import com.airwallex.guessmypup.features.quiz.ui.screens.QuizLoadingScreen

@Composable
fun QuizScreen(
    onBackToHome: () -> Unit,
    onReviewBreeds: () -> Unit,
    modifier: Modifier = Modifier,
    quizViewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by quizViewModel.uiState.collectAsState()

    when (val state = uiState) {
        is QuizScreenUiState.Loading -> {
            QuizLoadingScreen()
        }

        is QuizScreenUiState.Error -> {
            QuizErrorScreen(
                message = state.message,
                onRetry = { quizViewModel.startNewQuiz() },
                onBackToHome = onBackToHome,
            )
        }

        is QuizScreenUiState.Playing -> {
            val currentQuestion = state.currentQuestion
            if (currentQuestion != null) {
                QuizPlayingScreen(
                    currentQuestion = currentQuestion,
                    currentQuestionNumber = state.currentQuestionNumber,
                    totalQuestions = state.totalQuestions,
                    question = "What breed is this dog?",
                    isAnswerSelected = state.isAnswerSelected,
                    onAnswerSelected = { answer -> quizViewModel.selectAnswer(answer) },
                    onNextQuestion = { quizViewModel.nextQuestion() },
                    modifier = modifier,
                )
            }
        }

        is QuizScreenUiState.Award -> {
            QuizAwardScreen(
                score = state.score,
                totalQuestions = state.totalQuestions,
                onPlayAgain = { quizViewModel.restartQuiz() },
                onReviewBreeds = onReviewBreeds,
                onBackToHome = onBackToHome,
                modifier = modifier
            )
        }
    }
}