package com.example.rateflow.domain.usecase


import com.example.rateflow.domain.model.OfficialRate
import com.example.rateflow.domain.repository.CurrencyRepository
import javax.inject.Inject
import com.example.rateflow.domain.model.OfficialRatesResult

class GetRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend fun getOfficialRates(forceRefresh: Boolean = false): Result<OfficialRatesResult> {
        return try {
            val result = repository.getOfficialRates(forceRefresh)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
