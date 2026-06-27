package com.example.rateflow.domain.usecase

import com.example.rateflow.domain.model.Currency
import com.example.rateflow.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    fun getAllCurrencies(): Flow<List<Currency>> = repository.getAllCurrencies()

    fun getFavoriteCurrencies(): Flow<List<Currency>> = repository.getFavoriteCurrencies()

    suspend fun syncCurrencies() = repository.syncCurrencies()

    suspend fun toggleFavorite(code: String, isFavorite: Boolean) {
        repository.toggleFavoriteStatus(code, isFavorite)
    }
}
