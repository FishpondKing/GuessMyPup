package com.airwallex.guessmypup

import com.airwallex.guessmypup.data.mapper.DogBreedMapperTest
import com.airwallex.guessmypup.data.model.DogBreedTest
import com.airwallex.guessmypup.data.model.QuizQuestionTest
import com.airwallex.guessmypup.data.repository.DogBreedRepositoryImplTest
import com.airwallex.guessmypup.features.breedlibrary.viewmodel.BreedLibraryViewModelTest
import com.airwallex.guessmypup.features.quiz.domain.QuizScreenUiStateTest
import com.airwallex.guessmypup.features.quiz.viewmodel.QuizViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Test suite that runs all unit tests in the project.
 * 
 * This suite covers:
 * - Data layer: Models, Mappers, Repositories
 * - Domain layer: UI States, Business Logic
 * - Presentation layer: ViewModels
 * 
 * To run all tests: ./gradlew test
 * To run this suite: ./gradlew test --tests GuessMyPupTestSuite
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
    // Data Layer Tests
    DogBreedTest::class,
    QuizQuestionTest::class,
    DogBreedMapperTest::class,
    DogBreedRepositoryImplTest::class,
    
    // Domain Layer Tests
    QuizScreenUiStateTest::class,
    
    // Presentation Layer Tests
    QuizViewModelTest::class,
    BreedLibraryViewModelTest::class
)
class GuessMyPupTestSuite