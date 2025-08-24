package com.airwallex.guessmypup.features.quiz.domain

sealed interface QuizEvent {
    data class AnswerSelected(val data: AnswerSelectedEventData) : QuizEvent
    object QuizComplete : QuizEvent
}

data class AnswerSelectedEventData(
    val selectAnswer: String,
    val isCorrect: Boolean,
    val currentQuestion: Int,
    val totalQuestions: Int
)