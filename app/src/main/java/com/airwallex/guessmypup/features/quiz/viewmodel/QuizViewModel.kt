package com.airwallex.guessmypup.features.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airwallex.guessmypup.data.repository.DogBreedRepository
import com.airwallex.guessmypup.features.quiz.domain.AnswerSelectedEventData
import com.airwallex.guessmypup.features.quiz.domain.QuizEvent
import com.airwallex.guessmypup.features.quiz.domain.QuizScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val dogBreedRepository: DogBreedRepository
) : ViewModel() {

    companion object {
        private const val DEFAULT_NUMBER_OF_QUESTIONS = 10
    }

    private val _uiState = MutableStateFlow<QuizScreenUiState>(QuizScreenUiState.Loading)
    val uiState: StateFlow<QuizScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<QuizEvent>()
    val events: SharedFlow<QuizEvent> = _events.asSharedFlow()

    init {
        startNewQuiz()
    }

    fun startNewQuiz(numberOfQuestions: Int = DEFAULT_NUMBER_OF_QUESTIONS) {
        viewModelScope.launch {
            _uiState.value = QuizScreenUiState.Loading

            val result = dogBreedRepository.generateQuizQuestions(numberOfQuestions)
            if (result.isSuccess) {
                val questions = result.getOrThrow()
                _uiState.value = QuizScreenUiState.Playing(
                    questions = questions,
                    currentQuestionIndex = 0,
                    score = 0,
                    isAnswerSelected = false,
                )
            } else {
                _uiState.value = QuizScreenUiState.Error(
                    "Failed to load quiz questions. Please check your internet connection and try again."
                )
            }
        }
    }

    fun selectAnswer(answer: String) {
        val currentState = _uiState.value
        if (currentState !is QuizScreenUiState.Playing || currentState.isAnswerSelected) {
            return // Answer already selected or not in playing state
        }

        val currentQuestion = currentState.currentQuestion ?: return
        val isCorrect = answer == currentQuestion.correctAnswer

        _uiState.value = currentState.copy(
            isAnswerSelected = true,
            score = if (isCorrect) currentState.score + 1 else currentState.score
        )

        viewModelScope.launch {
            _events.emit(
                QuizEvent.AnswerSelected(
                    AnswerSelectedEventData(
                        selectAnswer = answer,
                        isCorrect = isCorrect,
                        currentQuestion = currentState.currentQuestionNumber,
                        totalQuestions = currentState.totalQuestions
                    )
                )
            )
        }
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        if (currentState !is QuizScreenUiState.Playing) return

        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex >= currentState.questions.size) {
            // Quiz complete - transition to Award state
            _uiState.value = QuizScreenUiState.Award(
                score = currentState.score,
                totalQuestions = currentState.totalQuestions
            )
        } else {
            // Move to next question
            _uiState.value = currentState.copy(
                currentQuestionIndex = nextIndex,
                isAnswerSelected = false,
            )
        }
    }

    fun restartQuiz() {
        val currentState = _uiState.value
        val questionCount = if (currentState is QuizScreenUiState.Playing)
            currentState.totalQuestions
        else
            DEFAULT_NUMBER_OF_QUESTIONS
        startNewQuiz(questionCount)
    }
}