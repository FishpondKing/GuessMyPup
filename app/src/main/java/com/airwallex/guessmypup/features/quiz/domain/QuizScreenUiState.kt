package com.airwallex.guessmypup.features.quiz.domain

import com.airwallex.guessmypup.data.model.QuizQuestion

sealed class QuizScreenUiState {
    object Loading : QuizScreenUiState()
    
    data class Error(
        val message: String
    ) : QuizScreenUiState()
    
    data class Playing(
        val questions: List<QuizQuestion>,
        val currentQuestionIndex: Int,
        val score: Int,
        val isAnswerSelected: Boolean,
    ) : QuizScreenUiState() {
        val currentQuestion: QuizQuestion?
            get() = questions.getOrNull(currentQuestionIndex)
            
        val currentQuestionNumber: Int
            get() = currentQuestionIndex + 1
            
        val totalQuestions: Int
            get() = questions.size
    }
    
    data class Award(
        val score: Int,
        val totalQuestions: Int
    ) : QuizScreenUiState()
}