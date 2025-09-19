# Google Map App 🗺️

Google Map is an Android application that allows users to view their current location, search for places, and display them on Google Maps. Built with Jetpack Compose, it uses the latest libraries and MVVM architecture.

---

## 📌 Features

- Display user's current location on the map
- Search for places by name and show them on the map
- Live updates of user location
- Location permission handling and GPS status check
- Smooth camera animations on the map
- Internet connectivity check with proper messages
- Uses Google Maps Compose and Google Places API
- Dependency injection and navigation with CoKoin

---

## 🛠 Technologies

- **Kotlin** with **Jetpack Compose**
- **Google Maps Compose** for map rendering
- **Google Places API** for location search
- **Fused Location Provider** for user location
- **CoKoin** for dependency injection and navigation
- **Kotlin Coroutines** for asynchronous operations
- **Lottie Compose** for animations
- **MVVM Architecture** with ViewModel
- **Android Gradle Plugin 8.x**

---

## 🏗 Architecture

- **UI:** Jetpack Compose  
- **ViewModel:** Manages state and retrieves location data  
- **Repository (optional):** Handles data management  
- **Navigation:** Page routing with CoKoinNavHost  
- **Location & Network Checker:** Monitors GPS and internet connectivity  
- **Map & Marker:** Displays user and searched locations  
- **Animations:** Smooth camera movements on map

---

## 📸 Screenshots

<p align="center">
  <img src="https://user-images.githubusercontent.com/your-image1.png" width="280" alt="Home Screen"/>
  &nbsp;&nbsp;&nbsp;
  <img src="https://user-images.githubusercontent.com/your-image2.png" width="280" alt="Search Location"/>
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/your-image3.png" width="280" alt="Map View"/>
</p>

---
