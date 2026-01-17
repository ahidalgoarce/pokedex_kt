# Pokedex Android Application

## Project Description

This project is a modern Android application that serves as a digital Pokedex. It allows users to browse and discover Pokémon from different generations, view detailed information about each one, and enjoy a clean, responsive user interface built entirely with Jetpack Compose.

The app is designed following modern Android development principles, including a Clean Architecture approach, MVVM for the presentation layer, and extensive use of Jetpack libraries.

## Main Features

- **Browse by Generation**: Filter the Pokémon list by selecting a specific generation.
- **Pokémon List**: Displays a grid of Pokémon with their names and images, featuring a shimmer effect for a smooth loading experience.
- **Detail View**: Tap on a Pokémon to see its detailed information, including:
    - Types, height, and weight.
    - Base stats with animated progress bars.
    - A short description.
    - Evolution chain.
- **Dynamic Theming**: The detail screen features a dynamic background color that matches the Pokémon's primary type.
- **Gesture Navigation**: Swipe left or right on the detail screen to navigate to the next or previous Pokémon.
- **Splash Screen**: A custom splash screen is displayed on app launch, compliant with Android 12+ standards.

## Architecture

The application follows the principles of **Clean Architecture** with an **MVVM (Model-View-ViewModel)** pattern for the presentation layer. This approach promotes a separation of concerns, making the codebase scalable, maintainable, and easy to test.

- **Domain Layer**: Contains the core business logic, including models, repository interfaces, and use cases. It is completely independent of the other layers.
- **Data Layer**: Implements the repository interface from the domain layer. It is responsible for fetching data from the network (using Retrofit) and mapping it to the domain models.
- **UI (Presentation) Layer**: Displays the data on the screen using Jetpack Compose. It consists of ViewModels that handle UI logic and expose state to the Composables.

## Technologies & Tools

- **Programming Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for building a fully declarative UI.
- **Android Jetpack Components**:
    - **ViewModel**: Manages UI-related data in a lifecycle-conscious way.
    - **Navigation**: Handles navigation between composables.
    - **Hilt**: Provides dependency injection to decouple classes and manage dependencies.
    - **SplashScreen API**: For creating a modern and compatible splash screen.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) for type-safe HTTP requests to the PokeAPI, with [Moshi](https://github.com/square/moshi) for JSON parsing.
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-guide.html) and [Flow](https://kotlinlang.org/docs/flow.html) for managing background threads and handling asynchronous data streams.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for loading and caching images in Jetpack Compose.
- **Testing**:
    - **JUnit**: For unit and instrumented testing.
    - **Coroutines Test & Turbine**: For testing `Flow` and coroutine-based code.
    - **Hilt Android Testing**: For dependency injection in UI tests.
    - **Compose UI Tests**: For verifying Compose layouts and interactions.

## Project Structure

The project is organized by feature and layer, following Clean Architecture principles:

```
com.ahidalgoa.pokedex
├── data                # Data Layer
│   ├── api             # Retrofit service, DTOs, and Mappers
│   └── repository      # Implementation of the domain repository
├── di                  # Hilt Dependency Injection modules
├── domain              # Domain Layer
│   ├── model           # Core data models (Pokemon, PokemonDetail)
│   ├── repository      # Repository interfaces
│   ├── usecase         # Business logic interactors
│   └── util            # Utility classes like Result
└── ui                  # Presentation Layer
    ├── navigation      # Navigation graph and destinations
    ├── state           # UI state data classes
    ├── theme           # App theme, colors, and typography
    ├── view            # Composable screens and components
    └── viewmodel       # ViewModel classes
```

## Requirements

- Android Studio Iguana | 2023.2.1 or newer
- Minimum Android SDK: 23 (Android 6.0 Marshmallow)

## How to Run the Project

1.  **Clone the repository**:
    ```sh
    git clone https://github.com/your-username/Pokedex.git
    ```
2.  **Open in Android Studio**:
    - Open Android Studio.
    - Click on `File` > `Open` and select the cloned project directory.
3.  **Sync Gradle**:
    - Let Android Studio sync the Gradle files. This will download all the required dependencies.
4.  **Run the app**:
    - Select the `app` configuration.
    - Choose an emulator or connect a physical device.
    - Click the 'Run' button.

## Best Practices

- **SOLID Principles**: The project adheres to SOLID principles to create maintainable and flexible code.
- **Separation of Concerns**: Each layer (Domain, Data, UI) has a distinct responsibility, as enforced by Clean Architecture.
- **Dependency Injection**: Hilt is used to manage dependencies, improving testability and decoupling components.
- **UI State Management**: The UI layer uses ViewModels to expose UI state via `StateFlow`, ensuring a reactive and predictable UI.
- **Clean Code**: The codebase aims for readability, simplicity, and clarity.

## Project Status

**In Progress**: This is a demo project under active development. New features and improvements are being added over time.
