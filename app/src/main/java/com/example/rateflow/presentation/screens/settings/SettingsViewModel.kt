package com.example.rateflow.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rateflow.data.preferences.ThemePalette
import com.example.rateflow.data.preferences.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.rateflow.presentation.localization.AppLanguage

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreference: ThemePreference
) : ViewModel() {

    val currentTheme: StateFlow<ThemePalette> = themePreference.themePaletteFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemePalette.PURPLE
        )

    val currentLanguage: StateFlow<AppLanguage> = themePreference.appLanguageFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppLanguage.EN
        )

    fun changeTheme(palette: ThemePalette) {
        viewModelScope.launch {
            themePreference.saveThemePalette(palette)
        }
    }

    fun changeLanguage(language: AppLanguage) {
        viewModelScope.launch {
            themePreference.saveAppLanguage(language)
        }
    }
}
