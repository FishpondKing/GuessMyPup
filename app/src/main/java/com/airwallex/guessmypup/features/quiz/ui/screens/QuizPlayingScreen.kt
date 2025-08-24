package com.airwallex.guessmypup.features.quiz.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airwallex.guessmypup.data.model.QuizQuestion
import com.airwallex.guessmypup.features.quiz.domain.QuizEvent
import com.airwallex.guessmypup.features.quiz.ui.components.AnswerOptionCard
import com.airwallex.guessmypup.features.quiz.ui.components.DogImageCard
import com.airwallex.guessmypup.features.quiz.ui.components.FeedbackOverlay
import com.airwallex.guessmypup.features.quiz.ui.components.FeedbackOverlayData
import com.airwallex.guessmypup.features.quiz.ui.components.QuizProgressBar
import com.airwallex.guessmypup.ui.theme.*
import com.airwallex.guessmypup.features.quiz.viewmodel.QuizViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

private const val RESULT_OVERLAY_DELAY_MS = 500L

@Composable
fun QuizPlayingScreen(
    currentQuestion: QuizQuestion,
    currentQuestionNumber: Int,
    totalQuestions: Int,
    question: String,
    isAnswerSelected: Boolean,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier,
    quizViewModel: QuizViewModel = hiltViewModel(),
) {
    var showFeedbackOverlay by remember { mutableStateOf(false) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isSelectCorrect by remember { mutableStateOf<Boolean?>(null) }
    var feedbackOverlayData by remember { mutableStateOf<FeedbackOverlayData?>(null) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val isSmallScreen = screenHeight < 600.dp

    // Responsive values
    val horizontalPadding = if (isSmallScreen) 16.dp else 24.dp
    val topSpacing = if (isSmallScreen) 16.dp else 32.dp
    val cardSpacing = if (isSmallScreen) 16.dp else 24.dp
    val cardAspectRatio = if (isSmallScreen) 1.5f else 1.2f

    // Handle quiz events and reset feedback when needed
    LaunchedEffect(Unit) {
        quizViewModel.events.collectLatest { event ->
            when (event) {
                is QuizEvent.AnswerSelected -> {
                    // Emit show answer feedback immediately, then overlay after delay
                    selectedAnswer = event.data.selectAnswer
                    isSelectCorrect = event.data.isCorrect
                    delay(RESULT_OVERLAY_DELAY_MS) // Brief delay before showing overlay
                    feedbackOverlayData = FeedbackOverlayData(
                        isCorrect = event.data.isCorrect,
                        currentQuestion = event.data.currentQuestion,
                        totalQuestions = event.data.totalQuestions
                    )
                    showFeedbackOverlay = true
                }

                QuizEvent.QuizComplete -> {
                    // Navigation handled in NavHost
                }
            }
        }
    }

    // Reset state when question changes
    LaunchedEffect(currentQuestionNumber) {
        selectedAnswer = null
        isSelectCorrect = null
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        LightBeige
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = horizontalPadding)
                .padding(bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            QuizProgressBar(
                currentQuestion = currentQuestionNumber,
                totalQuestions = totalQuestions,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(topSpacing))

            // Dog image card
            DogImageCard(
                imageUrls = currentQuestion.dogBreed.imageUrls,
                aspectRatio = cardAspectRatio
            )

            Spacer(modifier = Modifier.height(cardSpacing))

            // Question
            Text(
                text = question,
                style = if (isSmallScreen) {
                    MaterialTheme.typography.headlineSmall.copy(
                        color = DarkBrown,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    MaterialTheme.typography.headlineMedium.copy(
                        color = DarkBrown,
                        fontWeight = FontWeight.Bold
                    )
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(cardSpacing))

            // Answer options
            Column(
                verticalArrangement = Arrangement.spacedBy(if (isSmallScreen) 8.dp else 12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                currentQuestion.options.forEach { option ->
                    AnswerOptionCard(
                        text = option,
                        isSelected = selectedAnswer == option,
                        isCorrect = isSelectCorrect == true && selectedAnswer == option,
                        isWrong = isSelectCorrect == false && selectedAnswer == option,
                        onClick = { if (!isAnswerSelected) onAnswerSelected(option) },
                        isSmallScreen = isSmallScreen,
                        showFeedback = isAnswerSelected
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bottom overlay with result feedback and next button
        AnimatedVisibility(
            visible = showFeedbackOverlay,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) + fadeIn(),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = spring()
            ) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            feedbackOverlayData?.let { data ->
                FeedbackOverlay(
                    data = data,
                    onNextQuestion = {
                        onNextQuestion()
                        showFeedbackOverlay = false
                    },
                    isSmallScreen = isSmallScreen
                )
            }
            DisposableEffect(Unit) {
                onDispose {
                    feedbackOverlayData = null
                }
            }
        }
    }
}