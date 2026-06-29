package com.gokcank.valutarate.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gokcank.valutarate.domain.model.Currency
import com.gokcank.valutarate.domain.model.OfficialRate
import com.gokcank.valutarate.domain.usecase.GetCurrenciesUseCase
import com.gokcank.valutarate.domain.usecase.GetRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            
            // Try fetching official rates first
            val result = getRatesUseCase.getOfficialRates(forceRefresh)
            
            result.onSuccess { officialRatesResult ->
                _uiState.value = HomeUiState.Success(
                    officialRates = officialRatesResult.rates,
                    tcmbDate = officialRatesResult.date
                )
            }.onFailure { error ->
                _uiState.value = HomeUiState.Error(error.message ?: "An error occurred")
            }
        }
    }
}

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(
        val officialRates: List<OfficialRate>,
        val tcmbDate: String,
        val favoriteCurrencies: List<Currency> = emptyList()
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
