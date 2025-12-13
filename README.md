# ✈️ Flight Search App

A modern Android application built with **Jetpack Compose** that allows users to search for airports, explore flight routes, and save their favorite destinations. This project demonstrates advanced local data persistence using **Room Database** and **Preferences DataStore**.

## 📋 Overview

The Flight Search App is designed to simplify route finding. Users can search for airports by name or IATA code. The app provides real-time autocomplete suggestions based on a pre-populated database. Once an airport is selected, users can view all available flights departing from that location and "favorite" specific routes for quick access later.

This project was built to master **SQL queries**, **Room DAO integration**, and **state management** in Jetpack Compose.

## 📱 Screenshots

| Favorites Screen | Search & Autocomplete | Search Results |
|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/c688fe0b-2c32-47bd-84ee-6573ae678475" width="250" alt="Favorites Screen"/> | <img src="https://github.com/user-attachments/assets/79dfb53b-217c-4e8b-ba05-809772f4d914" width="250" alt="Search Screen"/> | <img src="https://github.com/user-attachments/assets/c159eac1-aa9e-493e-8c00-365abdef4020" width="250" alt="Results Screen"/> |


## 🎥 Video Demo


A short demonstration showcasing the app's key features: **real-time autocomplete suggestions**, the generation of **flight routes** after selecting a departure airport, and the functionality for **saving and removing favorite routes**. The demo also highlights **state persistence**, showing the app restoring the previous search query and results upon reopening.

<video src="https://github.com/user-attachments/assets/6299cc5d-bf84-41e5-a5d6-3e47f69f2d3b" controls loop width="100%"> 
</video>

## ✨ Key Features

* **🔍 Smart Search:** Enter an airport name or IATA identifier (e.g., "SFO") to find airports.
* **⚡ Autocomplete Suggestions:** Queries the database in real-time as the user types to provide instant suggestions.
* **🛫 Route Generation:** Selecting a departure airport automatically generates a list of all possible destinations from that airport.
* **❤️ Favorites System:** Users can save individual flight routes. These favorites are displayed on the main screen when the search bar is empty.
* **💾 State Persistence:** Uses **Preferences DataStore** to save the user's last search query. When the app is reopened, the search field is pre-populated, and results are restored.

## 🛠️ Tech Stack & Libraries

* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Database:** * [Room Persistence Library](https://developer.android.com/training/data-storage/room) (SQLite abstraction)
    * Pre-populated Database (Asset)
* **Data Storage:** [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (For saving search state)
* **Concurrency:** Kotlin Coroutines & Flow

## 🏗️ Architecture Overview 

The app strictly follows the recommended **Android App Architecture** (MVVM with a dedicated Data Layer) and utilizes **Dependency Injection** for robust component management.

1.  **UI Layer (Jetpack Compose):**
    * Composable functions define the UI and observe the `ViewModel` for state changes (e.g., loading status, flight lists, search text).
2.  **ViewModel Layer:**
    * Holds and manages the UI state (Search Query, Flight List, Favorite List).
    * Communicates with the Repository to fetch and update data.
3.  **Data Layer:**
    * **Repository:** The single source of truth; mediates between the database and DataStore.
    * **Room DAO:** Handles all persistence operations:
        * Retrieving airport lists and flight routes.
        * Managing favorite routes (`INSERT` to add, `DELETE` to remove).
        * Executing efficient `LIKE` queries for real-time search suggestions.
    * **Preferences DataStore:** Manages simple key-value storage for state persistence, specifically saving and restoring the user's last search text.
4.  **Dependency Injection (DI):**
    * Used to provide necessary dependencies (like the Room Database instance, DAOs, and the Repository) to the `ViewModel` and other classes. This ensures loose coupling and easy testing.

## 🚀 How to Run

### Prerequisites
- Android Studio Flamingo (2022.2.1) or higher
- Android SDK 34 (Android 14)
- Kotlin 1.9.0
  
### Installation
1.  **Clone the repository**
    Use the command below to clone the project to your local machine.
    ```bash
    git clone https://github.com/aaimran236/Flight-Search-app.git
    cd Flight-Search-app
    ```

2.  **Open the Project in Android Studio**
    - Launch Android Studio.
    - Click on **"Open"** or select **File → Open** from the menu.
    - Navigate to and select the `Flight-Search-app` directory you just cloned.
    - Android Studio will now import and index the project. This may take a few minutes on the first open as it downloads Gradle dependencies.

3.  **Build and Run the App**
    - Ensure you have an Android Virtual Device (AVD) set up or a physical device connected with USB debugging enabled.
    - In the toolbar at the top of Android Studio, select your target device from the dropdown menu.
    - Click the green **Run (▶️)** button or press `Shift + F10` to build the app and install it on your selected device.

## 💡 Usage Guide

### Searching for Flights
1. Launch the app.
2. Type an airport name (e.g., "Chicago") or IATA code (e.g., "ORD") into the search field.
3. Select an option from the real-time autocomplete suggestions.
4. View the generated list of available flight destinations from the selected airport.

### Managing Favorites
* **Add Favorite:** Tap the heart icon next to any route on the results list.
* **View Favorites:** Clear the search field to display all user-selected favorite routes.
* **Remove Favorite:** Tap the filled heart icon on a favorite route (either in the results or the main favorites list) to remove it from the database.



## ⚙️ Key Implementation Details

### 1. Data Persistence & Access
* **Database Setup:** Utilizes a pre-populated SQLite database containing all airport data, managed by the **Room ORM** for type-safe, compile-time checked database access.
* **State Persistence:** The user's last search query is efficiently saved and restored using **Preferences DataStore**, providing a seamless user experience upon reopening the app.

### 2. Search & Query Logic
* **Efficient Search:** Implemented real-time database querying using optimized SQL `LIKE` operators to provide instant autocomplete suggestions.
* **Case-Insensitivity:** Search logic handles both IATA codes and airport names in a case-insensitive manner.

### 3. UI and Architecture
* **Unidirectional Data Flow (UDF):** The Jetpack Compose UI adheres strictly to UDF principles, ensuring predictable state changes.
* **Dependency Injection (DI):** Employed for injecting the Room Database, DAOs, and the Repository, enhancing component testability and modularity.

## ✔️ Requirements Fulfilled (Project Scope)

This project successfully met all specified requirements:

* **Input:** Provided a text field for airport name/IATA code input.
* **Autocomplete:** Implemented database querying for real-time autocomplete suggestions.
* **Output:** Generated a list of available flight destinations (including IATA codes and names).
* **Favorites:** Included functionality for saving and displaying favorite routes.
* **State:** Persisted the last search query using Preferences DataStore.
* **Data Source:** Integrated the provided pre-populated database asset.

## 🤝 Acknowledgments

This project was developed as the final project for **Unit 6, Pathway 3: Project: Create a Flight Search app** within the **Android Basics with Compose** training course provided by Google.

* A special thanks to the Google Developers training team for providing the requirements, initial concept, and the pre-populated database used in this application.
