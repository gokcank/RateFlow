package com.example.rateflow.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Converter : Screen("converter")
    object Settings : Screen("settings")
}
