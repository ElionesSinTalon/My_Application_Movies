# Movie App - Android Project

A modern Android application for exploring movies, built with Jetpack Compose and following the MVVM (Model-View-ViewModel) architecture. This app interacts with a REST API to display current movies, premieres, and detailed information.

## 🚀 Features

- **Home Screen**: Features a cinematic, full-width auto-scrolling banner with automatic transitions every 5 seconds. It displays categories like "Now Playing", "Trending Now", and "Popular Genres".
- **Explorer Screen**: Advanced search functionality with real-time filtering by title or genre. Includes a category selection row for quick filtering.
- **Premiere Screen**: Dedicated section for upcoming and currently featured premiere movies, including a specialized banner.
- **Movie Details**: Comprehensive detail view for each movie, including synopsis, rating, duration, director, and genre.
- **Quick Actions**: A `ModalBottomSheet` accessible from the center navigation button allowing users to find a random movie, add to their list, or view top-rated content.
- **Robust State Management**: Integrated loading indicators and error handling with a "Retry" mechanism for a smooth user experience.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative UI.
- **Navigation**: [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) for screen transitions.
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) with [Gson](https://github.com/google/gson) for API communication.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for efficient asynchronous image loading.
- **Architecture**: MVVM with `StateFlow` and `viewModelScope` for reactive UI updates.
- **Dependency Injection**: Factory-based ViewModel injection for clean separation of concerns.

## 📋 Prerequisites

- **Android Studio**: Android Studio Ladybug (2024.2.1) or newer recommended.
- **JDK**: Java 11 or higher.
- **Android SDK**: Min SDK 24, Target SDK 36.

## ⚙️ Setup & Installation

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   ```
2. **Open in Android Studio**:
   - Launch Android Studio.
   - Select **Open** and navigate to the project directory.
   - Wait for the Gradle sync to complete.
3. **Run the app**:
   - Connect an Android device or start an emulator (API 24+).
   - Click the **Run** button (green play icon) in the top toolbar.

## 📂 Project Structure

- `mendoza.ruiz.myapplicationmovies.model`: Data classes and Mappers.
- `mendoza.ruiz.myapplicationmovies.network`: Retrofit setup and API service definitions.
- `mendoza.ruiz.myapplicationmovies.repository`: Data access layer abstraction.
- `mendoza.ruiz.myapplicationmovies.viewmodel`: UI State definitions and logic.
- `mendoza.ruiz.myapplicationmovies.screens`: Compose screens and reusable components.
- `mendoza.ruiz.myapplicationmovies.navigation`: Navigation host and routing logic.

## 🔗 API Reference

The app consumes a FastAPI backend hosted at:
`https://fastapi-peliculas-genf.onrender.com/`

Key Endpoints:
- `GET /peliculas`: List all movies.
- `GET /peliculas/{id}`: Get movie details.

---
Developed by Pedro Rafael, Uriel Enrique and David Fregoso.
