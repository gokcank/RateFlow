package com.example.rateflow.domain.repository

import com.example.rateflow.domain.model.Currency

import com.example.rateflow.domain.model.OfficialRate
import com.example.rateflow.domain.model.OfficialRatesResult
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getAllCurrencies(): Flow<List<Currency>>
    fun getFavoriteCurrencies(): Flow<List<Currency>>
    suspend fun toggleFavoriteStatus(code: String, isFavorite: Boolean)
    suspend fun syncCurrencies()

    suspend fun getOfficialRates(forceRefresh: Boolean = false): OfficialRatesResult
}
