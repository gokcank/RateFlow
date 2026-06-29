package com.gokcank.valutarate.presentation.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gokcank.valutarate.domain.model.ConversionResult
import com.gokcank.valutarate.domain.model.Currency
import com.gokcank.valutarate.domain.usecase.ConvertCurrencyUseCase
import com.gokcank.valutarate.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConverterUiState>(ConverterUiState.Idle)
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    private val _amount = MutableStateFlow("1.0")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _fromCurrency = MutableStateFlow("TRY")
    val fromCurrency: StateFlow<String> = _fromCurrency.asStateFlow()

    private val _toCurrency = MutableStateFlow("USD")
    val toCurrency: StateFlow<String> = _toCurrency.asStateFlow()

    private val _availableCurrencies = MutableStateFlow<List<Currency>>(emptyList())
    val availableCurrencies: StateFlow<List<Currency>> = _availableCurrencies.asStateFlow()

    init {
        loadCurrencies()
        convert()
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            getCurrenciesUseCase.getAllCurrencies().collect { list ->
                // Sort to have TRY, USD, EUR at top if desired, or just alphabetical
                _availableCurrencies.value = list.sortedBy { it.code }
            }
        }
    }

    fun updateAmount(newAmount: String) {
        _amount.value = newAmount
        convert()
    }

    fun updateFromCurrency(currencyCode: String) {
        _fromCurrency.value = currencyCode
        convert()
    }

    fun updateToCurrency(currencyCode: String) {
        _toCurrency.value = currencyCode
        convert()
    }

    fun swapCurrencies() {
        val temp = _fromCurrency.value
        _fromCurrency.value = _toCurrency.value
        _toCurrency.value = temp
        convert()
    }

    private fun convert() {
        val currentAmount = _amount.value.toDoubleOrNull() ?: return
        viewModelScope.launch {
            _uiState.value = ConverterUiState.Loading
            val result = convertCurrencyUseCase(
                amount = currentAmount,
                fromCurrency = _fromCurrency.value,
                toCurrency = _toCurrency.value
            )
            result.onSuccess { conversion ->
                _uiState.value = ConverterUiState.Success(
                    result = conversion
                )
            }.onFailure { error ->
                _uiState.value = ConverterUiState.Error(error.message ?: "Conversion failed")
            }
        }
    }
}

sealed interface ConverterUiState {
    object Idle : ConverterUiState
    object Loading : ConverterUiState
    data class Success(val result: ConversionResult) : ConverterUiState
    data class Error(val message: String) : ConverterUiState
}
