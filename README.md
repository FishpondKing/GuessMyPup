# 🐕 GuessMyPup

A modern Android quiz application that challenges users to identify dog breeds from images. Built with the latest Android development technologies and following Clean Architecture principles.

## 📱 Features

### Core Functionality
- **Interactive Quiz**: 10-question quiz featuring various dog breeds
- **Dog Breed Library**: Browse and explore different dog breeds with images
- **Real-time Feedback**: Immediate visual feedback on correct/incorrect answers
- **Score Tracking**: Track your performance with detailed scoring

### User Experience
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Smooth Animations**: Engaging transitions and feedback overlays

## 🏗️ Architecture & Technical Stack

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture Components**: ViewModel, StateFlow, Navigation Compose
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit with Kotlin Serialization
- **Image Loading**: Coil Compose
- **Testing**: JUnit, MockK, Coroutines Test, Turbine

### Project Structure
```
app/src/main/java/com/airwallex/guessmypup/
├── data/                          # Data Layer
│   ├── model/                     # Domain Models
│   ├── remote/api/                # API Services & DTOs
│   ├── repository/                # Repository Implementations
│   └── mapper/                    # Data Mappers
├── features/                      # Feature Modules
│   ├── quiz/                      # Quiz Feature
│   │   ├── domain/               # UI States & Events
│   │   ├── ui/                   # Composables & Screens
│   │   └── viewmodel/            # ViewModels
│   ├── breedlibrary/             # Breed Library Feature
│   └── welcome/                  # Welcome Screen
├── di/                           # Dependency Injection
├── navigation/                   # App Navigation
└── ui/                           # Common Ui & Design System
```

## 🔧 Key Technical Implementations

### State Management
- **Sealed Classes**: Type-safe UI state representation
- **StateFlow**: Reactive state management with coroutines
- **Event-Driven Architecture**: Clean separation of UI events and state

### Data Layer
- **Repository Pattern**: Interface-based abstraction for data sources
- **DTO Mapping**: Clean separation between API models and domain models
- **Error Handling**: Comprehensive Result-based error handling

### Testing Strategy
- **Unit Tests**: Comprehensive test coverage for business logic
- **Repository Tests**: Mock-based testing of data layer
- **ViewModel Tests**: StateFlow and event testing with Turbine
- **Mapper Tests**: Data transformation validation

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK 24+ (Android 7.0)

### Setup & Installation
1. **Clone the repository**
   ```bash
   git clone [repository-url]
   cd GuessMyPup
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Run tests**
   ```bash
   ./gradlew test
   ```

## 🧪 Testing

The project includes comprehensive unit tests covering:

### Test Coverage
- **Data Layer**: Repository implementations, mappers, models
- **Domain Layer**: UI states, business logic validation
- **Presentation Layer**: ViewModels with StateFlow testing

### Running Tests
```bash
# Run all unit tests
./gradlew test

# Run specific test suite
./gradlew test --tests GuessMyPupTestSuite

# Generate test coverage report
./gradlew testDebugUnitTest
```

## 🎨 Design Decisions

### UI/UX Choices
- **Material 3**: Modern design language with adaptive theming
- **Jetpack Compose**: Declarative UI for maintainable and testable interfaces
- **Custom Theme**: Warm color palette matching the dog/puppy theme

### Architecture Decisions
- **Clean Architecture**: Ensures testability, maintainability, and scalability
- **Feature-Based Modules**: Logical grouping for better code organization
- **Interface Segregation**: Repository interfaces for easy testing and swapping implementations
- **Immutable State**: Predictable state management with sealed classes

### API Integration
- **Dog CEO API**: Reliable source for dog breed data and images
- **Retrofit Configuration**: Kotlin Serialization for type-safe JSON handling

## 🔄 Data Flow

```
API Service → Repository → ViewModel → UI State → Compose UI
     ↓           ↓            ↓          ↓           ↓
   DTOs    → Domain Models → Events → State Updates → Recomposition
```

## 📊 Performance Considerations

- **Lazy Loading**: Efficient image loading with Coil
- **Coroutines**: Non-blocking asynchronous operations
- **State Optimization**: Minimal recomposition with stable state objects
- **Memory Management**: Proper lifecycle handling in ViewModels

## 📄 License

This project is developed as a demonstration application for interview purposes.

---

**Built with ❤️ using modern Android development practices**